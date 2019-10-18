package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;

public interface BankService {

	public List<CaseIdBean> viewQueries();



	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException;

	public boolean verifyQueryId(String queryId) throws IBSException;
	public void setQueryStatus(String queryId, String newStatus);

	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException;

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException;	public String getNewQueryStatus(int newQueryStatus) throws IBSException ;

	public List<DebitCardTransaction> getDebitTransactions(int days, BigInteger debitCardNumber)throws IBSException;

	public void checkDays(int days) throws IBSException;
}