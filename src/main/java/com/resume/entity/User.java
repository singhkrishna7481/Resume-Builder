package com.resume.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "Resume_User")
public class User 
{	
	@Id
	@SequenceGenerator(name = "g1",allocationSize = 1,initialValue = 2001)
	@GeneratedValue(generator = "g1",strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	@Column(length = 35)
	private String name;
	
	@NonNull
	@Column(length = 45,unique = true)
	private String email;
	
	@NonNull
	private Long phno;
	
	private String[] skills;
	private String[] hobbies;
	
	
	@OneToMany(targetEntity = Education.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Education> education;
	
	@OneToMany(targetEntity = Experience.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Experience> experience;
	
	private boolean showDiv=true;
 
}
