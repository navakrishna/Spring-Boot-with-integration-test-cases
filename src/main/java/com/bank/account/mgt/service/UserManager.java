package com.bank.account.mgt.service;

import java.math.BigDecimal;

/**
 * {@link UserManager} an interface consumed by {@link UserManagerImpl} which exposes different methods
 * consumed by {@link TotalSendCommand},{@link BalanceCommand}, @{link SendCommand}.
 *
 */
public interface UserManager {
	boolean existsUser(String username);

	/**
	 * It accepts @param username and fetches the balance of the user.
	 */
	BigDecimal getBalance(String username);

	/**
	 * It accepts @param deviceId and fetch user details from device id.
	 * If user details are missing proper error message must send to user.
	 * 
	 * @return
	 */
	String getUserNameForDeviceId(String deviceId);

}
