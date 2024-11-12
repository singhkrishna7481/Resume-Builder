package com.resume.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resume.entity.Education;

public interface IEducationRepo extends JpaRepository<Education, Long>{

}
