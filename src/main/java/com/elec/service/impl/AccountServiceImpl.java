package com.elec.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elec.entity.Account;
import com.elec.repository.AccountRepository;
import com.elec.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean authenticate(String email, String password) {
		Account account = accountRepository.findByEmail(email);
		if (account == null) {
			return false;
		} else {
			if (password.equals(account.getPassword()))
				return true;
			else
				return false;
		}

	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Optional<Account> find(Long id) {
		return accountRepository.findById(id);
	}

	@Override
	public Account update(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public void deleteInBatch(List<Account> accounts) {
		accountRepository.deleteAll(accounts);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}
