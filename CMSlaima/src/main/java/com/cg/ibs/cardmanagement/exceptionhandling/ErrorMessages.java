package com.cg.ibs.cardmanagement.exceptionhandling;

public interface ErrorMessages {
	
	public static final String INC_LENGTH_PIN_MESSAGE = "Incorrect Length of pin ";
	public static final String INVALID_INPUT_MESSAGE = "Not a valid input";
	public static final String INC_LENGTH_CARD_MESSAGE = "Incorrect  length";
	public static final String DEB_CARD_NOT_EXIST_MESSAGE =" Debit Card Number does not exist";
	public static final String INC_LENGTH_ACCOUNT_MESSAGE = "Incorrect  length";
	public static final String 	INVALID_ACCOUNT_MESSAGE=" Account Number does not exist";
	public static final String CRED_CARD_NOT_EXIST_MESSAGE = " Credit Card Number does not exist";
	public static final String NO_TRANSACTIONS_MESSAGE = "NO TRANSACTIONS";
	public static final String INVALID_TRANSACTION_ID_MESSAGE = "Invalid Transaction Id";
	public static final String TRANSACTION_ID_NOT_EXIST_MESSAGE=" Transaction ID does not exist";
	public static final String INVALID_CHOICE_MESSAGE = "Not a valid input";
	public static final String LESS_DAYS_MESSAGE = "Statement can not be generated for less than 1 day";
	public static final String MORE_DAYS_MESSAGE ="Enter days less than 730";
	public static final String CARD_BLOCK_MESSAGE="Your Card is blocked ";
		
	
}
