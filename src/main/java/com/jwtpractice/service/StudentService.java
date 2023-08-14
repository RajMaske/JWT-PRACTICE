package com.jwtpractice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwtpractice.model.Student;

@Service
public class StudentService {
	private List<Student> store = new ArrayList<>();

	// Initialize the student data when the service is created
	public StudentService() {
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
		store.add(new Student(UUID.randomUUID().toString(), "Raj Maske", "Maskeraj105@gmail.com"));
	}

	// Get the list of students
	public List<Student> getStudents() {
		System.out.println(store); // Print the list of students (for debugging)
		return store;
	}
}
