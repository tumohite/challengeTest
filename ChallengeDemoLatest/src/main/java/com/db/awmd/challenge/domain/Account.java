package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author Tushar
 *
 */
@Data
public class Account {

	@NotNull
	@NotEmpty
	private final String accountId;

	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	private BigDecimal balance;
	
//	private final ReadWriteLock rwLock;

	public Account(String accountId) {
		this.accountId = accountId;
		this.balance = BigDecimal.ZERO;
//		this.rwLock = new ReentrantReadWriteLock();
	}

	@JsonCreator
	public Account(@JsonProperty("accountId") String accountId, @JsonProperty("balance") BigDecimal balance) {
		this.accountId = accountId;
		this.balance = balance;
//		this.rwLock = new ReentrantReadWriteLock();
	}

//	public BigDecimal getBalance() {
//		this.rwLock.readLock().lock();
//		try {
//			return this.balance;
//		} finally {
//			this.rwLock.readLock().unlock();
//		}
//	}

	public BigDecimal getBalance() {
		return this.balance;
	}
	
// This method may not require as balance will be set either through constructor or deposit/withdraw action	
//	public void setBalance(BigDecimal balance) {
//		this.balance = balance;
//	}

	public String getAccountId() {
		return accountId;
	}

	/**
	 * This method is used to deposit the amount to account no
	 * 
	 * @param amount
	 */
//	public void deposit(BigDecimal amountToDeposit) {
//		this.rwLock.writeLock().lock();
//		try {
//			this.balance = getBalance().add(amountToDeposit);
//		} finally {
//			this.rwLock.writeLock().unlock();
//		}
//	}
	 	public synchronized void deposit(BigDecimal amount) {
	 		checkAmountNonNegative(amount);
	 		this.balance=getBalance().add(amount);
	 	}

	/**
	 * This method is used to withdraw the amount from given account no
	 * 
	 * @param amount
	 */
//	public void withdraw(BigDecimal amountToWithdraw) {
//		this.rwLock.writeLock().lock();
//		try {
//			if (getBalance().compareTo(amountToWithdraw) == -1) {
//				throw new IllegalArgumentException("Account does not have sufficient Balance");
//			} else {
//				this.balance = getBalance().subtract(amountToWithdraw);
//			}
//		} finally {
//			this.rwLock.writeLock().unlock();
//		}
//	}
	 	
	 public void withdraw(BigDecimal amount) {
		 checkAmountNonNegative(amount);
		 if (getBalance().compareTo(amount) == -1) {
				throw new InsufficientBalanceException("Not sufficient Balance");
		 }
		 this.balance = getBalance().subtract(amount);
	 }
	 	
	

//	public ReadWriteLock getRwLock() {
//		return rwLock;
//	}

	private void checkAmountNonNegative(BigDecimal amount) {
		if(amount.signum()==-1) {
			throw new IllegalArgumentException("Amount cannot be negative.");
		}
		
	}
}
