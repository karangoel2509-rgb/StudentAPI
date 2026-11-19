package com.kiet.studentapi.repository;
import com.kiet.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Integer> {}
