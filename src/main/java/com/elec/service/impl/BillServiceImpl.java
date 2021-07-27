package com.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elec.entity.Bill;
import com.elec.repository.BillRepository;
import com.elec.service.BillService;

@Service
public class BillServiceImpl implements BillService{

	@Autowired
	private BillRepository billReposiotry;
	
	@Override
	public List<Bill> findAll() {
		return billReposiotry.findAll();
	}

	@Override
	public Bill save(Bill bill) {
		return billReposiotry.save(bill);
	}
	
}
