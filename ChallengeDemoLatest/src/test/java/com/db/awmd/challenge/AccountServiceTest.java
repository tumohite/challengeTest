package com.db.awmd.challenge;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.NotificationService;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

/**
 * @author Tushar
 *
 */
@RunWith(JMockit.class)
public class AccountServiceTest {

	@Tested
	private AccountsService accountService;

	@Injectable
	private AccountsRepository accountRepository;

	@Injectable
	private NotificationService notificationService;

	/**
	 * this will cover positive scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTransferAmount() throws Exception {
		String accountFrom = "ABC001";
		String accountTo = "XYZ001";
		BigDecimal amount = BigDecimal.valueOf(1240);
		new Expectations() {
			{
				accountRepository.getAccount(anyString);
				Account from = new Account("ABC001", BigDecimal.valueOf(5000));
				result = from;

				accountRepository.getAccount(anyString);
				Account to = new Account("XYZ001");
				result = to;

				notificationService.notifyAboutTransfer((Account) any, anyString);
			}
		};

		accountService.transferAmount(accountFrom, accountTo, amount);
	}

	/**
	 * No account exist
	 * 
	 * @throws Exception
	 */
	@Test(expected = RuntimeException.class)
	public void testTransferAmount_withNoAccountExist() throws Exception {
		String accountFrom = "PQR001";
		String accountTo = "XYZ0001";
		BigDecimal amount = BigDecimal.valueOf(1240);
		new Expectations() {
			{
				accountRepository.getAccount(anyString);
				Account from = null;
				result = from;

				accountRepository.getAccount(anyString);
				Account to = null;
				result = to;

				notificationService.notifyAboutTransfer((Account) any, anyString);
			}
		};
		accountService.transferAmount(accountFrom, accountTo, amount);
	}

	@Test(expected = RuntimeException.class)
	public void testTransferAmount_withSourceAccountInsufficientBalance() throws Exception {
		String accountFrom = "ABC001";
		String accountTo = "XYZ001";
		BigDecimal amount = BigDecimal.valueOf(1240);
		new Expectations() {
			{
				accountRepository.getAccount(anyString);
				Account from = new Account("ABC001", BigDecimal.valueOf(1200));
				result = from;

				accountRepository.getAccount(anyString);
				Account to = null;
				result = to;

				notificationService.notifyAboutTransfer((Account) any, anyString);
			}
		};
		accountService.transferAmount(accountFrom, accountTo, amount);
	}

	@Test(expected = RuntimeException.class)
	public void testTransferAmount_withAmountAsNegative() throws Exception {
		String accountFrom = "ABC001";
		String accountTo = "XYZ001";
		BigDecimal amount = BigDecimal.valueOf(-1240);
		new Expectations() {
			{
				accountRepository.getAccount(anyString);
				Account from = new Account("ABC001", BigDecimal.valueOf(3000));
				result = from;

				accountRepository.getAccount(anyString);
				Account to = new Account("XYZ001");
				result = to;

				notificationService.notifyAboutTransfer((Account) any, anyString);
			}
		};
		accountService.transferAmount(accountFrom, accountTo, amount);
	}

}
