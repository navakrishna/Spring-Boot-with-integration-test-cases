package com.bank.account.mgt.controller.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.bank.account.mgt.command.ICommand;
import com.bank.account.mgt.command.SMSCommandKey;
import com.bank.account.mgt.constants.BankConstants;
import com.bank.account.mgt.constants.ErrorMessage;
import com.bank.account.mgt.controller.SMSHandler;
import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.UserManager;

/**
 * 
 * Implementation class for SMSHandler Interface.
 * 
 *
 */
@Controller
public class SMSHandlerImpl implements SMSHandler {

	@Value("${valid.commands}")
	private String allCmds;

	private UserManager userManager;
	
	private SMSCommandKey smsCommandKey;

	@Autowired
	public SMSHandlerImpl(UserManager userManager, SMSCommandKey smsCommandKey) {
		this.userManager = userManager;
		this.smsCommandKey = smsCommandKey;
	}

	/* (non-Javadoc)
	 * @see com.bank.account.mgt.controller.SMSHandler#handleSmsRequest(java.lang.String, java.lang.String)
	 */
	@Override
	public String handleSmsRequest(String smsContent, String senderDeviceId) {
		
		

		if (!Arrays.stream(allCmds.split(",")).parallel().anyMatch(smsContent::contains)) {
			throw new BankAccountMgtException(ErrorMessage.BANK_INVALID_COMMAND, smsContent,
					ErrorMessage.BANK_INVALID_COMMAND_RESOURCETYPE);
		}

		UserResource userRes = getUserDetails(smsContent, senderDeviceId);
		ICommand command = smsCommandKey.getCommand(userRes.getUserCmd().get(0));
		userRes = command.executeCommand(userRes);

		return userRes.getResponse();
	}

	/**
	 * 
	 * It creates UserResource class for all requests.
	 * 
	 * @param smsContent
	 * @param deviceId
	 * @return
	 */
	private UserResource getUserDetails(String smsContent, String deviceId) {

		UserResource userResource = new UserResource();
		userResource.setUserName(userManager.getUserNameForDeviceId(deviceId));
		userResource.setUserCmd(Arrays.asList(smsContent.split(BankConstants.SPLITTER)));

		return userResource;

	}

	/**
	 * @return
	 */
	public String getAllCmds() {
		return allCmds;
	}

	/**
	 * @param allCmds
	 */
	public void setAllCmds(String allCmds) {
		this.allCmds = allCmds;
	}
}
