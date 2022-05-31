package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FundTransfer {

	@NotNull
	@NotEmpty
	private String accountIdFrom;

	@NotNull
	@NotEmpty
	private String accountIdTo;

	@NotNull
	@NotEmpty
	private BigDecimal amountToTransfer;

	public String getAccountIdFrom() {
		return accountIdFrom;
	}

	public void setAccountIdFrom(String accountIdFrom) {
		this.accountIdFrom = accountIdFrom;
	}

	public String getAccountIdTo() {
		return accountIdTo;
	}

	public void setAccountIdTo(String accountIdTo) {
		this.accountIdTo = accountIdTo;
	}

	public BigDecimal getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(BigDecimal amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}

}
