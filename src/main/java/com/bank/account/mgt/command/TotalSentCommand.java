package com.bank.account.mgt.command;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.TransferManager;
import com.bank.account.mgt.util.BankAccountMgtUtil;

/**
 *{@link TotalSentCommand} is an implementation for {@link ICommand} which has one method
 *to perform list of transactions of the account holder.
 *
 */
@Component
public class TotalSentCommand implements ICommand {
	
	@Value("${valid.commands}")
	private String allCmds;
	
	private TransferManager transferManager;
	
	@Autowired
	public TotalSentCommand(TransferManager transferManager) {
		this.transferManager = transferManager;
	}

	/**
     * 
     * First populates user resource object with all the required details.
     * It triggers to fetch sum of all transactions of each recipient that sender has transferred.
     * 
     * 
     * @param userRes
     * @return userRes
     * 
     */
	@Override
	public UserResource executeCommand(UserResource userRes) {
		BankAccountMgtUtil.populateTransctionDetails(userRes, allCmds);
		String resp = userRes.getReceiver().stream()
		        .map(recipient -> transferManager.getAllTransactions(userRes.getUserName(), recipient)
		        		.stream().reduce(BigDecimal.ZERO, BigDecimal::add).toString()).collect(Collectors.joining(","));
		
		userRes.setResponse(resp);
		
		return userRes;
	}

	public String getAllCmds() {
		return allCmds;
	}

	public void setAllCmds(String allCmds) {
		this.allCmds = allCmds;
	}
	
	

}
