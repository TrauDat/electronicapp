package com.elec.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "HouseHold")
public class HouseHold {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String fullName;

	private LocalDate dob;

	private String gender;

	private String identityCard;

	private String email;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {
			CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH})
	@JoinTable(
			name="customer_bill",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "bill_id")
			)
	private List<Bill> bill;

}
