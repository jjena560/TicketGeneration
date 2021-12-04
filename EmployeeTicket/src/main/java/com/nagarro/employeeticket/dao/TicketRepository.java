package com.nagarro.employeeticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nagarro.employeeticket.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	// pagination
	@Query("from Ticket as c where c.employee.empId =:empId")
	public List<Ticket> findTicketsByEmployee(@Param("empId") long emId);
}
