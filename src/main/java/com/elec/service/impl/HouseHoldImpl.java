package com.elec.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elec.entity.HouseHold;
import com.elec.repository.HouseHoldRepository;
import com.elec.service.HouseHoldService;

@Service
public class HouseHoldImpl implements HouseHoldService {

	@Autowired
	private HouseHoldRepository houseHoldRepository;

	@Override
	public HouseHold save(HouseHold houseHold) {
		return houseHoldRepository.save(houseHold);
	}

	@Override
	public List<HouseHold> search(String fullName) {
		return houseHoldRepository.search(fullName);
	}

	@Override
	public Optional<HouseHold> findById(Long id) {
		return houseHoldRepository.findById(id);
	}

	@Override
	public List<HouseHold> findByAccountid(Long id) {
		return houseHoldRepository.findByAccountId(id);
	}

}
