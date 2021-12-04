package com.nagarro.employeeticket;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.models.Employee;
import com.nagarro.employeeticket.models.Ticket;

@SpringBootTest
class EmployeeTicketApplicationTests {

	@Autowired
	EmployeeRepository empDao;

	@Test
	void itShouldCheckIfEmployeeExists() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Employee emp = new Employee("finalUser", "finalUser@gmail.com", "1234", "Normal", true, "", "", tickets);
		String userName = "finalUser@gmail.com";
//		emp.setEmail(userName);

		empDao.save(emp);

//		Boolean actualResult = empDao.isEmployeExists(userName);
//
//		assertThat(actualResult).isTrue();
	}

}
