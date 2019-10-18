package com.cg.ibs.cardmanagement.bean;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CaseIdBean {

	public CaseIdBean() {

	}

	private String caseIdTotal;
	private LocalDateTime caseTimeStamp;
	private String statusOfQuery;
	private BigInteger accountNumber;
	private BigInteger UCI;
	private String defineQuery;
	private BigInteger cardNumber;
	private String customerReferenceId;
	public String getCaseIdTotal() {
		return caseIdTotal;
	}
	public void setCaseIdTotal(String caseIdTotal) {
		this.caseIdTotal = caseIdTotal;
	}
	public LocalDateTime getCaseTimeStamp() {
		return caseTimeStamp;
	}
	public void setCaseTimeStamp(LocalDateTime caseTimeStamp) {
		this.caseTimeStamp = caseTimeStamp;
	}
	public String getStatusOfQuery() {
		return statusOfQuery;
	}
	public void setStatusOfQuery(String statusOfQuery) {
		this.statusOfQuery = statusOfQuery;
	}
	public BigInteger getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}
	public BigInteger getUCI() {
		return UCI;
	}
	public void setUCI(BigInteger uCI) {
		UCI = uCI;
	}
	public String getDefineQuery() {
		return defineQuery;
	}
	public void setDefineQuery(String defineQuery) {
		this.defineQuery = defineQuery;
	}
	public BigInteger getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCustomerReferenceId() {
		return customerReferenceId;
	}
	public void setCustomerReferenceId(String customerReferenceId) {
		this.customerReferenceId = customerReferenceId;
	}
	public CaseIdBean(String caseIdTotal, LocalDateTime caseTimeStamp, String statusOfQuery, BigInteger accountNumber,
			BigInteger uCI, String defineQuery, BigInteger cardNumber, String customerReferenceId) {
		super();
		this.caseIdTotal = caseIdTotal;
		this.caseTimeStamp = caseTimeStamp;
		this.statusOfQuery = statusOfQuery;
		this.accountNumber = accountNumber;
		UCI = uCI;
		this.defineQuery = defineQuery;
		this.cardNumber = cardNumber;
		this.customerReferenceId = customerReferenceId;
	}
	@Override
	public String toString() {
		return "CaseIdBean [caseIdTotal=" + caseIdTotal + ", caseTimeStamp=" + caseTimeStamp + ", statusOfQuery="
				+ statusOfQuery + ", accountNumber=" + accountNumber + ", UCI=" + UCI + ", defineQuery=" + defineQuery
				+ ", cardNumber=" + cardNumber + ", customerReferenceId=" + customerReferenceId + "]";
	}



}
