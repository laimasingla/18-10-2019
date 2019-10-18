package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ibs.cardmanagement.bean.AccountBean;
import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.exceptionhandling.ErrorMessages;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public class CustomerServiceImpl implements CustomerService {

	public void getCardLength(BigInteger card) throws IBSException {
		int length = card.toString().length();
		if (length != 16)
			throw new IBSException(ErrorMessages.INC_LENGTH_PIN_MESSAGE);

	}

	CustomerDao customerDao;

	public CustomerServiceImpl() {
		customerDao = new CardManagementDaoImpl();

	}
	CaseIdBean caseIdObj = new CaseIdBean();
	CustomerBean customObj = new CustomerBean();
	AccountBean accountObj = new AccountBean();
	Random random = new Random();

	String caseIdGenOne = " ";
	static String caseIdTotal = " ";
	static int caseIdGenTwo = 0;
	LocalDateTime timestamp;
	LocalDateTime fromDate;
	LocalDateTime toDate;
	static String setCardType = null;
	static String uniqueID = null;
	static String customerReferenceID = null;

	String addToQueryTable(String caseIdGenOne) {
		String caseIdGenTw = String.format("%04d", caseIdGenTwo);
		caseIdTotal = caseIdGenOne + caseIdGenTw;
		caseIdGenTwo++;
		return caseIdTotal;
	}

	@Override
	public String applyNewDebitCard(BigInteger accountNumber, String newCardType) throws IBSException {

		

		caseIdGenOne = "ANDC";

		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + accountNumber.toString().substring(4));
		timestamp = LocalDateTime.now();
		caseIdObj.setDefineQuery(newCardType);
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		BigInteger uci = customerDao.getNDCUci(accountNumber);
		caseIdObj.setUCI(uci);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.newDebitCard(caseIdObj, accountNumber);
		return customerReferenceID;

	}

	public String getNewCardtype(int newCardType) throws IBSException {
		String cardType = newCardType + "";
		Pattern pattern = Pattern.compile("[123]");
		Matcher matcher = pattern.matcher(cardType);
		if (!(matcher.find() && matcher.group().equals(cardType)))
			throw new IBSException(ErrorMessages.INVALID_INPUT_MESSAGE);

		switch (newCardType) {
		case 1:

			setCardType = "Platinum";
			break;
		case 2:
			setCardType = "Gold";
			break;
		case 3:
			setCardType = "Silver";
			break;

		}
		return setCardType;
	}

	public static String getSetCardType() {
		return setCardType;
	}

	public static void setSetCardType(String setCardType) {
		CustomerServiceImpl.setCardType = setCardType;
	}

	@Override
	public String applyNewCreditCard(String newcardType) {
	
		caseIdGenOne = "ANCC";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + String.format("%04d", random.nextInt(10000000)));
		timestamp = LocalDateTime.now();
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setDefineQuery(newcardType);
		caseIdObj.setUCI(customerDao.getUci());
		customerDao.newCreditCard(caseIdObj);
		return customerReferenceID;
	}

	@Override
	public String requestDebitCardLost(BigInteger debitCardNumber) throws IBSException {
		caseIdGenOne = "RDCL";
		
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal.concat(debitCardNumber.toString().substring(9)));

		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(customerDao.getDebitUci(debitCardNumber));
		caseIdObj.setCardNumber(debitCardNumber);
		caseIdObj.setDefineQuery("Block");
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.requestDebitCardLost(caseIdObj, debitCardNumber);
		return (customerReferenceID);

	}

	@Override
	public String requestCreditCardLost(BigInteger creditCardNumber) throws IBSException {

	

		caseIdGenOne = "RCCL";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + creditCardNumber.toString().substring(9));
		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(customerDao.getCreditUci(creditCardNumber));
		caseIdObj.setCardNumber(creditCardNumber);
		caseIdObj.setDefineQuery("Blocked");
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.requestCreditCardLost(caseIdObj, creditCardNumber);
		return (customerReferenceID);
	}

	public String raiseDebitMismatchTicket(String transactionId) throws IBSException {

	
		caseIdGenOne = "RDMT";

		timestamp = LocalDateTime.now();
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + "0" + transactionId);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setAccountNumber(customerDao.getDMAccountNumber(transactionId));
		caseIdObj.setUCI(customerDao.getDMUci(transactionId));
		caseIdObj.setCardNumber(customerDao.getDebitCardNumber(transactionId));
		caseIdObj.setDefineQuery("Transaction ID" + transactionId);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.raiseDebitMismatchTicket(caseIdObj, transactionId);
		return (customerReferenceID);

	}

	public List<DebitCardBean> viewAllDebitCards() {

		return customerDao.viewAllDebitCards();
	}

	@Override
	public List<CreditCardBean> viewAllCreditCards() {

		return customerDao.viewAllCreditCards();

	}

	public String verifyDebitcardType(BigInteger debitCardNumber) throws IBSException {

		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			String type = customerDao.getdebitCardType(debitCardNumber);
			return type;
		} else {

			throw new NullPointerException("Debit card not found");
		}
	}

	@Override
	public String requestDebitCardUpgrade(BigInteger debitCardNumber, String myChoice) throws IBSException {
		String status = customerDao.getDebitCardStatus(debitCardNumber);
		if (status.equals("Blocked")) {
			throw new IBSException(ErrorMessages.CARD_BLOCK_MESSAGE);
		} else {
			

			caseIdGenOne = "RDCU";
			timestamp = LocalDateTime.now();
			caseIdTotal = addToQueryTable(caseIdGenOne);
			customerReferenceID = (caseIdTotal + debitCardNumber.toString().substring(9));
			caseIdObj.setCaseIdTotal(caseIdTotal);
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setCardNumber(debitCardNumber);
			caseIdObj.setCustomerReferenceId(customerReferenceID);
			caseIdObj.setUCI(customerDao.getDebitUci(debitCardNumber));
			caseIdObj.setDefineQuery(myChoice);
			caseIdObj.setAccountNumber(customerDao.getAccountNumber(debitCardNumber));
			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
			return (customerReferenceID);
		}
	}

	public boolean getDebitCardStatus(BigInteger debitCardNumber) {
		boolean status = false;
		String existingStatus = customerDao.getDebitCardStatus(debitCardNumber);
		if (!existingStatus.contains("Blocked")) {
			status = true;
		}
		return status;
	}

	public boolean getCreditCardStatus(BigInteger creditCardNumber) {
		boolean status = false;
		String existingStatus = customerDao.getCreditCardStatus(creditCardNumber);
		if (!existingStatus.contains("Blocked")) {
			status = true;
		}
		return status;
	}

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException {
		String debitCardNum = debitCardNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{16}");
		Matcher matcher = pattern.matcher(debitCardNum);
		if (!(matcher.find() && matcher.group().equals(debitCardNum)))
			throw new IBSException(ErrorMessages.INC_LENGTH_CARD_MESSAGE);
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (!check)
			throw new IBSException(ErrorMessages.DEB_CARD_NOT_EXIST_MESSAGE);
		return (check);

	}

	public boolean verifyAccountNumber(BigInteger accountNumber) throws IBSException {
		String accountNum = accountNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{11}");
		Matcher matcher = pattern.matcher(accountNum);
		if (!(matcher.find() && matcher.group().equals(accountNum)))
			throw new IBSException(ErrorMessages.INC_LENGTH_ACCOUNT_MESSAGE);
		boolean check = customerDao.verifyAccountNumber(accountNumber);
		if (!check)
			throw new IBSException(ErrorMessages.INVALID_ACCOUNT_MESSAGE);
		return (check);
	}

	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber) {
		if (pin == customerDao.getDebitCardPin(debitCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public void resetDebitPin(BigInteger debitCardNumber, int newPin) {

		customerDao.setNewDebitPin(debitCardNumber, newPin);

	}

	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException {
		String creditCardNum = creditCardNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{16}");
		Matcher matcher = pattern.matcher(creditCardNum);
		if (!(matcher.find() && matcher.group().equals(creditCardNum)))
			throw new IBSException(ErrorMessages.INC_LENGTH_CARD_MESSAGE);
		boolean check1 = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (!check1)
			throw new IBSException(ErrorMessages.CRED_CARD_NOT_EXIST_MESSAGE);
		return (check1);
	}

	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber) {

		if (pin == customerDao.getCreditCardPin(creditCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public void resetCreditPin(BigInteger creditCardNumber, int newPin) {

		customerDao.setNewCreditPin(creditCardNumber, newPin);

	}

	@Override
	public String requestCreditCardUpgrade(BigInteger creditCardNumber, int myChoice) throws IBSException {
		String Status = customerDao.getCreditCardStatus(creditCardNumber);

		if (Status.equals("Blocked")) {
			throw new IBSException(ErrorMessages.CARD_BLOCK_MESSAGE);
		} else {
		
			caseIdGenOne = "RCCU";
			timestamp = LocalDateTime.now();

			caseIdTotal = addToQueryTable(caseIdGenOne);
			customerReferenceID = (caseIdTotal + creditCardNumber.toString().substring(9));
			caseIdObj.setCaseIdTotal(caseIdTotal);
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setCardNumber(creditCardNumber);
			caseIdObj.setUCI(customerDao.getCreditUci(creditCardNumber));
			if (myChoice == 1) {
				caseIdObj.setDefineQuery("Gold");
				customerDao.requestCreditCardUpgrade(caseIdObj, creditCardNumber);
				return (customerReferenceID);
			} else if (myChoice == 2) {
				caseIdObj.setDefineQuery("Platinum");
				customerDao.requestDebitCardUpgrade(caseIdObj, creditCardNumber);
				return (customerReferenceID);
			} else {
				return ("Choose a valid option");
			}
		}

	}

	@Override
	public String verifyCreditcardType(BigInteger creditCardNumber) {
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			String type = customerDao.getcreditCardType(creditCardNumber);
			return type;
		} else {

			throw new NullPointerException("Credit Card not found");
		}

	}

	@Override
	public String raiseCreditMismatchTicket(String transactionId) throws IBSException {

	
		caseIdGenOne = "RCMT";

		timestamp = LocalDateTime.now();
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + "0" + transactionId);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(customerDao.getCMUci(transactionId));
		caseIdObj.setDefineQuery("Transaction ID:" + transactionId);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.raiseCreditMismatchTicket(caseIdObj, transactionId);
		return (customerReferenceID);

	}

	public List<DebitCardTransaction> getDebitTransactions(int days, BigInteger debitCardNumber) throws IBSException {

		List<DebitCardTransaction> debitCardBeanTrns = customerDao.getDebitTrans(days, debitCardNumber);
		if (debitCardBeanTrns.isEmpty())
			throw new IBSException(ErrorMessages.NO_TRANSACTIONS_MESSAGE);
		return customerDao.getDebitTrans(days, debitCardNumber);

	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException {

		List<CreditCardTransaction> creditCardBeanTrns = customerDao.getCreditTrans(days, creditCardNumber);
		if (creditCardBeanTrns.isEmpty())
			throw new IBSException(ErrorMessages.NO_TRANSACTIONS_MESSAGE);
		return customerDao.getCreditTrans(days, creditCardNumber);

	}

	@Override
	public int getPinLength(int pin) throws IBSException {
		int count = 0;
		while (pin != 0) {
			pin = pin / 10;
			++count;
		}
		if (count != 4)
			throw new IBSException(ErrorMessages.INC_LENGTH_PIN_MESSAGE);
		return count;
	}

	@Override
	public String viewQueryStatus(String customerReferenceId) throws IBSException {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdObj.setCustomerReferenceId(customerReferenceId);

		String currentQueryStatus = customerDao.getCustomerReferenceId(caseIdObj, customerReferenceId);
		if (currentQueryStatus == null)
			throw new IBSException(ErrorMessages.INVALID_TRANSACTION_ID_MESSAGE);

		return currentQueryStatus;

	}

	@Override
	public boolean checkDebitTransactionId(String transactionId) throws IBSException {
		boolean transactionResult = customerDao.verifyDebitTransactionId(transactionId);
		if (!transactionResult)
			throw new IBSException(ErrorMessages.TRANSACTION_ID_NOT_EXIST_MESSAGE);

		return transactionResult;
	}

	@Override
	public boolean verifyCreditCardTransactionId(String transactionId) throws IBSException {
		boolean transactionResult = customerDao.verifyCreditTransactionId(transactionId);
		if (!transactionResult)
			throw new IBSException(ErrorMessages.TRANSACTION_ID_NOT_EXIST_MESSAGE);

		return transactionResult;
	}

	@Override
	public String checkMyChoice(int myChoice) throws IBSException {
		String cardType = myChoice + "";
		Pattern pattern = Pattern.compile("[12]");
		Matcher matcher = pattern.matcher(cardType);
		if (!(matcher.find() && matcher.group().equals(cardType)))
			throw new IBSException(ErrorMessages.INVALID_CHOICE_MESSAGE);

		switch (myChoice) {
		case 1:
			setCardType = "Gold";
			break;
		case 2:
			setCardType = "Platinum";
			break;

		}
		return setCardType;

	}

	@Override
	public String checkMyChoiceGold(int myChoice) throws IBSException {
		String cardType = myChoice + "";
		Pattern pattern = Pattern.compile("[2]");
		Matcher matcher = pattern.matcher(cardType);
		if (!(matcher.find() && matcher.group().equals(cardType)))
			throw new IBSException(ErrorMessages.INVALID_CHOICE_MESSAGE);
		return ("Platinum");
	}

	@Override
	public void checkDays(int days1) throws IBSException {
		if (days1 < 1) {

			throw new IBSException(ErrorMessages.LESS_DAYS_MESSAGE);

		} else if (days1 >= 730) {

			throw new IBSException(ErrorMessages.MORE_DAYS_MESSAGE);
		}

	}

}