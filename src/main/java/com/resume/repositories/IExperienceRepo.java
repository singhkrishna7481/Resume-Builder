package com.resume.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resume.entity.Experience;

public interface IExperienceRepo extends JpaRepository<Experience, Long>{

}
