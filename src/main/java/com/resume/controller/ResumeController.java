package com.resume.controller;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.User;
import com.resume.service.IUserService;

@Controller
public class ResumeController {
	@Autowired
	private IUserService service;

	@GetMapping("/")
	String getMethodName() {
		return "home";
	}

	@PostMapping("/add")
	public String generateResume(@ModelAttribute User user, @RequestParam(required = false) List<String> school,
			@RequestParam(required = false) List<String> degree,
			@RequestParam(required = false) List<Integer> graduationYear,
			@RequestParam(required = false) List<String> company, @RequestParam(required = false) List<String> jobTitle,
			@RequestParam(required = false) List<String> jobDescription,
			@RequestParam(required = false) List<Integer> jobYears) {
		Set<Education> eduSet = new HashSet<>();
		if (school != null) {
			for (int i = 0; i < school.size(); i++) {
				Education education = new Education(degree.get(i), school.get(i), graduationYear.get(i));
				eduSet.add(education);
				education.setUser(user);
			}
		}
		user.setEducation(eduSet); // Add to user's education list

		Set<Experience> expSet = new HashSet<>();
		if (company != null) {
			for (int i = 0; i < company.size(); i++) {
				Experience experience = new Experience(company.get(i), jobTitle.get(i), jobDescription.get(i),
						jobYears.get(i));
				expSet.add(experience);
				experience.setUser(user);
			}
		}
		user.setExperience(expSet); // Add to user's experience list

		service.addUser(user, eduSet, expSet);
		return "redirect:/view?email=" + user.getEmail();
	}

	private User user;
	private String template;

	@GetMapping("/view")
	public String view(@RequestParam(required = false) String email, Map<String, User> map) {
		user = service.getByEmailId(email);
		System.out.println(user);
		map.put("resume", user);
		return "view-resume";
	}

	@GetMapping("/demo")
	public String demo(Map<String, User> map) {
		map.put("resume", user);
		template = "resume-templates/resume";
		return template;
	}

		@GetMapping("/demo/{templateId}")
		public String demo(Map<String, User> map, @PathVariable String templateId) {
			map.put("resume", user);
			template = "resume-templates/resume-" + templateId;
			return template;
		}

	@GetMapping("/save")
	public ResponseEntity<Resource> savePDF() {
		service.generatePDF(user, template);
		File file = new File("src/main/resources/static/resume.pdf");
		Resource resource = new FileSystemResource(file);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf").body(resource);
	}

}
