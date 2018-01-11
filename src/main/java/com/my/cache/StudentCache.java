package com.my.cache;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.my.dao.StudentJPA;
import com.my.model.Student;

@Service
public class StudentCache {

	@Resource
	private StudentJPA studentJPA;

	@CachePut(key = "#token", value = "default")
	public String add(String token, Student stu) {
		Student student = studentJPA.save(stu);
		return student.toString();
	}
}
