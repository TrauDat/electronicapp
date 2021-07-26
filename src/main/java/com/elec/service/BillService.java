package com.elec.service;

import java.util.List;

import com.elec.entity.Bill;

public interface BillService {
	List<Bill> findAll();
	
	Bill save(Bill bill);
}
