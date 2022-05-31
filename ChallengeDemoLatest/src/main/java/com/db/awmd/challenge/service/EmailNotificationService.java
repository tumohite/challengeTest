package com.db.awmd.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.web.AccountsController;

//import lombok.extern.slf4j.Slf4j;

// @Slf4j   
public class EmailNotificationService implements NotificationService {

	Logger log = LoggerFactory.getLogger(AccountsController.class);

	@Override
	public void notifyAboutTransfer(Account account, String transferDescription) {
		// THIS METHOD SHOULD NOT BE CHANGED - ASSUME YOUR COLLEAGUE WILL IMPLEMENT IT
		log.info("Sending notification to owner aof {}: {}", account.getAccountId(), transferDescription);
	}

}
