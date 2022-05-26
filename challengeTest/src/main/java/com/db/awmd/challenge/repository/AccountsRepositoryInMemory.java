package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.web.AccountsController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	Logger log = LoggerFactory.getLogger(AccountsRepositoryInMemory.class);

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		Account account = accounts.get(accountId);
		if (account == null) {
			throw new IllegalArgumentException("Account No " + accountId + " does not Exist");
		}
		return account;
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public boolean transferAmount(String accountIdFrom, String accountIdTo, BigDecimal amountToTransfer) {
		try {
			// validate the source Account No
			Account accountFrom = getAccount(accountIdFrom);
			// validate the source Account balance
			if (accountFrom.getBalance().compareTo(amountToTransfer) == -1) {
				throw new IllegalArgumentException(
						"Source Account with Account No " + accountIdFrom + " does not have enough balance");
			}
			// validate the destination Account No
			Account accountTo = getAccount(accountIdTo);
			// UUID uuid = UUID.randomUUID(); // we can create Util to generate Unique id
			// for transaction
			accountFrom.withdraw(amountToTransfer);
			accountTo.deposit(amountToTransfer);
			return true;
		} catch (Exception ex) {
			throw new RuntimeException("Transaction failed");

		}

	}

}
