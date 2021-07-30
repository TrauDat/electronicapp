package com.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elec.entity.ElecPrice;
import com.elec.repository.ElecPriceRepository;
import com.elec.service.ElecPriceService;

@Service
public class ElecPriceServiceImpl implements ElecPriceService {

	@Autowired
	private ElecPriceRepository elecPriceRepository;
	
	
	@Override
	public List<ElecPrice> listElecPrice() {
		return elecPriceRepository.findAll();
	}

}
