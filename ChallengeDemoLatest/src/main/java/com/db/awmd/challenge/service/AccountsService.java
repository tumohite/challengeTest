package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
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
	 * @throws Exception
	 */
	public void transferAmount(String accountIdFrom, String accountIdTo, BigDecimal amountToTransfer) throws Exception {
		try {
			// validate the source Account No
			Account accountFrom = this.accountsRepository.getAccount(accountIdFrom);
			// validate the source Account balance
			if (null != accountFrom && accountFrom.getBalance().compareTo(amountToTransfer) == -1) {
				throw new InsufficientBalanceException(
						"Source Account with Account No " + accountIdFrom + " does not have enough balance");
			}
			// validate the destination Account No
			Account accountTo = this.accountsRepository.getAccount(accountIdTo);

//				accountFrom.getRwLock().writeLock().lock();
//				accountTo.getRwLock().writeLock().lock();
//				try {
//					accountFrom.withdraw(amountToTransfer);
//					accountTo.deposit(amountToTransfer);
//				} finally {
//					accountFrom.getRwLock().writeLock().unlock();
//					accountTo.getRwLock().writeLock().unlock();
//				}
			// In multithreaded environment to avoid deadlock when transfer from A to B and
			// B to A is happening
			if (null != accountFrom && null != accountTo) {
				if (accountFrom.getAccountId().compareTo(accountTo.getAccountId()) > 0) {
					synchronized (accountFrom) {
						synchronized (accountTo) {
							accountFrom.withdraw(amountToTransfer);
							accountTo.deposit(amountToTransfer);
						}
					}
				} else if (accountFrom.getAccountId().compareTo(accountTo.getAccountId()) < 0) {
					synchronized (accountTo) {
						synchronized (accountFrom) {
							accountFrom.withdraw(amountToTransfer);
							accountTo.deposit(amountToTransfer);
						}

					}
				}
				notificationService.notifyAboutTransfer(null, "Transaction Successful");
			} else {
				throw new IllegalArgumentException("Invalid Source and destination account Id");
			}

		} catch (Exception ex) {
			notificationService.notifyAboutTransfer(null, ex.getMessage());
			throw new RuntimeException("Transaction failed", ex);
		}
	}

}
