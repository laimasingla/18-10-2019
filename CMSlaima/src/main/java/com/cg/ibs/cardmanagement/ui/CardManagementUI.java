package com.cg.ibs.cardmanagement.ui;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.service.BankService;
import com.cg.ibs.cardmanagement.service.BankServiceClassImpl;
import com.cg.ibs.cardmanagement.service.CustomerService;
import com.cg.ibs.cardmanagement.service.CustomerServiceImpl;

public class CardManagementUI {

	static BigInteger accountNumber = null;
	static Scanner scan;
	static BigInteger debitCardNumber = null;
	static BigInteger creditCardNumber = null;
	static int userInput = -1;
	static int ordinal = -1;
	static int pin=-1;
	static String type = null;
	static String transactionId;
	static boolean success = false;
	static int myChoice = -1;

	boolean check = false;
	static String customerReferenceId = null;
	static int newCardType = -1;
	static int days = 0;
	CustomerService customService = new CustomerServiceImpl();
	BankService bankService = new BankServiceClassImpl();

	
	public void doIt(){
		while (true) {
			success = false;
			System.out.println("Welcome to card management System");
			System.out.println("Enter 1 to login as a customer");
			System.out.println("Enter 2 to login as a bank admin");
			
		
			while (!success) {

				try {

					userInput = scan.nextInt();
					success = true;
				} catch (InputMismatchException wrongFormat) {
					scan.next();
					System.out.println("Enter between 1 or 2");

				}
			}

			if (userInput == 1) {

				System.out.println("You are logged in as a customer");
				CustomerMenu choice = null;
				while (choice != CustomerMenu.CUSTOMER_LOG_OUT) {

					System.out.println("Menu");
					System.out.println("--------------------");
					System.out.println("Choice");
					System.out.println("--------------------");
					for (CustomerMenu mmenu : CustomerMenu.values()) {
						System.out.println(mmenu.ordinal() + "\t" + mmenu);
					}
					System.out.println("Choice");
					success = false;
					while (!success) {

						try {
							ordinal = scan.nextInt();
							success = true;
						} catch (InputMismatchException wrongFormat) {
							scan.next();
							System.out.println("Choose a valid option");
						}
					}
					if (ordinal >= 0 && ordinal < 16) {
						choice = CustomerMenu.values()[ordinal];

						
						switch (choice) {

						case LIST_EXISTING_DEBIT_CARDS:

							listExistingDebitCards();
							break;

						case LIST_EXISTING_CREDIT_CARDS:

							listExistingCreditCards();
							break;

						case APPLY_NEW_DEBIT_CARD:
							applyNewDebitCard();
							break;
						case APPLY_NEW_CREDIT_CARD:
							applyNewCreditCard();
							break;

						case UPGRADE_EXISTING_DEBIT_CARD:

							upgradeExistingDebitCard();
							break;
						case UPGRADE_EXISTING_CREDIT_CARD:
							upgradeExistingCreditCard();
							break;

						case RESET_DEBIT_CARD_PIN:
							resetDebitCardPin();

							break;
						case RESET_CREDIT_CARD_PIN:
							resetCreditCardPin();
							break;

						case REPORT_DEBIT_CARD_LOST:

							reportDebitCardLost();

							break;

						case REPORT_CREDIT_CARD_LOST:

							reportCreditCardLost();
							break;

						case REQUEST_DEBIT_CARD_STATEMENT:
							requestDebitCardStatement();

							break;
						case REQUEST_CREDIT_CARD_STATEMENT:
							requestCreditCardStatement();
							break;
						case REPORT_DEBITCARD_STATEMENT_MISMATCH:

							reportDebitStatementMismatch();
							break;
						case REPORT_CREDITCARD_STATEMENT_MISMATCH:
							reportCreditStatementMismatch();
							break;

						case CUSTOMER_LOG_OUT:
							System.out.println("LOGGED OUT");
							break;
						case VIEW_QUERY_STATUS:
							viewQueryStatus();

							break;
						}
					}

				}
			} else {
				if (userInput == 2) {

					System.out.println("You are logged in as a Bank Admin");
					BankMenu cchoice = null;
					while (cchoice != BankMenu.BANK_LOG_OUT) {
						System.out.println("Menu");
						System.out.println("--------------------");
						System.out.println("Choice");
						System.out.println("--------------------");
						for (BankMenu mmenu : BankMenu.values()) {
							System.out.println(mmenu.ordinal() + "\t" + mmenu);
						}
						System.out.println("Choice");
						success = false;
						while (!success) {
							try {
								ordinal = scan.nextInt();
								success = true;
							} catch (InputMismatchException wrongFormat) {
								scan.next();
								System.out.println("Enter a valid  option");
							}
						}

						if (ordinal >= 0 && ordinal < BankMenu.values().length) {
							cchoice = BankMenu.values()[ordinal];

							switch (cchoice) {

							case LIST_QUERIES:

								listPendingQueries();
								break;

							case REPLY_QUERIES:
								replyQueries();
								break;
							case VIEW_DEBIT_CARD_STATEMENT:
								viewBankDebitCardStatement();

								break;
							case VIEW_CREDIT_CARD_STATEMENT:
								viewBankCreditCardStatement();
								break;
							case BANK_LOG_OUT:
								System.out.println("LOGGED OUT");
								break;

							}
						}
					}
				} else {
					System.out.println("Invalid Option!!");

				}

			}

		}
	}

	void listExistingDebitCards() {
		List<DebitCardBean> debitCardBeans = customService.viewAllDebitCards();
		if (debitCardBeans.isEmpty()) {
			System.out.println("No Existing Debit Cards");
		} else {

			for (DebitCardBean debitCardBean : debitCardBeans) {
				String completeDebitCardNumber = (debitCardBean.getDebitCardNumber()).toString();
				String creditCardShow = completeDebitCardNumber.substring(0, 4) + "-XXXX-XXXX-"
						+ completeDebitCardNumber.substring(12);
				System.out.println("Debit Card Number             :\t" + creditCardShow);
                System.out.println("Type                          :\t" + debitCardBean.getDebitCardType());
			//	System.out.println("Status                        :\t" + debitCardBean.getDebitCardStatus());
				System.out.println("Name                          :\t" + debitCardBean.getNameOnDebitCard());
				//System.out.println("Account number                :\t" + debitCardBean.getAccountNumber());
				System.out.println("Date of expiry(yyyy/MM/dd)    :\t" + debitCardBean.getDebitDateOfExpiry());
				System.out.println("......................................................");
			}
		}
	}

	void listExistingCreditCards() {

		List<CreditCardBean> creditCardBeans = customService.viewAllCreditCards();
		if (creditCardBeans.isEmpty()) {
			System.out.println("No Existing Credit Cards");
		} else {
			for (CreditCardBean creditCardBean : creditCardBeans) {
				String completeCreditCardNumber = (creditCardBean.getCreditCardNumber()).toString();
				String creditCardShow = completeCreditCardNumber.substring(0, 4) + "-XXXX-XXXX-"
						+ completeCreditCardNumber.substring(12);
				System.out.println("Credit Card Number                    :\t" + creditCardShow);
				//System.out.println("Credit card status                    :\t" + creditCardBean.getCreditCardStatus());
				System.out.println("Name on Credit card                   :\t" + creditCardBean.getNameOnCreditCard());
				System.out.println("Date of expiry(yyyy/MM/dd)            :\t" + creditCardBean.getCreditDateOfExpiry());
				System.out.println("Credit card type                      :\t" + creditCardBean.getCreditCardType());
				//System.out.println("Credit score                          :\t" + creditCardBean.getCreditScore());
				//System.out.println("Credit limit                          :\t" + creditCardBean.getCreditLimit());
				System.out.println("......................................................");
			}
		}
	}

	void applyNewDebitCard() {

		success = false;
		System.out.println("You are applying for a new Debit Card");
		while (!success) {

			try {
				System.out.println("Enter Account Number you want to apply debit card for :");

				accountNumber = scan.nextBigInteger();
				check = customService.verifyAccountNumber(accountNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {

				// scan.next();
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Renter 11 digit account number");
				 
			} catch (IBSException notFound) {
				System.out.println(notFound.getMessage());
				 
			}
		}
		if (check) {
			success = false;
			while (!success) {

				try {
					System.out.println("We offer three kinds of Debit Cards:");
					System.out.println(".....................................");
					System.out.println("1 for  Platinum");
					System.out.println("2 for  Gold");
					System.out.println("3 for Silver");
					System.out.println("Choose between 1 to 3");

					newCardType = scan.nextInt();

					System.out.println("You have applied for: " + customService.getNewCardtype(newCardType));
					customerReferenceId = customService.applyNewDebitCard(accountNumber,
							customService.getNewCardtype(newCardType));
					System.out.println("Application for new debit card success!!");
					System.out.println("Your reference Id is " + customerReferenceId);
					success = true;

				} catch (InputMismatchException cardNew) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Enter 1/2/3");

				} catch (IBSException cardNew) {
					System.out.println(cardNew.getMessage());
					 
				}

			}
		}
	}

	void applyNewCreditCard() {
		success = false;
		System.out.println("You are applying for a new Credit Card");
		while (!success) {

			try {
				System.out.println("We offer three kinds of Credit Cards:");
				System.out.println(".....................................");
				System.out.println("1 for  Platinum");
				System.out.println("2 for  Gold");
				System.out.println("3 for Silver");
				System.out.println("Choose between 1 to 3");

				newCardType = scan.nextInt();

				System.out.println("You have applied for: " + customService.getNewCardtype(newCardType));
				customerReferenceId = customService.applyNewCreditCard(customService.getNewCardtype(newCardType));
				System.out.println("Application for new Credit card success!!");

				System.out.println("Your reference Id is " + customerReferenceId);
				success = true;

			} catch (InputMismatchException cardNew) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Enter 1/2/3");

			} catch (IBSException cardNew) {
				System.out.println(cardNew.getMessage());
				 
			}

		}

	}

	void upgradeExistingDebitCard() {

		success = false;

		System.out.println("Enter your Debit Card Number: ");
		while (!success) {

			try {

				debitCardNumber = scan.nextBigInteger();
				check = customService.verifyDebitCardNumber(debitCardNumber);

				type = customService.verifyDebitcardType(debitCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Enter a valid Debit card number");
				 

			} catch (IBSException noCard) {
				System.out.println(noCard.getMessage());
				 
			}

			catch (NullPointerException notFound) {
				System.out.println("Debit Card not Found");
				 

			}
			if (customService.getDebitCardStatus(debitCardNumber)) {
				if (check) {
					if (type.equalsIgnoreCase("Silver")) {
						System.out.println("Choose 1 to upgrade to Gold");
						System.out.println("Choose 2 to upgrade to Platinum");
						String mString = null;
						success = false;
						while (!success) {
							try {
								myChoice = scan.nextInt();
								mString = customService.checkMyChoice(myChoice);
								System.out.println("You have applied for " + mString);

								success = true;
							} catch (InputMismatchException wrongFormat) {
								if (scan.next().equalsIgnoreCase("x"))
									return;
								System.out.println("Choose between 1 or 2");
								 
							} catch (IBSException e) {
								System.out.println(e.getMessage());
							}
						}
						try {
							System.out.println("Ticket Raised successfully . Your reference Id is "
									+ customService.requestDebitCardUpgrade(debitCardNumber, mString));
						} catch (IBSException e) {
							System.out.println(e.getMessage());
						}
					} else if (type.equalsIgnoreCase("Gold")) {
						System.out.println("Choose 2 to upgrade to Platinum");
						success = false;
						String mString = null;
						while (!success) {
							try {
								myChoice = scan.nextInt();
								System.out.println(customService.checkMyChoiceGold(myChoice));
								success = true;

							} catch (InputMismatchException wrongFormat) {
								if (scan.next().equalsIgnoreCase("x"))
									return;
								System.out.println("Enter 2 to upgrade");
								 
							} catch (IBSException e) {
								System.out.println(e.getMessage());
							}
						}
						try {
							System.out.println("Ticket Raised successfully . Your reference Id is "
									+ customService.requestDebitCardUpgrade(creditCardNumber, mString));
						} catch (IBSException e) {
							System.out.println(e.getMessage());
						}

					} else {
						System.out.println("You already have a Platinum Card");
					}

				}
			} else {
				System.out.println("YOUR CARD IS BLOCKED");
			}

		}
	}

	void upgradeExistingCreditCard() {

		success = false;

		System.out.println("Enter your Credit Card Number: ");
		while (!success) {

			try {

				creditCardNumber = scan.nextBigInteger();
				check = customService.verifyCreditCardNumber(creditCardNumber);
				type = customService.verifyCreditcardType(creditCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Enter a valid Credit card number");
				 

			} catch (NullPointerException notFound) {
				System.out.println(notFound.getMessage());
				 
			} catch (IBSException notFound) {
				System.out.println(notFound.getMessage());
				 
			}
			if (customService.getCreditCardStatus(creditCardNumber)) {
				if (check) {

					if (type.equalsIgnoreCase("Silver")) {
						System.out.println("Choose 1 to upgrade to Gold");
						System.out.println("Choose 2 to upgrade to Platinum");
						String mString = null;
						success = false;
						while (!success) {
							try {
								myChoice = scan.nextInt();
								mString = customService.checkMyChoice(myChoice);
								System.out.println("You have applied for " + mString);

								success = true;
							} catch (InputMismatchException wrongFormat) {
								if (scan.next().equalsIgnoreCase("x"))
									return;
								System.out.println("Choose between 1 or 2");
								 
							} catch (IBSException e) {
								System.out.println(e.getMessage());
							}
						}
						try {
							System.out.println("Ticket Raised successfully . Your reference Id is "
									+ customService.requestDebitCardUpgrade(creditCardNumber, mString));
						} catch (IBSException e) {
							System.out.println(e.getMessage());
						}
					} else if (type.equalsIgnoreCase("Gold")) {
						System.out.println("Choose 2 to upgrade to Platinum");
						success = false;
						String mString = null;
						while (!success) {
							try {
								myChoice = scan.nextInt();
								System.out.println(customService.checkMyChoiceGold(myChoice));
								success = true;

							} catch (InputMismatchException wrongFormat) {
								if (scan.next().equalsIgnoreCase("x"))
									return;
								System.out.println("Enter 2 to upgrade");
								 
							} catch (IBSException e) {
								System.out.println(e.getMessage());
							}
						}
						try {
							System.out.println("Ticket Raised successfully . Your reference Id is "
									+ customService.requestDebitCardUpgrade(creditCardNumber, mString));
						} catch (IBSException e) {
							System.out.println(e.getMessage());
						}

					} else {
						System.out.println("You already have a Platinum Card");
					}

				}
			} else {
				System.out.println("YOUR CARD IS BLOCKED");
			}
		}
	}

	void resetDebitCardPin() {

		success = false;
		System.out.println("Enter your Debit Card Number: ");

		while (!success) {

			try {
				debitCardNumber = scan.nextBigInteger();

				check = customService.verifyDebitCardNumber(debitCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Enter a valid debit card number");
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
			}

		}
		if (customService.getDebitCardStatus(debitCardNumber)) {
			if (check) {
				System.out.println("Enter your existing pin:");

				success = false;
				while (!success) {
					try {

						pin = scan.nextInt();

						if (customService.getPinLength(pin) == 4) {

							if (customService.verifyDebitPin(pin, debitCardNumber)) {
								System.out.println("Enter new pin");
								success = false;
								while (!success) {
									try {

										pin = scan.nextInt();

										if (customService.getPinLength(pin) != 4)
											throw new IBSException("Incorrect Length of pin ");
										
										
										else {

											System.out.println("Re-enter your new pin");
											int rpin = scan.nextInt();
											if (customService.getPinLength(rpin) != 4)
												throw new IBSException("Incorrect Length of pin ");
											else {
												if (rpin == pin) {
													customService.resetDebitPin(debitCardNumber, pin);
													System.out.println("PIN CHANGED SUCCESSFULLY!!!");
													success = true;
												} else {
													System.out.println("PINS DO NOT MATCH...TRY AGAIN");
													success = true;
												}
											}
										}

										
									} catch (InputMismatchException wrongFormat) {
										System.out.println("Enter a valid 4 digit pin");
										scan.next();
					

									} catch (IBSException ExceptionObj) {
										System.out.println(ExceptionObj.getMessage());

							

									}
								}

							} else {

								System.out.println("You have entered wrong pin ");
								System.out.println("Try again");
							}
						}
						success = true;
					} catch (InputMismatchException wrongFormat) {
						System.out.println("Enter a valid 4 digit pin");
						scan.next();
						 

					} catch (IBSException ExceptionObj) {
						System.out.println(ExceptionObj.getMessage());

						 

					}
				}
			}
		} else {
			System.out.println("YOUR CARD IS BLOCKED");
		}
	}

	void resetCreditCardPin() {

		success = false;
		System.out.println("Enter your Credit Card Number: ");
		while (!success) {
			try {
				creditCardNumber = scan.nextBigInteger();
				check = customService.verifyCreditCardNumber(creditCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Enter a valid credit card number");
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
			}
		}
		if (customService.getCreditCardStatus(creditCardNumber)) {
			if (check) {
				System.out.println("Enter your existing pin:");
				success = false;
				while (!success) {
					try {

						int pin = scan.nextInt();

						if (customService.getPinLength(pin) == 4)

							if (customService.verifyCreditPin(pin, creditCardNumber)) {
								System.out.println("Enter new pin");
								success = false;
								while (!success) {
									try {

										pin = scan.nextInt();

										if (customService.getPinLength(pin) != 4)
											throw new IBSException("Incorrect Length of pin ");
										
										else {

											System.out.println("Re-enter your new pin");
											int rpin = scan.nextInt();
											if (customService.getPinLength(rpin) != 4)
												throw new IBSException("Incorrect Length of pin ");
											else {
												if (rpin == pin) {
													customService.resetCreditPin(creditCardNumber, pin);
													System.out.println("PIN CHANGED SUCCESSFULLY!!!");
													success = true;
												} else {
													System.out.println("PINS DO NOT MATCH...TRY AGAIN");
													success = true;
												}
											}
										}
										
										
									} catch (InputMismatchException wrongFormat) {
										System.out.println("Enter a valid 4 digit pin");
										if (scan.next().equalsIgnoreCase("x"))
											return;
										 
									} catch (IBSException ExceptionObj) {
										System.out.println(ExceptionObj.getMessage());

										 
									}
								}

							} else {

								System.out.println("You have entered wrong pin ");
								System.out.println("Try again");
							}
						success = true;
					} catch (InputMismatchException wrongFormat) {
						System.out.println("Enter a valid 4 digit pin");
						if (scan.next().equalsIgnoreCase("x"))
							return;
						 
					} catch (IBSException ExceptionObj) {
						System.out.println(ExceptionObj.getMessage());

						 
					}
				}
			}
		} else {
			System.out.println("YOUR CARD IS BLOCKED");
		}

	}

	void reportDebitCardLost() {

		success = false;
		while (!success) {

			try {

				System.out.println("Enter your Debit Card Number: ");
				debitCardNumber = scan.nextBigInteger();
				check = customService.verifyDebitCardNumber(debitCardNumber);
				if (check) {
					System.out.println("Ticket Raised successfully . Your reference Id is "
							+ customService.requestDebitCardLost(debitCardNumber));
					success = true;
				}
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Not a valid format");
				 
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
				 
			}

		}
	}

	void reportCreditCardLost() {
		success = false;
		while (!success) {

			try {

				System.out.println("Enter your Credit Card Number: ");
				creditCardNumber = scan.nextBigInteger();
				check = customService.verifyCreditCardNumber(creditCardNumber);
				if (check) {
					System.out.println("Ticket Raised successfully . Your reference Id is "
							+ customService.requestCreditCardLost(creditCardNumber));
					success = true;
				}

			} catch (InputMismatchException wrongFormat) {

				System.out.println("Not a valid format");
				if (scan.next().equalsIgnoreCase("x"))
					return;
			
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
			
			}
		}
	}

	void requestDebitCardStatement() {
		success = false;

		while (!success) {

			try {
				System.out.println("Enter your Debit Card Number: ");
				debitCardNumber = scan.nextBigInteger();
				check = customService.verifyDebitCardNumber(debitCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Not a valid format");
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
			
			}
		}
		success = false;
		if (check) {
			while (!success) {
				try {
					System.out.println("enter days : ");
					days = scan.nextInt();
					customService.checkDays(days);
					success = true;
				} catch (InputMismatchException wrongFormat) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Not a valid format");
				} catch (IBSException newException) {
					System.out.println(newException.getMessage());
					 
				}
			}

			try {
				List<DebitCardTransaction> debitCardBeanTrns = customService.getDebitTransactions(days,
						debitCardNumber);
				for (DebitCardTransaction debitCardTrns : debitCardBeanTrns)
					System.out.println(debitCardTrns.toString());

			}

			catch (IBSException newException) {
				System.out.println(newException.getMessage());

			}
		}
	}

	void requestCreditCardStatement() {
		success = false;

		while (!success) {

			try {
				System.out.println("Enter your Credit Card Number: ");
				creditCardNumber = scan.nextBigInteger();
				check = customService.verifyCreditCardNumber(creditCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Not a valid format");
				 
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
				 
			}
		}
		success = false;
		if (check) {
			while (!success) {
				try {
					System.out.println("enter days : ");
					days = scan.nextInt();
					customService.checkDays(days);
					success = true;
				} catch (InputMismatchException wrongFormat) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Not a valid format");
					 
				} catch (IBSException e) {

					System.out.println(e.getMessage());
					 
				}

				try {
					List<CreditCardTransaction> creditCardBeanTrns = customService.getCreditTrans(days,
							creditCardNumber);
					for (CreditCardTransaction creditCardTrns : creditCardBeanTrns)
						System.out.println(creditCardTrns.toString());

				}

				catch (IBSException e) {

					System.out.println(e.getMessage());
					 
				}
			}
		}
	}

	void reportDebitStatementMismatch() {

		success = false;
		while (!success) {

			try {

				System.out.println("Enter your transaction id");

				if ((transactionId = scan.next()).equalsIgnoreCase("x"))
					return;

				check = customService.checkDebitTransactionId(transactionId);
				if (check) {
					System.out.println("Ticket Raised successfully . Your reference Id is "
							+ customService.raiseDebitMismatchTicket(transactionId));

					success = true;
				}
			} catch (InputMismatchException wrongFormat) {
				scan.next();

				System.out.println("Not a valid format");
				 
			} catch (IBSException e) {

				System.out.println(e.getMessage());
				 
			}
		}
	}

	void reportCreditStatementMismatch() {
		success = false;
		while (!success) {

			try {

				System.out.println("Enter your transaction id");
				if ((transactionId = scan.next()).equalsIgnoreCase("x"))
					return;
				check = customService.verifyCreditCardTransactionId(transactionId);
				if (check) {
					System.out.println("Ticket Raised successfully . Your reference Id is "
							+ customService.raiseCreditMismatchTicket(transactionId));
					success = true;
				}
			} catch (InputMismatchException wrongFormat) {
				scan.next();

				System.out.println("Not a valid format");
			} catch (IBSException e) {
				System.out.println(e.getMessage());
				 
			}
		}

	}

	void viewQueryStatus() {
		success = false;
		while (!success) {
			System.out.println("Enter your Unique Reference ID");
			try {
				if ((customerReferenceId = scan.next()).equalsIgnoreCase("x"))
					return;

				System.out.println(customService.viewQueryStatus(customerReferenceId));
				success = true;
			} catch (IBSException e) {
				System.out.println(e.getMessage());
			} catch (NullPointerException n) {
				System.out.println("d");
				 
			}
		}

	}

	void listPendingQueries() {
		List<CaseIdBean> caseBeans = bankService.viewQueries();
		if (caseBeans.isEmpty()) {
			System.out.println("No Existing Queries");
		} else {

			for (CaseIdBean caseId : caseBeans) {

				System.out.println(caseId.toString());
			}
		}
	}

	void replyQueries() {
		String queryId = null;
		int newStatus = -1;
		success = false;
		while (!success) {
			try {
				System.out.println("Enter query ID ");
				if ((queryId = scan.next()).equalsIgnoreCase("x"))
					return;
				check = bankService.verifyQueryId(queryId);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				scan.next();
				System.out.println("Not a valid format");
				 
			} catch (IBSException ibs) {
				System.out.println(ibs.getMessage());
				 
			}
		}
		if (check) {

			success = false;
			while (!success) {
				System.out.println("Select new Status from the following list:");
				System.out.println("..........................................");
				System.out.println("1 for Approved...... ");
				System.out.println("2 for In Process.....");
				System.out.println("3 for Disapproved ...");

				newStatus = scan.nextInt();
				String newQueryStatus;
				try {
					newQueryStatus = bankService.getNewQueryStatus(newStatus);

					System.out.println(" You have chosen " + newQueryStatus);

					bankService.setQueryStatus(queryId, newQueryStatus);
					success = true;
				} catch (IBSException e) {
					System.out.println(e.getMessage());
					 
				} catch (InputMismatchException e) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Enter 1/2/3");

				}

			}

		}

		else {
			System.out.println("Invalid query id");
		}
	}

	void viewBankDebitCardStatement() {
		success = false;

		while (!success) {

			try {
				System.out.println("Enter your Debit Card Number: ");
				debitCardNumber = scan.nextBigInteger();
				check = bankService.verifyDebitCardNumber(debitCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Not a valid format");
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
				 
			}
		}
		success = false;
		if (check) {
			while (!success) {
				try {
					System.out.println("enter days : ");
					days = scan.nextInt();
					bankService.checkDays(days);
					success = true;
				} catch (InputMismatchException wrongFormat) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Not a valid format");
				} catch (IBSException newException) {
					System.out.println(newException.getMessage());
					 
				}
			}

			try {
				List<DebitCardTransaction> debitCardBeanTrns = bankService.getDebitTransactions(days, debitCardNumber);
				for (DebitCardTransaction debitCardTrns : debitCardBeanTrns)
					System.out.println(debitCardTrns.toString());

			}

			catch (IBSException newException) {
				System.out.println(newException.getMessage());

			}
		}
	}

	void viewBankCreditCardStatement() {
		success = false;

		while (!success) {

			try {
				System.out.println("Enter your Credit Card Number: ");
				creditCardNumber = scan.nextBigInteger();
				check = bankService.verifyCreditCardNumber(creditCardNumber);

				success = true;
			} catch (InputMismatchException wrongFormat) {
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Not a valid format");
				 
			} catch (IBSException newException) {
				System.out.println(newException.getMessage());
				 
			}
		}
		success = false;
		if (check) {
			while (!success) {
				try {
					System.out.println("enter days : ");
					days = scan.nextInt();
					bankService.checkDays(days);
					success = true;
				} catch (InputMismatchException wrongFormat) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Not a valid format");
					 
				} catch (IBSException e) {
					System.out.println(e.getMessage());
					 
				}

				try {
					List<CreditCardTransaction> creditCardBeanTrns = bankService.getCreditTrans(days, creditCardNumber);
					for (CreditCardTransaction creditCardTrns : creditCardBeanTrns)
						System.out.println(creditCardTrns.toString());

				}

				catch (IBSException e) {

					System.out.println(e.getMessage());
					 
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
	    scan = new Scanner(System.in);
		CardManagementUI obj = new CardManagementUI();
		obj.doIt();
		System.out.println("Program End");
		obj.scan.close();
	}
}
