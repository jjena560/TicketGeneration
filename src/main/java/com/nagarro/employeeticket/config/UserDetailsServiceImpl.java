package com.nagarro.employeeticket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nagarro.employeeticket.dao.EmployeeRepository;
import com.nagarro.employeeticket.models.Employee;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	EmployeeRepository empDao;

	/**
	 * fetching employee from database
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Employee emp = empDao.getEmployeeByEmployeeName(username);

		if (emp == null) {
			throw new UsernameNotFoundException("Employee Not Found");
		}

		CustomUserDetails customUserDetails = new CustomUserDetails(emp);

		return customUserDetails;
	}

}
