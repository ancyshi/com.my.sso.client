package com.my.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.model.Student;

public interface StudentJPA extends JpaRepository<Student, Long> {

}
