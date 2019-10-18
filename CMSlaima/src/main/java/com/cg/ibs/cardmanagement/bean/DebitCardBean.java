package com.cg.ibs.cardmanagement.bean;

import java.math.BigInteger;
import java.time.LocalDate;

public class DebitCardBean {

	private BigInteger accountNumber;
	private BigInteger debitCardNumber;
	private String debitCardStatus;
	private String nameOnDebitCard;
	private int debitCvvNum;
	private int debitCurrentPin;
	private LocalDate debitDateOfExpiry;
	private String debitCardType;

	public DebitCardBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DebitCardBean(BigInteger accountNumber, BigInteger debitCardNumber, String debitCardStatus,
			String nameOnDebitCard, int debitCvvNum, int debitCurrentPin, LocalDate debitDateOfExpiry, 
			String debitCardType) {
		super();
		this.accountNumber = accountNumber;
		this.debitCardNumber = debitCardNumber;
		this.debitCardStatus = debitCardStatus;
		this.nameOnDebitCard = nameOnDebitCard;
		this.debitCvvNum = debitCvvNum;
		this.debitCurrentPin = debitCurrentPin;
		this.debitDateOfExpiry = debitDateOfExpiry;
		this.debitCardType = debitCardType;
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

	public String getDebitCardStatus() {
		return debitCardStatus;
	}

	public void setDebitCardStatus(String debitCardStatus) {
		this.debitCardStatus = debitCardStatus;
	}

	public String getNameOnDebitCard() {
		return nameOnDebitCard;
	}

	public void setNameOnDebitCard(String nameOnDebitCard) {
		this.nameOnDebitCard = nameOnDebitCard;
	}

	public int getDebitCvvNum() {
		return debitCvvNum;
	}

	public void setDebitCvvNum(int debitCvvNum) {
		this.debitCvvNum = debitCvvNum;
	}

	public int getDebitCurrentPin() {
		return debitCurrentPin;
	}

	public void setDebitCurrentPin(int debitCurrentPin) {
		this.debitCurrentPin = debitCurrentPin;
	}

	public LocalDate getDebitDateOfExpiry() {
		return debitDateOfExpiry;
	}

	public void setDebitDateOfExpiry(LocalDate debitDateOfExpiry) {
		this.debitDateOfExpiry = debitDateOfExpiry;
	}

	
	public String getDebitCardType() {
		return debitCardType;
	}

	public void setDebitCardType(String debitCardType) {
		this.debitCardType = debitCardType;
	}

	@Override
	public String toString() {
		return "DebitCardBean [accountNumber=" + accountNumber + ", debitCardNumber=" + debitCardNumber
				+ ", debitCardStatus=" + debitCardStatus + ", nameOnDebitCard=" + nameOnDebitCard + ", debitCvvNum="
				+ debitCvvNum + ", debitCurrentPin=" + debitCurrentPin + ", debitDateOfExpiry=" + debitDateOfExpiry
				+ ", debitCardType=" + debitCardType + "]";
	}

	public void setUCI(String uCI) {
	}


}