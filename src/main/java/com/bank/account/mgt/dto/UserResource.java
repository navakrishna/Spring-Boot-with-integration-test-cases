package com.bank.account.mgt.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * DTO class for data transfer.
 *
 */

public class UserResource {
	private String userName;
	private BigDecimal balance;
	private String deviceId;
	private List<String> userCmd;
	private String sender;
	private List<String> receiver;
	private String response;

	private BigDecimal transferredAmount;

	/**
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return
	 */
	public List<String> getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 */
	public void setReceiver(List<String> receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return
	 */
	public BigDecimal getTransferredAmount() {
		return transferredAmount;
	}

	/**
	 * @param transferredAmount
	 */
	public void setTransferredAmount(BigDecimal transferredAmount) {
		this.transferredAmount = transferredAmount;
	}


	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return
	 */
	public List<String> getUserCmd() {
		return userCmd;
	}

	/**
	 * @param userCmd
	 */
	public void setUserCmd(List<String> userCmd) {
		this.userCmd = userCmd;
	}

	/**
	 * @return
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	

}
