package com.elec.service;

import java.util.List;
import java.util.Optional;

import com.elec.entity.Account;

public interface AccountService {

	boolean authenticate(String email, String password);
	
	Account findByEmail(String email);
	
	Account save(Account account);
	
	Optional<Account> find(Long id);
	
	Account update(Account account);
	
	void deleteInBatch(List<Account> accounts);
	
	List<Account> findAll();
	
}
