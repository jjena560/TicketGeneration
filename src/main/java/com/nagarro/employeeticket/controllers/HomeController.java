package com.nagarro.employeeticket.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.helper.Message;
import com.nagarro.employeeticket.models.Employee;

@Controller
public class HomeController {
	@Autowired
	EmployeeRepository empDao;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	Logger logger = org.slf4j.LoggerFactory.getLogger(HomeController.class);

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/")

	public String home(Model model) {
		model.addAttribute("title", "The Community");
		return "home";
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - The Community");
		model.addAttribute("employee", new Employee());
		return "signup";
	}

//	handler for registering the user
	@PostMapping("/do_register")
	public String registerEmp(@ModelAttribute("employee") Employee employee, Model model, HttpSession session) {

		try {
			System.out.println("Employee" + employee);

			employee.setRole("ROLE_USER");
			employee.setEnabled(true);
			employee.setImageUrl("default.png");
			employee.setPassword(passEncoder.encode(employee.getPassword()));

			Employee emp = this.empDao.save(employee);
			logger.info("Registered");

			// after saving fields should be blank so new user
			model.addAttribute("employee", new Employee());
			session.setAttribute("message", new Message("Succesfuly registered !!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("employee", employee);
			session.setAttribute("message", new Message("Something went wrong" + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

	@GetMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title", "Login Page");
		return "signin";
	}

//	
//	
//	Endpoints for POSTMAN

}
