package com.nagarro.employeeticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeTicketApplication {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeTicketApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTicketApplication.class, args);
		logger.debug("this is a debug message");
		logger.info("this is a info message");
		logger.warn("this is a warning message");
		logger.error("this is a error message");
	}

}
