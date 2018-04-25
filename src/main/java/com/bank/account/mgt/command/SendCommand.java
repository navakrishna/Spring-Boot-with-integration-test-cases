package com.bank.account.mgt.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.account.mgt.constants.BankConstants;
import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.TransferManager;
import com.bank.account.mgt.util.BankAccountMgtUtil;

/**
 *{@link SendCommand} is an implementation for {@link ICommand} which has one method
 *to perform transactions of the account holder.
 * 
 *
 */
@Component
public class SendCommand implements ICommand {

	@Value("${valid.commands}")
	private String allCmds;

	private TransferManager transferManager;

	
	
	@Autowired
	public SendCommand(TransferManager transferManager) {
		this.transferManager = transferManager;
	}

	/**
     * 
     * First populates user resource object with all the required details.
     * It triggers transaction for each user from userresource instance.
     * 
     * 
     * @param userRes
     * @return userRes
     * 
     */
	@Override
	public UserResource executeCommand(final UserResource userRes) {

		BankAccountMgtUtil.populateTransctionDetails(userRes, allCmds);
		userRes.getReceiver().stream().forEach(recipient -> transferManager.sendMoney(userRes.getUserName(), recipient,
				userRes.getTransferredAmount()));

		userRes.setResponse(BankConstants.SEND_RESPONSE_OK);
		
		return userRes;

	}

	public String getAllCmds() {
		return allCmds;
	}

	public void setAllCmds(String allCmds) {
		this.allCmds = allCmds;
	}
	
	
	

}
