package com.kiet.studentapi.controller;

import com.kiet.studentapi.model.Student;
import com.kiet.studentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    // 1. Add
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    // 2. Retrieve all
    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // 3. Search by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 4. Update
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updated) {
        Optional<Student> opt = repository.findById(id);
        if (opt.isPresent()) {
            Student s = opt.get();
            s.setStudentName(updated.getStudentName());
            s.setEmail(updated.getEmail());
            s.setCourseName(updated.getCourseName());
            s.setProgrammingScore(updated.getProgrammingScore());
            s.setMathScore(updated.getMathScore());
            s.setSkills(updated.getSkills());
            return ResponseEntity.ok(repository.save(s));
        }
        return ResponseEntity.notFound().build();
    }

    // 5. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully.");
        }
        return ResponseEntity.status(404).body("Not found.");
    }

    // 6. Display Merit List (Using Streams - Important for Viva!)
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        List<Student> allStudents = repository.findAll();
        // Using Java Streams to sort by average descending
        return allStudents.stream()
                .sorted((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()))
                .collect(Collectors.toList());
    }
}
