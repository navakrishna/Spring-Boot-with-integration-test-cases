package com.bank.account.mgt.constants;

/**
 * Error constants to populate error messages.
 *
 */
public class ErrorMessage {

	 public static final String BANK_INVALID_COMMAND="The command was not recognized";
	 public static final String BANK_INVALID_COMMAND_RESOURCETYPE="ERR-UNKNOWN COMMAND";
	 
	 
	 public static final String BANK_INVALID_DEVICEID="Device is not registered to use this feature";
	 public static final String BANK_INVALID_DEVICEID_RESOURCETYPE="ERR - INVALID-DEVICE";
	 
	 public static final String BANK_INVALID_USERS="System can’t find the user with username";
	 public static final String BANK_INVALID_USERS_RESOURCETYPE="ERR – NO USER";
	 
	 public static final String BANK_INSUFFICIENT_FUND="User hasn’t got enough money to make the transfer";
	 public static final String BANK_INSUFFICIENT_FUND_RESOURCETYPE="ERR – INSUFFICIENT FUNDS";
	 public static final String BANK_INVALID_TRANSEFER = "Sender and Recipient are same";
	 public static final String BANK_INVALID_TRANSEFER_FUND_RESOURCETYPE = "ERR_INVALID TRANSFER";
	 
}
