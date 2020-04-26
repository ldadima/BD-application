package org.fit.linevich.repositories;

import org.fit.linevich.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeesRepo extends CrudRepository<EmployeeEntity,Integer>{
}
