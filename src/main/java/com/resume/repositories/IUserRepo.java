package com.resume.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resume.entity.User;


public interface IUserRepo extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
