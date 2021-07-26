package com.elec.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "Account")
@Data
public class Account {

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<HouseHold> listHouseHold;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountBill", cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,
//			CascadeType.REFRESH })
//	private List<Bill> listBill;

	@Transient
	private boolean isSendMail;

	public boolean getIsSendMail() {
		return isSendMail;
	}

	public void setIsSendMail(boolean selected) {
		this.isSendMail = selected;
	}

	public void updateHouseHold(int position, HouseHold houseHold) {
		listHouseHold.set(position, houseHold);
	}

	public void addHouseHold(HouseHold houseHold) {
		if (houseHold == null) {
			listHouseHold = new ArrayList<>();
		}

		listHouseHold.add(houseHold);

		houseHold.setAccount(this);
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", email="
				+ email + "]";
	}

}
