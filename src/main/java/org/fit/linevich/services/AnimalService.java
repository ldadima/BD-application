package org.fit.linevich.services;

import lombok.AllArgsConstructor;
import org.fit.linevich.domain.AnimalEntity;
import org.fit.linevich.mapper.CustomDataMapper;
import org.fit.linevich.model.ClimaticZone;
import org.fit.linevich.repositories.AnimalsRepo;
import org.fit.linevich.views.Animal;
import org.fit.linevich.views.AnimalCellQuery;
import org.fit.linevich.views.FullInfoAnimalQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
public class AnimalService {
    private final AnimalsRepo animalsRepo;
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
                        "where co.animalKind = :kind", AnimalEntity.class)
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
