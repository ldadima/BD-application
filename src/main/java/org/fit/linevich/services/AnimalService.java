package org.fit.linevich.services;

import lombok.RequiredArgsConstructor;
import org.fit.linevich.domain.AnimalCompatibilityEntity;
import org.fit.linevich.domain.AnimalCompatibilityEntityPK;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.CellsAnimalsEntity;
import org.fit.linevich.domain.CellsAnimalsEntityPK;
import org.fit.linevich.domain.EvenDayRationEntity;
import org.fit.linevich.domain.EvenDayRationEntityPK;
import org.fit.linevich.domain.FeedEntity;
import org.fit.linevich.domain.IllnessAnimalsEntity;
import org.fit.linevich.domain.IllnessAnimalsEntityPK;
import org.fit.linevich.domain.IllnessEntity;
import org.fit.linevich.domain.MedCardEntity;
import org.fit.linevich.domain.OddDayRationEntity;
import org.fit.linevich.domain.OddDayRationEntityPK;
import org.fit.linevich.domain.VaccineEntity;
import org.fit.linevich.domain.VaccineEntityPK;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.model.Development;
import org.fit.linevich.model.Season;
import org.fit.linevich.repositories.AnimalsRepo;
import org.fit.linevich.repositories.FeedsRepo;
import org.fit.linevich.repositories.IllnessesRepo;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.AnimalCellQuery;
import org.fit.linevich.views.AnimalMed;
import org.fit.linevich.views.FullInfoAnimalQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalsRepo animalsRepo;
    private final FeedsRepo feedsRepo;
    private final IllnessesRepo illnessesRepo;
    private final CustomDataMapper customDataMapper;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;


    @PostConstruct
    public void initJDBC(){
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName("checkDate");
    }

    public Page<Animal> showAll(int page, int size) {
        Page<AnimalEntity> animalEntities = animalsRepo.findAll(PageRequest.of(page, size,
                Sort.by("id").ascending()));
        return customDataMapper.toAnimalPage(animalEntities);
    }

    public AnimalMed findById(int id) {
        Optional<AnimalEntity> animalEntity = animalsRepo.findById(id);
        AnimalMed animalMed = new AnimalMed();
        animalMed.setAnimal(animalEntity.map(customDataMapper::toAnimalView).orElse(null));
        MedCardEntity medCardEntity = animalEntity.map(AnimalEntity::getMedCardById).orElse(null);
        if(medCardEntity != null){
            animalMed.setHeight(medCardEntity.getHeight());
            animalMed.setWeight(medCardEntity.getWeight());
            animalMed.setDevelopment(medCardEntity.getDevelopment());
            animalMed.setNeedHospital(medCardEntity.getNeedHospital());
            animalMed.setDateLast(LocalDate.parse(medCardEntity.getDateLastInspection().toString()));
        }
        return animalMed;
    }

    @Transactional
    public void create(AnimalMed animal) {
        AnimalEntity animalEntity = new AnimalEntity();
        customDataMapper.toAnimalEntity(animal.getAnimal(), animalEntity);
        if (animalEntity.getDepartureDate() != null) {
            animalEntity.setDepartureDate((Date) simpleJdbcCall.execute(Map.of("date", animalEntity.getDepartureDate())).get("returnValue"));
        }
        animalEntity = animalsRepo.save(animalEntity);
        animalEntity.setMedCardById(new MedCardEntity(animalEntity.getId(), animal.getHeight(), animal.getWeight(),
                animal.getDevelopment(), animal.getNeedHospital(), Date.valueOf(animal.getDateLast()), animalEntity));
        animalsRepo.save(animalEntity);
    }

    @Transactional
    public boolean update(AnimalMed animal) {
        AnimalEntity animalInBd = animalsRepo.findById(animal.getAnimal().getId())
                .orElse(null);
        if (animalInBd == null) {
            return false;
        }
        customDataMapper.toAnimalEntity(animal.getAnimal(), animalInBd);
        if (animalInBd.getDepartureDate() != null) {
            animalInBd.setDepartureDate((Date) simpleJdbcCall.execute(Map.of("date", animalInBd.getDepartureDate())).get("returnValue"));
        }
        animalInBd.setMedCardById(new MedCardEntity(animalInBd.getId(), animal.getHeight(), animal.getWeight(),
                animal.getDevelopment(), animal.getNeedHospital(), Date.valueOf(animal.getDateLast()), animalInBd));
        animalsRepo.save(animalInBd);
        return true;
    }

    public void deleteById(int id) {
        animalsRepo.deleteById(id);
    }

    public void addInfoCell(int cellId, int animalId, Date begin, Date end, boolean heating) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getCellsAnimalsById().add(new CellsAnimalsEntity(new CellsAnimalsEntityPK(cellId, animalId), begin, end, heating, animalEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteInfoCell(int cellId, int animalId) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getCellsAnimalsById().removeIf(cellsAnimalsEntity -> cellId == cellsAnimalsEntity.getCellsAnimalsEntityPK().getCellId());
        animalsRepo.save(animalEntity);
    }

    public void addOddDayRation(int animalId, Season season, int feedId, int quantity) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        FeedEntity feedEntity = feedsRepo.findById(animalId).orElse(null);
        if (animalEntity == null || feedEntity == null) {
            return;
        }
        animalEntity.getOddDayRationsById()
                .add(new OddDayRationEntity(new OddDayRationEntityPK(season, animalId), quantity, animalEntity,
                        feedEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteOddDayRation(int animalId, Season season) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getOddDayRationsById().removeIf(
                oddDayRationEntity -> oddDayRationEntity.getOddDayRationEntityPK().getSeason().equals(season));
        animalsRepo.save(animalEntity);
    }

    public void addEvenDayRation(int animalId, Season season, int feedId, int quantity) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        FeedEntity feedEntity = feedsRepo.findById(feedId).orElse(null);
        if (animalEntity == null || feedEntity == null) {
            return;
        }
        animalEntity.getEvenDayRationsById()
                .add(new EvenDayRationEntity(new EvenDayRationEntityPK(season, animalId), quantity, animalEntity,
                        feedEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteEvenDayRation(int animalId, Season season) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getEvenDayRationsById().removeIf(
                evenDayRationEntity -> evenDayRationEntity.getEvenDayRationEntityPK().getSeason().equals(season));
        animalsRepo.save(animalEntity);
    }

    public void addIllness(int animalId, int illnessId, LocalDate dateBegin, LocalDate dateEnd) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        IllnessEntity illnessEntity = illnessesRepo.findById(illnessId).orElse(null);
        if (animalEntity == null || illnessEntity == null) {
            return;
        }
        animalEntity.getIllnessAnimalsById()
                .add(new IllnessAnimalsEntity(new IllnessAnimalsEntityPK(animalId, illnessId), Date.valueOf(dateBegin),
                        Date.valueOf(dateEnd), animalEntity, illnessEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteIllness(int animalId, int illnessId) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getIllnessAnimalsById().removeIf(
                illnessAnimalsEntity -> illnessAnimalsEntity.getIllnessAnimalsEntityPK().getIllnessId() == illnessId);
        animalsRepo.save(animalEntity);
    }

    public void addVaccine(int animalId, int vaccineId, String name, int dose, LocalDate date) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getVaccinesById()
                .add(new VaccineEntity(new VaccineEntityPK(animalId, vaccineId), name, dose, Date.valueOf(date),
                        animalEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteVaccine(int animalId, int vaccineId) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getVaccinesById().removeIf(
                vaccineEntity -> vaccineEntity.getVaccineEntityPK().getVaccineId() == vaccineId);
        animalsRepo.save(animalEntity);
    }

    public void addCompatibility(int animalId, String kind) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getAnimalCompatibilitiesById()
                .add(new AnimalCompatibilityEntity(new AnimalCompatibilityEntityPK(kind, animalId), animalEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteCompatibility(int animalId, String kind) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getAnimalCompatibilitiesById()
                .removeIf(animalCompatibilityEntity -> animalCompatibilityEntity.getAnimalCompatibilityEntityPK()
                        .getAnimalKind().equals(kind));
        animalsRepo.save(animalEntity);
    }

    public void addOrUpdateMedCard(int animalId, int height, int weight, Development development, boolean needHospital,
            LocalDate dateLast) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        MedCardEntity medCardEntity = animalEntity.getMedCardById();
        if (medCardEntity == null) {
            medCardEntity =
                    new MedCardEntity(animalId, height, weight, development, needHospital, Date.valueOf(dateLast),
                            animalEntity);
            animalEntity.setMedCardById(medCardEntity);
        }
        medCardEntity.setDateLastInspection(Date.valueOf(dateLast));
        medCardEntity.setHeight(height);
        medCardEntity.setWeight(weight);
        medCardEntity.setDevelopment(development);
        animalsRepo.save(animalEntity);
    }

    public void deleteMedCard(int animalId) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.setMedCardById(null);
        animalsRepo.save(animalEntity);
    }


    /**
     * 4-ый запрос
     */
    public Page<AnimalCellQuery> animalsCellLife(int page, int size, int cellId) {
        List<AnimalCellQuery> animalCellQueries = entityManager.createQuery(
                "select new org.fit.linevich.views.AnimalCellQuery(a.name, a.kindAnimal, a.gender, m.height, m" +
                        ".weight, a.birthday) from " +
                        "AnimalEntity a join MedCardEntity m on m.animal = a join CellsAnimalsEntity c on a = c" +
                        ".animalId left " +
                        "join AnimalReceiptEntity ar on ar.animalId = a  where " +
                        "c.cellsAnimalsEntityPK.cellId = :cell" +
                        "  and ((not ar.animalId is null  and" +
                        "        ar.dateReceipt = c.dateBegin and" +
                        "        (a.departureDate = c.dateEnd or" +
                        "         (a.departureDate is null and c.dateEnd is null)))" +
                        "  or" +
                        "       (a.birthday = c.dateBegin and" +
                        "        (a.departureDate = c.dateEnd or" +
                        "         (a.departureDate is null and c.dateEnd is null)))) order by a.birthday",
                AnimalCellQuery.class).setParameter("cell", cellId).getResultList();
        return new PageImpl<>(animalCellQueries.subList(page*size, Math.min(size * (page + 1), animalCellQueries.size())), PageRequest.of(page, size), animalCellQueries.size());
    }

    /**
     * 5-ый запрос
     */
    public Page<Animal> needWarmAnimalsByAge(int page, int size, int age) {
        List<AnimalEntity> animalEntities = entityManager
                .createNativeQuery("select *" +
                                "from" +
                                " animals " +
                                " where (climatic_habitat = 'Экваториальный лес'" +
                                "  or climatic_habitat = 'Субтропический лес'" +
                                "  or climatic_habitat = 'Пустыни')" +
                                "  and (DATE_PART('year', now()) - DATE_PART('year', animals.birthday)) * 12 +" +
                                "      (DATE_PART('month', now()) - DATE_PART('month', animals.birthday)) = :age",
                        AnimalEntity.class)
                .setParameter("age", age)
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 6-ой запрос
     */
    public Page<Animal> givenIllnessAnimals(int page, int size, String illness) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join IllnessAnimalsEntity as ia on a = ia.animalId " +
                        "join IllnessEntity as ill on ill = ia.illnessId " +
                        "where ill.name = :illness", AnimalEntity.class)
                .setParameter("illness", illness)
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 6-ой запрос
     */
    public Page<Animal> givenVaccineAnimals(int page, int size, String vaccine) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join VaccineEntity as vac on a = vac.animalId " +
                        "where vac.medicineName = :vaccine", AnimalEntity.class)
                .setParameter("vaccine", vaccine)
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 7-ой запрос
     */
    public Page<Animal> compatibilityKindAnimals(int page, int size, String kind) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join AnimalCompatibilityEntity as co on co.animalId = a " +
                        "where co.animalCompatibilityEntityPK.animalKind = :kind", AnimalEntity.class)
                .setParameter("kind", kind)
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 7-ой запрос
     */
    public Page<Animal> needRelocationAnimals(int page, int size) {
        Page<AnimalEntity> animalEntities =
                animalsRepo.getAnimalEntitiesByNeedRelocationIsTrue(PageRequest.of(page, size, Sort.by("id").ascending()));
        return customDataMapper.toAnimalPage(animalEntities);
    }

    /**
     * 7-ой запрос
     */
    public Page<Animal> needWarmCellAnimals(int page, int size) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join CellsAnimalsEntity as ce on ce.animalId = a " +
                        "where a.climaticHabitat in :zones and ce.heating = false ", AnimalEntity.class)
                .setParameter("zones", Set.of(ClimaticZone.EQUATORIAL_FOREST, ClimaticZone.SUBTROPICAL_FOREST,
                        ClimaticZone.DESERT))
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 10-ый запрос
     */
    public Page<Animal> needFeed(int page, int size, String feed, Season season) {
        List<AnimalEntity> animalEntities = entityManager.createNativeQuery("select *  " +
                "from animals  " +
                "where id in (select (anim_feeds.animal_id)  " +
                "             from feeds  " +
                "                    join (select *  " +
                "                          from even_day_ration  " +
                "                          union all  " +
                "                          select *  " +
                "                          from odd_day_ration)  " +
                "               as anim_feeds on feeds.id = anim_feeds.feed_id  " +
                "             where feeds.name = :feed  " +
                "               and season = :season)", AnimalEntity.class)
                .setParameter("feed", feed)
                .setParameter("season", season.getName())
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }

    /**
     * 11-ый запрос
     */
    public List<FullInfoAnimalQuery> fullInfoByKind(String kind) {
        return entityManager
                .createQuery(
                        "select new org.fit.linevich.views.FullInfoAnimalQuery(a.name, a.kindAnimal, a.type, a" +
                                ".climaticHabitat, a.gender, a.physicalState, a.progeny, a.birthday, med.height, med" +
                                ".weight, v.medicineName, ill.name) from AnimalEntity a " +
                                "left join MedCardEntity med on med.animal = a " +
                                "left join IllnessAnimalsEntity ill_a on ill_a.animalId = a " +
                                "left join IllnessEntity ill on ill = ill_a.illnessId " +
                                "left join VaccineEntity v on v.animalId = a " +
                                "where a.kindAnimal = :kind", FullInfoAnimalQuery.class)
                .setParameter("kind", kind)
                .getResultList();
    }

    /**
     * 11-ый запрос
     */
    public List<FullInfoAnimalQuery> fullInfoByVaccine(String vaccine) {
        return entityManager
                .createQuery(
                        "select new org.fit.linevich.views.FullInfoAnimalQuery(a.name, a.kindAnimal, a.type, a" +
                                ".climaticHabitat, a.gender, a.physicalState, a.progeny, a.birthday, med.height, med" +
                                ".weight, v.medicineName, ill.name) from AnimalEntity a " +
                                "left join MedCardEntity med on med.animal = a " +
                                "left join IllnessAnimalsEntity ill_a on ill_a.animalId = a " +
                                "left join IllnessEntity ill on ill = ill_a.illnessId " +
                                "left join VaccineEntity v on v.animalId = a " +
                                "where v.medicineName = :vaccine", FullInfoAnimalQuery.class)
                .setParameter("vaccine", vaccine)
                .getResultList();
    }

    /**
     * 12-ый запрос
     */
    public Page<Animal> expectedChild(int page, int size) {
        List<AnimalEntity> animalEntities = entityManager.createNativeQuery("select * " +
                "from animals" +
                "       join productive_age on kind_animal = kind " +
                "where (DATE_PART('year', now()) - DATE_PART('year', animals.birthday)) * 12 + " +
                "      (DATE_PART('month', now()) - DATE_PART('month', animals.birthday)) between productive_age.age " +
                "and 4*productive_age.age\n" +
                "  and physical_state = 'Здоров'", AnimalEntity.class)
                .getResultList();
        List<Animal> animals = customDataMapper.toAnimalListView(animalEntities);
        return new PageImpl<>(animals.subList(page*size, Math.min(size * (page + 1), animals.size())), PageRequest.of(page, size, Sort.by("id").ascending()), animals.size());
    }
}
