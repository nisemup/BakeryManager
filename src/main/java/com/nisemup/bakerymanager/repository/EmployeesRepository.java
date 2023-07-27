package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

}
