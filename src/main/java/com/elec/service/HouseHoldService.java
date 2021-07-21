package com.elec.service;

import java.util.List;
import java.util.Optional;

import com.elec.entity.HouseHold;

public interface HouseHoldService {
	
	HouseHold save(HouseHold houseHold);
	
	List<HouseHold> search(String fullName);
	
	Optional<HouseHold> findById(Long id);
	
	List<HouseHold> findByAccountid(Long id);
}
