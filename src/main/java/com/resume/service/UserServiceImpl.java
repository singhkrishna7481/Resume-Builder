package com.resume.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.User;
import com.resume.pdfservice.PdfService;
import com.resume.repositories.IEducationRepo;
import com.resume.repositories.IExperienceRepo;
import com.resume.repositories.IUserRepo;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private PdfService pdfService;

	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private IEducationRepo eduRepo;
	@Autowired
	private IExperienceRepo exprRepo;

	@Override
	public void addUser(User user, Set<Education> edu, Set<Experience> expr) {
		System.out.println(user);
		userRepo.save(user);
		eduRepo.saveAll(edu);
		exprRepo.saveAll(expr);
		System.out.println("saved");
	}

	@Override
	public User viewById(long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public User getByEmailId(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void generatePDF(User resume,String template) {
		try {
			resume.setShowDiv(false);
			pdfService.generatePdf(resume,template);
			resume.setShowDiv(true);
			System.out.println(template+" :: "+resume);
		} catch (Exception e) {
			resume.setShowDiv(true);
			System.out.println("Error generating PDF: " + e.getMessage());
		}
	}
}
