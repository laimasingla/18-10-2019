package com.cg.ibs.cardmanagement.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class CreditCardBean {

	private BigInteger creditCardNumber;
	private String creditCardStatus;
	private String nameOnCreditCard;
	private int CreditCvvNum;
	private int creditCurrentPin;
	private LocalDate creditDateOfExpiry;
	private BigInteger UCI;
	private String creditCardType;
	private int creditScore;
	private BigDecimal creditLimit;
	private double income;
	
	
	public CreditCardBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CreditCardBean(BigInteger creditCardNumber, String creditCardStatus, String nameOnCreditCard,
			int creditCvvNum, int creditCurrentPin, LocalDate creditDateOfExpiry, BigInteger uCI2, String creditCardType,
			int creditScore, BigDecimal creditLimit, double income) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.creditCardStatus = creditCardStatus;
		this.nameOnCreditCard = nameOnCreditCard;
		CreditCvvNum = creditCvvNum;
		this.creditCurrentPin = creditCurrentPin;
		this.creditDateOfExpiry = creditDateOfExpiry;
		uCI2 = uCI2;
		this.creditCardType = creditCardType;
		this.creditScore = creditScore;
		this.creditLimit = creditLimit;
		this.income = income;
	}


	public BigInteger getCreditCardNumber() {
		return creditCardNumber;
	}


	public void setCreditCardNumber(BigInteger creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}


	public String getCreditCardStatus() {
		return creditCardStatus;
	}


	public void setCreditCardStatus(String creditCardStatus) {
		this.creditCardStatus = creditCardStatus;
	}


	public String getNameOnCreditCard() {
		return nameOnCreditCard;
	}


	public void setNameOnCreditCard(String nameOnCreditCard) {
		this.nameOnCreditCard = nameOnCreditCard;
	}


	public int getCreditCvvNum() {
		return CreditCvvNum;
	}


	public void setCreditCvvNum(int creditCvvNum) {
		CreditCvvNum = creditCvvNum;
	}


	public int getCreditCurrentPin() {
		return creditCurrentPin;
	}


	public void setCreditCurrentPin(int creditCurrentPin) {
		this.creditCurrentPin = creditCurrentPin;
	}


	public LocalDate getCreditDateOfExpiry() {
		return creditDateOfExpiry;
	}


	public void setCreditDateOfExpiry(LocalDate creditDateOfExpiry) {
		this.creditDateOfExpiry = creditDateOfExpiry;
	}


	public BigInteger getUCI() {
		return UCI;
	}


	public void setUCI(BigInteger UCI) {
		UCI = UCI;
	}


	public String getCreditCardType() {
		return creditCardType;
	}


	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}


	public int getCreditScore() {
		return creditScore;
	}


	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}


	public BigDecimal getCreditLimit() {
		return creditLimit;
	}


	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}


	public double getIncome() {
		return income;
	}


	public void setIncome(double income) {
		this.income = income;
	}


	@Override
	public String toString() {
		return "CreditCardBean [creditCardNumber=" + creditCardNumber + ", creditCardStatus=" + creditCardStatus
				+ ", nameOnCreditCard=" + nameOnCreditCard + ", CreditCvvNum=" + CreditCvvNum + ", creditCurrentPin="
				+ creditCurrentPin + ", creditDateOfExpiry=" + creditDateOfExpiry + ", UCI=" + UCI + ", creditCardType="
				+ creditCardType + ", creditScore=" + creditScore + ", creditLimit=" + creditLimit + ", income="
				+ income + "]";
	}

}
