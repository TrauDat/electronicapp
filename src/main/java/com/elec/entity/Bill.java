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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String address;
	
	private String phone;
	
	private long consumptionNumOld;
	
	private long consumptionNumNew;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private boolean status = true;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade= {
			CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH})
	@JoinTable(
			name="customer_bill",
			joinColumns=@JoinColumn(name="bill_id"),
			inverseJoinColumns=@JoinColumn(name="customer_id")
			)
	private List<HouseHold> houseHold;
	
	public void addHouseHold(HouseHold theHouseHold) {
		if (houseHold == null) {
			houseHold = new ArrayList<>();
		}
		
		houseHold.add(theHouseHold);
	}
}
