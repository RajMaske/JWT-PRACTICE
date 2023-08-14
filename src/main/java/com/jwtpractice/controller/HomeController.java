
package com.jwtpractice.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtpractice.model.Student;
import com.jwtpractice.service.StudentService;

@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/user")
	public List<Student> getUser() {
		List<Student> students = studentService.getStudents();
		return students;
	}
	@GetMapping("/active-user")
	public String getLoggedUser(Principal principal) {
		return principal.getName();
		
	}
}
