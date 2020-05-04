package org.fit.linevich.repositories;

import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.model.EmployeeCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EmployeesRepo extends CrudRepository<EmployeeEntity, Integer> {
    @Override
    List<EmployeeEntity> findAll();

    List<EmployeeEntity> getEmployeeEntitiesByCategory(EmployeeCategory category);

    @Query("select e from EmployeeEntity e GROUP BY e.id, e.durationWork")
    List<EmployeeEntity> groupByDuration();

    @Query("select e from EmployeeEntity e GROUP BY e.id, e.age")
    List<EmployeeEntity> groupByAge();

    @Query("select e from EmployeeEntity e GROUP BY e.id, e.gender")
    List<EmployeeEntity> groupByGender();

    @Query("select e from EmployeeEntity e GROUP BY e.id, e.salary")
    List<EmployeeEntity> groupBySalary();

    @Query("select distinct e from EmployeeEntity e join ResponsibleAnimalsEntity r on e = r.employerId join " +
            "AnimalEntity a on a = r.animalId" +
            " where a.kindAnimal = :kind AND (r.dateBegin between :begin and :end or r.dateEnd between :begin and " +
            ":end)")
    List<EmployeeEntity> responsibleAnimal(@Param("kind") String kindAnimal, @Param("begin") Date dateBegin,
            @Param("end") Date dateEnd);

    @Query("select distinct e from EmployeeEntity e join AccessAnimalsEntity ac on e = ac.employerId join " +
            "AnimalEntity a on a = ac.animalId" +
            " where a.kindAnimal = :kind ")
    List<EmployeeEntity> accessAnimal(@Param("kind") String kindAnimal);
}
