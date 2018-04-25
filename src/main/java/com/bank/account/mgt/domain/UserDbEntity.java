package com.bank.account.mgt.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Nava Krishna
 * 
 * User Entity resembles user table in database.
 *
 */
@Entity
@Table(name="User")
public class UserDbEntity {

	@Id
	@NotNull
	@Column(name="username")
	private String userName;
	@Column(name="balance")
	private BigDecimal balance;
	@Column(name="deviceid")
	private String deviceId;
	@Column(name="usercmd")
	private String userCmd;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserCmd() {
		return userCmd;
	}

	public void setUserCmd(String userCmd) {
		this.userCmd = userCmd;
	}


}
