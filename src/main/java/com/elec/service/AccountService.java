package com.elec.service;

import com.elec.entity.Account;
import com.elec.generic.GenericService;

public interface AccountService extends GenericService<Account> {

	boolean authenticate(String email, String password);
	
	Account findByEmail(String email);
	
}
