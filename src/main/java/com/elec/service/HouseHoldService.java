package com.elec.service;

import java.util.List;

import com.elec.entity.HouseHold;

public interface HouseHoldService {
	
	HouseHold save(HouseHold houseHold);
	
	List<HouseHold> search(String fullName);
}
