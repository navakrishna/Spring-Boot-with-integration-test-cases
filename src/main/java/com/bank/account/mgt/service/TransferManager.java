package com.bank.account.mgt.service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * {@link TransferManager} an interface consumed by {@link TransferMangerImpl}
 * which exposes different methods consumed to {@link TotalSendCommand}.
 *
 */
public interface TransferManager {
	/**
	 * 
	 * 
	 * It accepts @param senderUsername, @param recipientUsername and performs
	 * transactions. Before performing transactions it should validate all recipient
	 * and sender user details are correct or not.
	 * 
	 * 
	 * @param amount
	 */
	void sendMoney(String senderUsername, String recipientUsername, BigDecimal amount);

	/**
	 * 
	 * It accepts @param senderUsername, @param recipientUsername and list all
	 * transactions of the sender. Before fetching transaction list it should
	 * validate all recipient and sender details are correct or not.
	
	 */
	List<BigDecimal> getAllTransactions(String senderUsername, String recipientUsername);

}
