package com.bank.account.mgt.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.mgt.constants.ErrorMessage;
import com.bank.account.mgt.dao.UserDAO;
import com.bank.account.mgt.dao.UserTranscationDAO;
import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.domain.UserTranscationDbEntity;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.TransferManager;
import com.bank.account.mgt.service.UserManager;

/**
 * 
 *{@link TransferManagerImpl} is a service class implementation of {@link TransferManager} interface 
 * consumed for transferring the funds and listing the transactions.
 *
 */
@Service
public class TransferManagerImpl implements TransferManager {
	
	public UserDAO userDAO;
	public UserManager userManager;
	public UserTranscationDAO userTranscationDao;
	
	@Autowired
	public TransferManagerImpl(UserDAO userDAO, UserManager userManager, UserTranscationDAO userTranscationDao) {
		this.userDAO = userDAO;
		this.userManager = userManager;
		this.userTranscationDao = userTranscationDao;
	}

	

    /**
     * 
     * First checks whether user exists or not if not throws respective error message.
     * Second it checks whether sender and receiver are different and if it's same throws respective error message.
     * 
     * After above two validations fetch sender and receiver details from database and performs transaction of money.
     * 
     * @param senderUsername 
     * @param recipientUsername
     * @param recipientAmount
     * 
     */
	@Transactional
	@Override
	public void sendMoney(String senderUsername, String recipientUsername, BigDecimal recipientAmount) {
		userManager.existsUser(recipientUsername);
		
		BigDecimal senderBalance = userManager.getBalance(senderUsername);
		
		if(!(senderBalance.compareTo(recipientAmount) >= 1)) {
			throw new BankAccountMgtException(ErrorMessage.BANK_INSUFFICIENT_FUND,
					recipientAmount.toString(), ErrorMessage.BANK_INSUFFICIENT_FUND_RESOURCETYPE);
		} 
		
		if(senderUsername.equalsIgnoreCase(recipientUsername)) {
			throw new BankAccountMgtException(ErrorMessage.BANK_INVALID_TRANSEFER,
					recipientAmount.toString(), ErrorMessage.BANK_INVALID_TRANSEFER_FUND_RESOURCETYPE);
		}
				
		
		UserDbEntity sendUser = userDAO.findByUserName(senderUsername).get();
		sendUser.setBalance(senderBalance.subtract(recipientAmount));
		
		UserDbEntity recipientUser = userDAO.findByUserName(recipientUsername).get();
		recipientUser.setBalance(recipientUser.getBalance().add(recipientAmount));
		
		UserTranscationDbEntity userTransDBEntity = new UserTranscationDbEntity();
		userTransDBEntity.setUserTransc(sendUser);
		userTransDBEntity.setReceiver(recipientUsername);
		userTransDBEntity.setTransferredAmount(recipientAmount);
		
		userTranscationDao.save(userTransDBEntity);
		userDAO.saveAndFlush(sendUser);
		userDAO.saveAndFlush(recipientUser);
		
		
	}

	/**
     * 
     * First checks whether user exists or not if not throws respective error message.
     * After above  validations fetch transaction details of the requested users from database.
     * 
     * @param senderUsername 
     * @param recipientUsername
     * 
     */
	@Override
	public List<BigDecimal> getAllTransactions(String senderUsername, String recipientUsername) {
		userManager.existsUser(recipientUsername);
		
		UserDbEntity userDbEntity = new UserDbEntity();
		userDbEntity.setUserName(senderUsername);
		
		List<BigDecimal> amounts =  userTranscationDao.findByUserTranscAndReceiver(userDbEntity,recipientUsername).stream()
		       .map(usertrans -> usertrans.getTransferredAmount()).collect(Collectors.toList());
		
		return amounts;
		
	}

}
