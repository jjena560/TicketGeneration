package com.nagarro.employeeticket.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.dao.TicketRepository;
import com.nagarro.employeeticket.models.Employee;
import com.nagarro.employeeticket.models.Ticket;

@RestController
@RequestMapping("/user_rest")
public class EmployeeRestController {

	@Autowired
	EmployeeRepository empDao;

	@Autowired
	TicketRepository ticketDao;

	/**
	 * open add ticket handler
	 * 
	 * @return
	 */
	@GetMapping("/add_ticket")
	public Ticket addTicket(@RequestBody Ticket ticket) {
		Employee emp = ticket.getEmployee();
		List<Ticket> tickets = emp.getTickets();
		tickets.add(ticket);
		empDao.save(emp);
		return ticket;
	}

	/**
	 * @param principal
	 * @param model
	 * @return
	 */
	@GetMapping("/view_tickets/{id}")
	public List<Ticket> viewTickets(@PathVariable("id") long id) {

//		List<Ticket> tickets = this.ticketDao.findTicketsByEmployee(employee.getEmpId());
		List<Ticket> tickets = this.ticketDao.findTicketsByEmployee(id);

		return tickets;
	}

	/**
	 * @param id
	 * @param model
	 * @param principal
	 * @param session
	 * @return
	 */
	@GetMapping("/delete/{ticketId}")
	public String deleteTicket(@PathVariable("ticketId") long id) {

		Optional<Ticket> ticketOptional = this.ticketDao.findById(id);
		Ticket ticket = ticketOptional.get();

//		Employee emp = empDao.getEmployeeByEmployeeName(name);

		ticket.setEmployee(null);
		this.ticketDao.delete(ticket);

		return "SUCCESS";
	}

	/**
	 * @param id
	 * @param model
	 * @param principal
	 * @param session
	 * @return
	 */
	@PutMapping("/update_ticket")
	public Ticket updateTicket(@RequestBody Ticket ticket) {

		Employee employee = ticket.getEmployee();

		for (Ticket tic : employee.getTickets()) {
			if (tic.getTicketId() == ticket.getTicketId()) {
				tic.setCategory(ticket.getCategory());
				tic.setDescription(ticket.getDescription());
				ticketDao.save(tic);
			}
		}

		return ticket;
	}

}
