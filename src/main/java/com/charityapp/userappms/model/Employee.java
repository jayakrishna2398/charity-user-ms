package com.charityapp.userappms.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name="active")
	private Boolean active = false;
	@Column(name="locked")
	private Boolean locked = false;
	@Column(name="blocked")
	private Boolean blocked = false;
	@Column(name="created_date")
	private LocalDate createdDate = LocalDate.now();
	@Column(name="modified_date")
	private LocalDate modifiedDate = LocalDate.now();
}
