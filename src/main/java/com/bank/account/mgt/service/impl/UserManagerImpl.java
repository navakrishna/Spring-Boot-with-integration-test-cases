package com.bank.account.mgt.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.mgt.constants.ErrorMessage;
import com.bank.account.mgt.dao.UserDAO;
import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.UserManager;

/**
 * 
 * @author Nava Krishna
 * 
 *{@link UserManagerImpl} is a service class implementation of {@link UserManager} interface consumed
 *for user validations and to check user balance.
 * 
 *
 */
@Service
public class UserManagerImpl implements UserManager {

	
	private UserDAO userDao;
	
	@Autowired
	public UserManagerImpl(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	/**
     * 
     * First checks whether user exists, if not throws respective error message.
     * If exists return true.
     * 
     * @param username 
     * @return true -  if user exists, if not throws error message
     * 
     */
	@Override
	public boolean existsUser(String username) {
		userDao.findByUserName(username).orElseThrow(() ->  
		           new BankAccountMgtException(ErrorMessage.BANK_INVALID_USERS, username, ErrorMessage.BANK_INVALID_USERS_RESOURCETYPE));
		return true;
	}

	/**
     * 
     * Fetch user balance based on username.
     * 
     * @param username 
     * @return  -  Sends the balance amount of the user
     * 
     */
	@Override
	public BigDecimal getBalance(String username) {
		return userDao.findByUserName(username).get().getBalance();
	}

	/**
     * 
     * Fetch user names based on deviceId.
     * 
     * @param deviceid 
     * @return - returns username based on username.
     * 
     */
	@Override
	public String getUserNameForDeviceId(String deviceId) {
		UserDbEntity userDBEntity = userDao.findByDeviceId(deviceId).orElseThrow(() -> new BankAccountMgtException(ErrorMessage.BANK_INVALID_DEVICEID, deviceId,
				ErrorMessage.BANK_INVALID_DEVICEID_RESOURCETYPE));
		return userDBEntity.getUserName();
	}
	

}
