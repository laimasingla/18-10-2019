package com.cg.ibs.cardmanagement.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import org.apache.log4j.Logger;

import com.cg.ibs.cardmanagement.bean.AccountBean;
import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.ui.CardManagementUI;

public class CardManagementDaoImpl implements CustomerDao, BankDao {
	static Logger log = Logger.getLogger(CardManagementUI.class.getName());

	CaseIdBean caseIdObj = new CaseIdBean();
	DebitCardBean bean = new DebitCardBean();
	CreditCardBean bean1 = new CreditCardBean();
	CustomerBean bean2 = new CustomerBean();
	AccountBean bean3 = new AccountBean();

	@Override
	public void newDebitCard(CaseIdBean caseIdObj, BigInteger accountNumber) {

		String sql = SqlQueries.APPLY_NEW_DEBIT_CARD;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, caseIdObj.getCaseIdTotal());
			preparedStatement.setDate(2, java.sql.Date.valueOf(caseIdObj.getCaseTimeStamp().toLocalDate()));
			preparedStatement.setString(3, caseIdObj.getStatusOfQuery());
			preparedStatement.setBigDecimal(4, new BigDecimal(caseIdObj.getAccountNumber()));
			preparedStatement.setBigDecimal(5, new BigDecimal(caseIdObj.getUCI()));
			preparedStatement.setString(6, caseIdObj.getDefineQuery());

			preparedStatement.setString(7, caseIdObj.getCustomerReferenceId());
			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			log.error(Arrays.toString(e.getStackTrace()));

		}

	}

	@Override
	public List<CaseIdBean> viewAllQueries() {

		String sql = SqlQueries.SELECT_DATA_FROM_QUERY_TABLE;
		List<CaseIdBean> query = new ArrayList();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					CaseIdBean caseIdObj = new CaseIdBean();
					Timestamp timestamp = resultSet.getTimestamp("case_timestamp");
					LocalDateTime localDateTime = timestamp.toLocalDateTime();

					caseIdObj.setCaseIdTotal(resultSet.getString("query_id"));
					caseIdObj.setCaseTimeStamp(localDateTime);
					caseIdObj.setStatusOfQuery(resultSet.getString("status_of_query"));
					caseIdObj.setAccountNumber(resultSet.getBigDecimal("account_num").toBigInteger());
					caseIdObj.setUCI(resultSet.getBigDecimal("UCI").toBigInteger());
					caseIdObj.setDefineQuery(resultSet.getString("define_query"));
					caseIdObj.setCardNumber(resultSet.getBigDecimal("card_num").toBigInteger());
					caseIdObj.setCustomerReferenceId(resultSet.getString("customer_reference_ID"));

					query.add(caseIdObj);

				}

			} catch (Exception e) {
				log.error(Arrays.toString(e.getStackTrace()));

			}
		} catch (Exception e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
		return query;

	}

	@Override
	public List<DebitCardBean> viewAllDebitCards() {
		
		List<DebitCardBean> debitCards = new ArrayList();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SELECT_DATA_FROM_DEBIT_CARD)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					DebitCardBean deb = new DebitCardBean();

					deb.setDebitCardNumber(resultSet.getBigDecimal("debit_card_number").toBigInteger());
					deb.setNameOnDebitCard(resultSet.getString("name_on_deb_card"));
					deb.setDebitCvvNum(resultSet.getInt("debit_cvv_num"));
					deb.setDebitDateOfExpiry(resultSet.getDate("debit_expiry_date").toLocalDate());
					deb.setDebitCardType(resultSet.getString("debit_card_type"));

					debitCards.add(deb);

				}

			} catch (Exception e) {
				log.error(Arrays.toString(e.getStackTrace()));

			}
		} catch (Exception e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
		return debitCards;

	}

	public List<CreditCardBean> viewAllCreditCards() {
		String sql = SqlQueries.SELECT_DATA_FROM_CREDIT_CARD;
		List<CreditCardBean> creditCards = new ArrayList();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					CreditCardBean crd = new CreditCardBean();

					crd.setCreditCardNumber(resultSet.getBigDecimal("credit_card_num").toBigInteger());
					crd.setCreditCardStatus(resultSet.getString("credit_card_status"));
					crd.setNameOnCreditCard(resultSet.getString("name_on_cred_card"));
					crd.setCreditCvvNum(resultSet.getInt("credit_cvv_num"));
					crd.setCreditDateOfExpiry(resultSet.getDate("credit_expiry_date").toLocalDate());
					crd.setCreditCardType(resultSet.getString("credit_card_type"));

					creditCards.add(crd);

				}

			} catch (Exception e) {
				log.error(Arrays.toString(e.getStackTrace()));

			}
		} catch (Exception e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
		return creditCards;

	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) {

		List<CreditCardTransaction> creditCardsList = new ArrayList<>();

		String sql1 = SqlQueries.SELECT_DATA_FROM_CREDIT_TRANSACTION;
		CreditCardTransaction credTran = new CreditCardTransaction();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {

			LocalDateTime fromDate1 = LocalDateTime.now().minusDays(days);
			LocalDateTime currentDate1 = LocalDateTime.now();
			Timestamp timestamp1 = Timestamp.valueOf(fromDate1);
			Timestamp timestamp2 = Timestamp.valueOf(currentDate1);
			preparedStatement.setTimestamp(1, timestamp1);
			preparedStatement.setTimestamp(2, timestamp2);
			preparedStatement.setBigDecimal(3, new BigDecimal(creditCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					credTran.setCreditCardNumber(resultSet.getBigDecimal("Credit_Card_Num").toBigInteger());
					credTran.setAmount(resultSet.getBigDecimal("amount"));
					credTran.setTransactionid(resultSet.getString("credit_Trans_Id"));
					credTran.setDescription(resultSet.getString("description"));
					credTran.setDateOfTran(resultSet.getTimestamp("Date_Of_trans").toLocalDateTime());
					credTran.setUCI(resultSet.getBigDecimal("UCI").toBigInteger());
					creditCardsList.add(credTran);

				}

			} catch (Exception e) {
				log.error(e.getMessage());
				log.error(Arrays.toString(e.getStackTrace()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return creditCardsList;
	}

	@Override
	public boolean verifyQueryId(String queryId) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_QUERY_ID);) {
			preparedStatement.setString(1, queryId);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public void setQueryStatus(String queryId, String newStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionANDC(BigInteger debitCardNumber, Integer cvv, Integer pin, String queryId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionANCC(BigInteger creditCardNumber, int cvv, int pin, String queryId, int score, double income,
			String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionBlockDC(String queryId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionBlockCC(String queryId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionUpgradeDC(String queryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionUpgradeCC(String queryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newCreditCard(CaseIdBean caseIdObjId) {
		String sql = SqlQueries.APPLY_NEW_CREDIT_CARD;
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, caseIdObj.getCaseIdTotal());
			preparedStatement.setDate(2, java.sql.Date.valueOf(caseIdObj.getCaseTimeStamp().toLocalDate()));
			preparedStatement.setString(3, caseIdObj.getStatusOfQuery());
			preparedStatement.setBigDecimal(4, new BigDecimal(caseIdObj.getUCI()));
			preparedStatement.setString(5, caseIdObj.getDefineQuery());

			preparedStatement.setString(6, caseIdObj.getCustomerReferenceId());
			preparedStatement.execute();

		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public BigInteger getAccountNumber(BigInteger debitCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getdebitCardType(BigInteger debitCardNumber) {
		String sql = SqlQueries.GET_DEBIT_CARD_TYPE;
		String type = new String();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(debitCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					DebitCardBean deb = new DebitCardBean();
					String debitCardType = resultSet.getString("debit_card_type");
					deb.setDebitCardType(debitCardType);

					type = deb.getDebitCardType();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public void requestDebitCardUpgrade(CaseIdBean caseIdObj, BigInteger debitCardNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestDebitCardLost(CaseIdBean caseIdObj, BigInteger debitCardNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCreditCardLost(CaseIdBean caseIdObj, BigInteger creditCardNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verifyAccountNumber(BigInteger accountNumber) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SqlQueries.VERIFY_ACCOUNT_NUM_FROM_ACCOUNT);) {
			preparedStatement.setBigDecimal(1, new BigDecimal(accountNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_DEBIT_CARD_NUM);) {
			preparedStatement.setBigDecimal(1, new BigDecimal(debitCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_CREDIT_CARD_NUM);) {
			preparedStatement.setBigDecimal(1, new BigDecimal(creditCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public void setNewDebitPin(BigInteger debitCardNumber, int newPin) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDebitCardPin(BigInteger debitCardNumber) {
		String sql = SqlQueries.GET_DEBIT_CARD_PIN;
		int pin = 0;
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(debitCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					DebitCardBean deb = new DebitCardBean();
					int debitCardPin = resultSet.getInt("debit_current_pin");
					deb.setDebitCurrentPin(debitCardPin);

					pin = deb.getDebitCurrentPin();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pin;
	}

	@Override
	public void setNewCreditPin(BigInteger creditCardNumber, int newPin) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCreditCardPin(BigInteger creditCardNumber) {
		String sql = SqlQueries.GET_CREDIT_CARD_PIN;
		int pin = 0;
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(creditCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					CreditCardBean cred = new CreditCardBean();
					int creditCardPin = resultSet.getInt("credit_current_pin");
					cred.setCreditCurrentPin(creditCardPin);

					pin = cred.getCreditCurrentPin();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pin;

	}

	@Override
	public BigInteger getUci() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getDebitUci(BigInteger debitCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getCreditUci(BigInteger creditCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getcreditCardType(BigInteger creditCardNumber) {

		String sql = SqlQueries.GET_CREDIT_CARD_TYPE;
		String type = new String();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(creditCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					CreditCardBean cred = new CreditCardBean();
					String creditCardType = resultSet.getString("credit_card_type");
					cred.setCreditCardType(creditCardType);

					type = cred.getCreditCardType();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public boolean verifyCreditTransactionId(String transactionId) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_CREDIT_TRANS_ID);) {
			preparedStatement.setString(1, transactionId);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public void raiseDebitMismatchTicket(CaseIdBean caseIdObj, String transactionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void raiseCreditMismatchTicket(CaseIdBean caseIdObj, String transactionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DebitCardTransaction> getDebitTrans(int days, BigInteger debitCardNumber) {
		List<DebitCardTransaction> debitCardsList = new ArrayList<>();

		String sql1 = SqlQueries.SELECT_DATA_FROM_DEBIT_TRANSACTION;
		DebitCardTransaction debitTran = new DebitCardTransaction();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {

			LocalDateTime fromDate1 = LocalDateTime.now().minusDays(days);
			LocalDateTime currentDate1 = LocalDateTime.now();
			Timestamp timestamp1 = Timestamp.valueOf(fromDate1);
			Timestamp timestamp2 = Timestamp.valueOf(currentDate1);
			preparedStatement.setTimestamp(1, timestamp1);
			preparedStatement.setTimestamp(2, timestamp2);
			preparedStatement.setBigDecimal(3, new BigDecimal(debitCardNumber));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					debitTran.setDebitCardNumber(resultSet.getBigDecimal("Debit_Card_Num").toBigInteger());
					debitTran.setAmount(resultSet.getBigDecimal("amount"));
					debitTran.setTransactionid(resultSet.getString("debit_Trans_Id"));
					debitTran.setDescription(resultSet.getString("description"));
					debitTran.setDate(resultSet.getTimestamp("Date_Of_trans").toLocalDateTime());
					debitTran.setUCI(resultSet.getBigDecimal("UCI").toBigInteger());
					debitCardsList.add(debitTran);

				}

			} catch (Exception e) {
				log.error(e.getMessage());
				log.error(Arrays.toString(e.getStackTrace()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return debitCardsList;
	}

	@Override
	public void requestCreditCardUpgrade(CaseIdBean caseIdObj, BigInteger creditCardNumber) {

		

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.REQUEST_DEBIT_CARD_UPGRADE)) {

			preparedStatement.setString(1, caseIdObj.getCaseIdTotal());
			preparedStatement.setDate(2, java.sql.Date.valueOf(caseIdObj.getCaseTimeStamp().toLocalDate()));
			preparedStatement.setString(3, caseIdObj.getStatusOfQuery());
			preparedStatement.setBigDecimal(4, new BigDecimal(caseIdObj.getAccountNumber()));
			preparedStatement.setBigDecimal(5, new BigDecimal(caseIdObj.getUCI()));
			preparedStatement.setString(6, caseIdObj.getDefineQuery());

			preparedStatement.setBigDecimal(7, new BigDecimal(caseIdObj.getCardNumber()));
			preparedStatement.setString(8, caseIdObj.getCustomerReferenceId());
			preparedStatement.executeUpdate();

		} catch (SQLException | IOException e) {
			log.error(Arrays.toString(e.getStackTrace()));

		}

	}

	@Override
	public boolean verifyDebitTransactionId(String transactionId) {
		boolean result = false;

		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_DEBIT_TRANS_ID);) {
			preparedStatement.setString(1, transactionId);

			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				if (resultSet.next()) {

					result = true;
				}

			}
		} catch (Exception e) {

			log.error(Arrays.toString(e.getStackTrace()));

		}

		return result;
	}

	@Override
	public String getCustomerReferenceId(CaseIdBean caseIdObj, String customerReferenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDebitCardStatus(BigInteger debitCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCreditCardStatus(BigInteger creditCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getDebitCardNumber(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getDMUci(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getDMAccountNumber(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getCMUci(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getNDCUci(BigInteger accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
