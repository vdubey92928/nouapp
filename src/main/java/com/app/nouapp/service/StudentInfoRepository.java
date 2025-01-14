package com.app.nouapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.nouapp.model.Response;
import com.app.nouapp.model.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long>{
	
}
