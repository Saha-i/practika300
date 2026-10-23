package com.example.practika300.controller;

import com.example.practika300.model.Student;
import com.example.practika300.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null;
    }

    @GetMapping("/students")
    public String list(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("students", studentService.getAllStudents());
        return "students-list";
    }

    @GetMapping("/students/add")
    public String addForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("student", new Student());
        return "students-form";
    }

    @PostMapping("/students/add")
    public String add(@ModelAttribute Student student, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }
}
