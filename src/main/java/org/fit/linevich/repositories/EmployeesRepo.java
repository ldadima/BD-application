package org.fit.linevich.repositories;

import org.fit.linevich.domain.EmployeeEntity;
import org.fit.linevich.model.EmployeeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EmployeesRepo extends PagingAndSortingRepository<EmployeeEntity, Integer> {
    @Override
    Page<EmployeeEntity> findAll(Pageable pageable);

    Page<EmployeeEntity> getEmployeeEntitiesByCategory(Pageable pageable, EmployeeCategory category);

    @Query("select e from EmployeeEntity e order BY e.durationWork")
    Page<EmployeeEntity> orderByDuration(Pageable pageable);

    @Query("select e from EmployeeEntity e order BY e.age")
    Page<EmployeeEntity> orderByAge(Pageable pageable);

    @Query("select e from EmployeeEntity e order BY e.gender")
    Page<EmployeeEntity> orderByGender(Pageable pageable);

    @Query("select e from EmployeeEntity e order BY e.salary")
    Page<EmployeeEntity> orderBySalary(Pageable pageable);

    @Query("select distinct e from EmployeeEntity e join ResponsibleAnimalsEntity r on e = r.employerId join " +
            "AnimalEntity a on a = r.animalId" +
            " where a.kindAnimal = :kind and (r.dateBegin between :begin and :endD or " +
            "(r.dateEnd is null and :now between :begin and :endD) or " +
            "(not r.dateEnd is null and r.dateEnd between :begin and :endD))")
    Page<EmployeeEntity> responsibleAnimal(Pageable pageable, @Param("kind") String kindAnimal, @Param("begin") Date dateBegin,
            @Param("endD") Date dateEnd, @Param("now") Date now);

    @Query("select distinct e from EmployeeEntity e join AccessAnimalsEntity ac on e = ac.employerId join " +
            "AnimalEntity a on a = ac.animalId" +
            " where a.kindAnimal = :kind ")
    Page<EmployeeEntity> accessAnimal(Pageable pageable ,@Param("kind") String kindAnimal);
}
