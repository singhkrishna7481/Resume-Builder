package com.resume.service;

import java.util.Set;

import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.User;

public interface IUserService {
	
	void addUser(User user,Set<Education> edu,Set<Experience> expr);
	
	User viewById(long id);
	
	User getByEmailId(String email);
	
	void generatePDF(User resume,String template) ;
}
