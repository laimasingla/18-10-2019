package com.cg.ibs.cardmanagement.dao;

public class SqlQueries {

	public static final String SELECT_DATA_FROM_CREDIT_CARD ="select credit_card_num, credit_card_status, name_on_cred_card, credit_cvv_num, credit_expiry_date, credit_card_type from credit_card";
	public static final String SELECT_DATA_FROM_DEBIT_CARD = "select debit_card_number, debit_card_status, name_on_deb_card, debit_cvv_num, debit_expiry_date, debit_card_type from debit_card";
	public static final String APPLY_NEW_DEBIT_CARD = "insert into query_log(query_ID,case_TimeStamp,status_of_query,account_num,UCI,define_query,customer_reference_ID)values(?,?,?,?,?,?,?)";
	public static final String SELECT_DATA_FROM_CREDIT_TRANSACTION = 
			"select credit_trans_Id,UCI,Credit_Card_Num,Date_Of_Trans,amount,description from Credit_Card_trans WHERE ( Date_Of_Trans between ? And ? ) and credit_card_num=?";
	public static final String SELECT_DATA_FROM_QUERY_TABLE = " Select * from query_log where status_of_query='In Process' or status_of_query='Pending'";
	public static final String VERIFY_ACCOUNT_NUM_FROM_ACCOUNT = "Select account_number from accounts where account_number =?";
	public static final  String GET_DEBIT_CARD_TYPE = "select debit_card_type from debit_card where debit_card_number=?";
	public static final  String GET_CREDIT_CARD_TYPE = "select credit_card_type from credit_card where credit_card_num=?";
	public static final  String GET_DEBIT_CARD_PIN = "select debit_current_pin from debit_card where debit_card_number=?";
	public static final  String GET_CREDIT_CARD_PIN = "select credit_current_pin from credit_card where credit_card_num=?";
	public static final String APPLY_NEW_CREDIT_CARD = "insert into query_log(query_ID,case_TimeStamp,status_of_query,UCI,define_query,customer_reference_ID)values(?,?,?,?,?,?)";
    //public static final String SELECT_DATA_FROM_CREDIT_TRANSACTION = "select credit_trans_Id,UCI,Credit_Card_Num,Date_Of_Trans,amount,description from Credit_Card_trans WHERE ( Date_Of_Trans between ? And ? ) and credit_card_num=?";
	  // public static final String VERIFY_ACCOUNT_NUM_FROM_ACCOUNT = "Select account_number from accounts where account_number =?";
	    public static final String VERIFY_DEBIT_CARD_NUM = "Select debit_card_number from debit_card where debit_card_number =?";
	    public static final String VERIFY_CREDIT_CARD_NUM = "Select credit_card_number from credit_card where credit_card_number =?";
	    public static final String VERIFY_DEBIT_TRANS_ID = "Select Debit_Trans_Id from Debit_Card_trans where Debit_Trans_Id=?";
	    public static final String VERIFY_CREDIT_TRANS_ID = "Select Credit_Trans_Id from Credit_Card_trans where Credit_Trans_Id=?";
	    public static final String VERIFY_QUERY_ID = "Select query_id from Query_Log where query_id =?";
	
	
	public static final String SELECT_DATA_FROM_DEBIT_TRANSACTION = "select debit_trans_Id,UCI,debit_Card_Num,Date_Of_Trans,amount,description from debit_Card_trans WHERE( Date_Of_Trans between ? And ?) and debit_card_num=?";
	public static final String REQUEST_DEBIT_CARD_UPGRADE = "insert into query_log(query_ID,case_TimeStamp,status_of_query,account_num,UCI,define_query,card_num,customer_reference_ID)values(?,?,?,?,?,?,?,?)";
}