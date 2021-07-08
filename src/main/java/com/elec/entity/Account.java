package com.elec.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "Account")
@Data
public class Account{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	private String firstName;

	private String lastName;

	private LocalDate dob;

	private String gender;

	private String role;

	private String email;

	private String password;

	@Transient
	private boolean isSendMail;
	
	public boolean getIsSendMail() {
        return isSendMail;
    }
	
	public void setIsSendMail(boolean selected) {
        this.isSendMail = selected;
    }

	@Override
	public String toString() {
		return "Account [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", email="
				+ email + "]";
	}
}
