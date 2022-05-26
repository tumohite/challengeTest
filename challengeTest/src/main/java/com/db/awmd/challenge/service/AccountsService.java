package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tushar
 *
 */
@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	/**
	 * @param accountIdFrom
	 * @param accountIdTo
	 * @param amount
	 */
	public void transferAmount(String accountIdFrom, String accountIdTo, BigDecimal amount) {
		try {
			boolean result = accountsRepository.transferAmount(accountIdFrom, accountIdTo, amount);
			if (result) {
				notificationService.notifyAboutTransfer(null, accountIdTo);
			}
		} catch (Exception ex) {

		}
	}

}
