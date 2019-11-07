package com.ibm.entity;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String contactNumber;
	
	private String regCode;
	
	private String status;
	
	private String roles;
	
	private String linkedinUrl;
	
	private LocalDateTime createDate;
	
}
