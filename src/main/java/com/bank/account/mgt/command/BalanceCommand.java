package com.bank.account.mgt.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.UserManager;

/**
 * {@link BalanceCommand} is the implementation for {@link ICommand} interface consumed 
 * for executing balance command and fetch result. 
 * 
 *
 */
@Component
public class BalanceCommand implements ICommand {

	private UserManager userManager;

	@Autowired
	public BalanceCommand(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
     * 
     * 
     * It fetches the balance of the user.
     * 
     * 
     * @param userRes
     * @return userRes
     * 
     */
	
	@Override
	public UserResource executeCommand(UserResource userRes) {
		userRes.setResponse(String.valueOf(userManager.getBalance(userRes.getUserName()).intValue()));
		return userRes;
	}

}
