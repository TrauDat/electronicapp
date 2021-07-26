package com.elec.dto;

import com.elec.entity.Account;
import com.elec.entity.HouseHold;

import lombok.Data;

@Data
public class BillDTO {
	private Long id;

	private String address;

	private String phone;

	private long consumptionNumOld;

	private long consumptionNumNew;

	private boolean status = true;

	private Account accountBill;

	private HouseHold houseHold;
}
