package com.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elec.entity.Account;
import com.elec.repository.AccountRepository;
import com.elec.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Account save(Account entity) {
		return accountRepository.save(entity);
	}

	@Override
	public Account update(Account entity) {
		return accountRepository.save(entity);
	}

	@Override
	public void delete(Account entity) {
		accountRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		accountRepository.delete(id);
	}

	@Override
	public void deleteInBatch(List<Account> accounts) {
		accountRepository.deleteInBatch(accounts);
	}

	@Override
	public Account find(Long id) {
		return accountRepository.findOne(id);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public boolean authenticate(String email, String password) {
		Account account = accountRepository.findByEmail(email);
		if (account == null) {
			return false;
		} else {
			if (password.equals(account.getPassword())) return true;
			else return false;
		}
		
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

}
