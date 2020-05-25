package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AnimalCompatibilityEntity;
import org.fit.linevich.domain.AnimalCompatibilityEntityPK;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.domain.CellsAnimalsEntity;
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
import org.fit.linevich.views.FullInfoAnimalQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
public class AnimalService {
    private final AnimalsRepo animalsRepo;
    private final FeedsRepo feedsRepo;
    private final IllnessesRepo illnessesRepo;
    private final CustomDataMapper customDataMapper;
    private final EntityManager entityManager;


    public List<Animal> showAll() {
        List<AnimalEntity> animalsList = animalsRepo.findAll();
        return customDataMapper.toAnimalListView(animalsList);
    }

    public Animal findById(int id) {
        Optional<AnimalEntity> animalEntity = animalsRepo.findById(id);
        return animalEntity.map(customDataMapper::toAnimalView).orElse(null);
    }

    public void create(Animal animal) {
        AnimalEntity animalEntity = new AnimalEntity();
        customDataMapper.toAnimalEntity(animal, animalEntity);
        animalsRepo.save(animalEntity);
    }

    public boolean update(Animal animal) {
        AnimalEntity animalInBd = animalsRepo.findById(animal.getId())
                .orElse(null);
        if (animalInBd == null) {
            return false;
        }
        customDataMapper.toAnimalEntity(animal, animalInBd);
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
        animalEntity.getCellsAnimalsById().add(new CellsAnimalsEntity(cellId, begin, end, heating, animalEntity));
        animalsRepo.save(animalEntity);
    }

    public void deleteInfoCell(int cellId, int animalId) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        animalEntity.getCellsAnimalsById().removeIf(cellsAnimalsEntity -> cellId == cellsAnimalsEntity.getCellId());
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

    public void addOrUpdateMedCard(int animalId, int height, int weight, Development development, boolean needHospital, LocalDate dateLast) {
        AnimalEntity animalEntity = animalsRepo.findById(animalId).orElse(null);
        if (animalEntity == null) {
            return;
        }
        MedCardEntity medCardEntity = animalEntity.getMedCardById();
        if(medCardEntity == null){
            medCardEntity = new MedCardEntity(animalId, height, weight, development, needHospital, Date.valueOf(dateLast), animalEntity);
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
    public List<AnimalCellQuery> animalsCellLife(int cellId) {
        return entityManager.createQuery(
                "select new org.fit.linevich.views.AnimalCellQuery(a.name, a.kindAnimal, a.gender, m.height, m" +
                        ".weight, a.birthday) from " +
                        "AnimalEntity a join MedCardEntity m on m.animal = a join CellsAnimalsEntity c on a = c" +
                        ".animalId left " +
                        "join AnimalReceiptEntity ar on ar.animalId = a  where " +
                        "c.cellId = :cell" +
                        "  and ((not ar.animalId is null  and" +
                        "        ar.dateReceipt = c.dateBegin and" +
                        "        (a.departureDate = c.dateEnd or" +
                        "         (a.departureDate is null and c.dateEnd is null)))" +
                        "  or" +
                        "       (a.birthday = c.dateBegin and" +
                        "        (a.departureDate = c.dateEnd or" +
                        "         (a.departureDate is null and c.dateEnd is null)))) order by a.birthday",
                AnimalCellQuery.class).setParameter("cell", cellId).getResultList();
    }

    /**
     * 5-ый запрос
     */
    public List<Animal> needWarmAnimalsByAge(int age) {
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
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 6-ой запрос
     */
    public List<Animal> givenIllnessAnimals(String illness) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join IllnessAnimalsEntity as ia on a = ia.animalId " +
                        "join IllnessEntity as ill on ill = ia.illnessId " +
                        "where ill.name = :illness", AnimalEntity.class)
                .setParameter("illness", illness)
                .getResultList();
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 6-ой запрос
     */
    public List<Animal> givenVaccineAnimals(String vaccine) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join VaccineEntity as vac on a = vac.animalId " +
                        "where vac.medicineName = :vaccine", AnimalEntity.class)
                .setParameter("vaccine", vaccine)
                .getResultList();
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 7-ой запрос
     */
    public List<Animal> compatibilityKindAnimals(String kind) {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join AnimalCompatibilityEntity as co on co.animalId = a " +
                        "where co.animalCompatibilityEntityPK.animalKind = :kind", AnimalEntity.class)
                .setParameter("kind", kind)
                .getResultList();
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 7-ой запрос
     */
    public List<Animal> needRelocationAnimals() {
        List<AnimalEntity> animalEntities = animalsRepo.getAnimalEntitiesByNeedRelocationIsTrue();
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 7-ой запрос
     */
    public List<Animal> needWarmCellAnimals() {
        List<AnimalEntity> animalEntities = entityManager
                .createQuery("select a from AnimalEntity as a " +
                        "join CellsAnimalsEntity as ce on ce.animalId = a " +
                        "where a.climaticHabitat in :zones and ce.heating = false ", AnimalEntity.class)
                .setParameter("zones", Set.of(ClimaticZone.EQUATORIAL_FOREST, ClimaticZone.SUBTROPICAL_FOREST,
                        ClimaticZone.DESERT))
                .getResultList();
        return customDataMapper.toAnimalListView(animalEntities);
    }

    /**
     * 10-ый запрос
     */
    public List<Animal> needFeed(String feed, String season) {
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
                .setParameter("season", season)
                .getResultList();
        return customDataMapper.toAnimalListView(animalEntities);
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

}
