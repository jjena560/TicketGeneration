package com.nagarro.employeeticket.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.dao.TicketRepository;
import com.nagarro.employeeticket.helper.Message;
import com.nagarro.employeeticket.models.Employee;
import com.nagarro.employeeticket.models.Ticket;

@Controller
@RequestMapping("/user")
public class EmployeeController {

	@Autowired
	EmployeeRepository empDao;

	@Autowired
	TicketRepository ticketDao;

	/**
	 * this function will call everytime and pass the values to all the handlers
	 * 
	 * @param m
	 * @param principal
	 */
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		// gives the username
		String name = principal.getName();

		// get the user using username
		Employee emp = empDao.getEmployeeByEmployeeName(name);
		model.addAttribute("employee", emp);
	}

	/**
	 * Home page for the employee
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

//		// gives the username
//		String name = principal.getName();
//
//		// get the user using username
//		Employee emp = empDao.getEmployeeByEmployeeName(name);
//		model.addAttribute("employee", emp);
		model.addAttribute("title", "Employee Dashboard");

		return "normal/user_dashboard";
	}

	/**
	 * open add ticket handler
	 * 
	 * @return
	 */
	@GetMapping("/add_ticket")
	public String openAddTicket(Model model) {
		model.addAttribute("title", "Raise Ticket");
		model.addAttribute("ticket", new Ticket());

		return "normal/add_ticket";
	}

	/**
	 * Raises the ticket and saves it to the database
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("/process_ticket")
	public String raiseTicket(@ModelAttribute Ticket ticket, Principal principal, Model model) {

		String name = principal.getName();

		Employee emp = this.empDao.getEmployeeByEmployeeName(name);
		// as this is a bidirectional mapping
		ticket.setEmployee(emp);

		System.out.println(ticket);

		// updates the list of tickets
		emp.getTickets().add(ticket);
		empDao.save(emp);

		System.out.println("Raised");

		return "normal/add_ticket";
	}

	/**
	 * @param principal
	 * @param model
	 * @return
	 */
	@GetMapping("/view_tickets")
	public String viewTickets(Principal principal, Model model) {
		String name = principal.getName();
		Employee emp = empDao.getEmployeeByEmployeeName(name);

//		List<Ticket> tickets = emp.getTickets();
		List<Ticket> tickets = this.ticketDao.findTicketsByEmployee(emp.getEmpId());
		model.addAttribute("tickets", tickets);
		model.addAttribute("title", "Employee Tickets");

		return "normal/user_tickets";
	}

	/**
	 * @param id
	 * @param model
	 * @param principal
	 * @param session
	 * @return
	 */
	@GetMapping("/delete/{ticketId}")
	public String deleteTicket(@PathVariable("ticketId") long id, Model model, Principal principal,
			HttpSession session) {

		Optional<Ticket> ticketOptional = this.ticketDao.findById(id);
		Ticket ticket = ticketOptional.get();

		String name = principal.getName();
		Employee emp = empDao.getEmployeeByEmployeeName(name);

		if (emp.getEmpId() == ticket.getEmployee().getEmpId()) {
			ticket.setEmployee(null);
			this.ticketDao.delete(ticket);
			model.addAttribute("ticket", ticket);
			model.addAttribute("title", ticket.getCategory());
		} else {
			session.setAttribute("message", new Message("Ticket Deleted", "success"));

		}

		return "redirect:/user/view_tickets";
	}

	/**
	 * @param id
	 * @param model
	 * @param principal
	 * @param session
	 * @return
	 */
	@GetMapping("/update_ticket/{ticketId}")
	public String updateTicket(@PathVariable("ticketId") long id, Model model, Principal principal,
			HttpSession session) {

		Optional<Ticket> ticketOptional = this.ticketDao.findById(id);
		Ticket ticket = ticketOptional.get();

		String name = principal.getName();
		Employee emp = empDao.getEmployeeByEmployeeName(name);

		if (emp.getEmpId() == ticket.getEmployee().getEmpId()) {
			model.addAttribute("ticket", ticket);
			model.addAttribute("title", ticket.getCategory());
			return "normal/update_ticket";
		} else {
			session.setAttribute("message", "You don't have the authority");
		}

		return "normal/user_tickets";
	}

	/**
	 * @param id
	 * @param model
	 * @param principal
	 * @param session
	 * @return
	 */
	@GetMapping("/ticket/{ticketId}")
	public String ticketDetails(@PathVariable("ticketId") long id, Model model, Principal principal,
			HttpSession session) {

		Optional<Ticket> ticketOptional = this.ticketDao.findById(id);
		Ticket ticket = ticketOptional.get();

		String name = principal.getName();
		Employee emp = empDao.getEmployeeByEmployeeName(name);

		model.addAttribute("ticket", ticket);
		model.addAttribute("title", ticket.getCategory());

		return "normal/update_ticket";
	}

	/**
	 * updates the ticket
	 * 
	 * @param ticket
	 * @param principal
	 * @param model
	 * @return
	 */
	@PostMapping("/do_update_ticket")
	public String doUpdateTicket(@ModelAttribute Ticket ticket, Principal principal, Model model) {

		String name = principal.getName();
		Employee emp = this.empDao.getEmployeeByEmployeeName(name);

		ticket.setEmployee(emp);

		this.ticketDao.save(ticket);

//		Ticket t = this.ticketDao.getById(ticket.getTicketId());
//		// as this is a bidirectional mapping
//
//		System.out.println(ticket.getCategory());
//		System.out.println(ticket.getTicketId());
//
//		t.setCategory(ticket.getCategory());
//		t.setDescription(ticket.getDescription());

		System.out.println(ticket.toString());

		// updates the list of tickets

		System.out.println("updated");

		return "normal/user_tickets";

	}

//	

//	
//	Endpoints for postman

	@GetMapping("/view_tickets_post")
	public String viewTickets_postman(Principal principal, Model model) {
		String name = principal.getName();
		Employee emp = empDao.getEmployeeByEmployeeName(name);

//		List<Ticket> tickets = emp.getTickets();
		List<Ticket> tickets = this.ticketDao.findTicketsByEmployee(emp.getEmpId());
		model.addAttribute("tickets", tickets);
		model.addAttribute("title", "Employee Tickets");

		return "normal/user_tickets";
	}

}
