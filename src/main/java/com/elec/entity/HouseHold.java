package com.elec.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class HouseHold {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false)
	private String id;
	
	private String firstName;

	private String lastName;

	private LocalDate dob;

	private String gender;
	
	private int identityCard;
	
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
}
