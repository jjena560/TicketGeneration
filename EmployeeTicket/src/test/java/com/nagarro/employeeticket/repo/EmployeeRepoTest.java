package com.nagarro.employeeticket.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.models.Employee;
import com.nagarro.employeeticket.models.Ticket;

@SpringBootTest
class EmployeeRepoTest {

	@Autowired
	private EmployeeRepository empDao;

	@Test
	void isEmployeeExistsByUserName() {
		List<Ticket> tickets = new ArrayList<>();
		Employee employee = new Employee("JpUser", "dem04@gmail.com", "1234", "NORMAL", true, "", "", tickets);
		empDao.save(employee);

		String userName = "demo4@gmail.com";

		Employee actualResult = empDao.getEmployeeByEmployeeName(employee.getEmail());
		assertThat(actualResult.getEmpId()).isEqualTo(employee.getEmpId());
	}
}
