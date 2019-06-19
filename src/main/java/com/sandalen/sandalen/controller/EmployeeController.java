package com.sandalen.sandalen.controller;

import com.sandalen.sandalen.dao.DepartmentDao;
import com.sandalen.sandalen.dao.EmployeeDao;
import com.sandalen.sandalen.entities.Department;
import com.sandalen.sandalen.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao ed;

    @Autowired
    private DepartmentDao dd;

    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> emps = ed.getAll();
        model.addAttribute("emps",emps);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddPage(Model model){
        Collection<Department> departments = dd.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String add(Employee employee,Model model){
        ed.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Collection<Department> departments = dd.getDepartments();
        model.addAttribute("depts",departments);

        Employee employee = ed.get(id);
        model.addAttribute("emp",employee);

        return "emp/add";
    }

    @PutMapping("/emp")
    public String edit(Employee employee){
        ed.save(employee);
        System.out.println("employee = " + employee);
        return "redirect:/emps";
    }
}
