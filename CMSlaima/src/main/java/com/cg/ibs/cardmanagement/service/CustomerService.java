package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public interface CustomerService {

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException;

	public List<DebitCardBean> viewAllDebitCards();

	public List<CreditCardBean> viewAllCreditCards();

	public String getNewCardtype(int newCardType) throws IBSException;

	String verifyDebitcardType(BigInteger debitCardNumber) throws IBSException;

	String requestDebitCardUpgrade(BigInteger debitCardNumber, String myChoice) throws IBSException;

	void resetDebitPin(BigInteger debitCardNumber, int newPin);

	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber);

	boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException;

	void resetCreditPin(BigInteger creditCardNumber, int newPin);

	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber);

	String applyNewCreditCard(String newCardType);

	String requestDebitCardLost(BigInteger debitCardNumber) throws IBSException;

	String requestCreditCardLost(BigInteger creditCardNumber) throws IBSException;

	String verifyCreditcardType(BigInteger creditCardNumber);

	String raiseDebitMismatchTicket(String transactionId) throws IBSException;

	String raiseCreditMismatchTicket(String transactionId) throws IBSException;

	public List<DebitCardTransaction> getDebitTransactions(int dys, BigInteger debitCardNumber) throws IBSException;

	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException;

	public int getPinLength(int pin) throws IBSException;

	void getCardLength(BigInteger card) throws IBSException;

	String applyNewDebitCard(BigInteger accountNumber, String newCardType) throws IBSException;

	public boolean verifyAccountNumber(BigInteger accountNumber) throws IBSException;

	public String viewQueryStatus(String customerReferenceId) throws IBSException;

	public boolean checkDebitTransactionId(String transactionId) throws IBSException;

	public boolean verifyCreditCardTransactionId(String transactionId) throws IBSException;

	public String checkMyChoice(int myChoice) throws IBSException;

	public String checkMyChoiceGold(int myChoice) throws IBSException;

	public void checkDays(int days1) throws IBSException;

	public boolean getDebitCardStatus(BigInteger debitCardNumber);

	public boolean getCreditCardStatus(BigInteger creditCardNumber);

	String requestCreditCardUpgrade(BigInteger creditCardNumber, int myChoice) throws IBSException;

}