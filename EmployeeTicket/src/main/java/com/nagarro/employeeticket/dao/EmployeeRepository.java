package com.nagarro.employeeticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nagarro.employeeticket.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("Select u from Employee u where u.email = :email")
	public Employee getEmployeeByEmployeeName(@Param("email") String email);

//	@Query("Select CASE WHEN COUNT(u) > 0 THEN True else false end from employee u where u.email = :email")
//	Boolean isEmployeExists(@Param("email") String email);
}
