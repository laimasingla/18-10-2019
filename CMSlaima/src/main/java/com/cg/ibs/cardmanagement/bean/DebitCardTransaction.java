package com.cg.ibs.cardmanagement.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class DebitCardTransaction {

	private String transactionId;
	private BigInteger UCI;
	private BigInteger accountNumber;

	private BigInteger debitCardNumber;
	private LocalDateTime date;
	private BigDecimal amount;
	private String description;
	private String transactiontype;

	public DebitCardTransaction() {
		super();
	}

	@Override
	public String toString() {
		return "DebitCardTransaction [transactionId=" + transactionId + ", UCI=" + UCI + ", accountNumber="
				+ accountNumber + ", debitCardNumber=" + debitCardNumber + ", date=" + date + ", amount=" + amount
				+ ", description=" + description + ", transactiontype=" + transactiontype + "]";
	}

	public DebitCardTransaction(String transactionId, BigInteger UCI, BigInteger accountNumber,
			BigInteger debitCardNumber, LocalDateTime date, BigDecimal amount, String description,
			String transactiontype) {

		this.transactionId = transactionId;
		this.UCI = UCI;
		this.accountNumber = accountNumber;
		this.debitCardNumber = debitCardNumber;
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.transactiontype = transactiontype;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionid(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigInteger getUCI() {
		return UCI;
	}

	public void setUCI(BigInteger uCI) {
		UCI = uCI;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigInteger getDebitCardNumber() {
		return debitCardNumber;
	}

	public void setDebitCardNumber(BigInteger debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

}
