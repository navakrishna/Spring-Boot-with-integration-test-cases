package com.bank.account.mgt.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import com.bank.account.mgt.dto.UserResource;

/**
 * @author Nava Krishna
 * 
 * {@link BankAccountMgtUtil} populate {@link UserResource} instance from the command inputs.
 *
 */
public class BankAccountMgtUtil {

	/**
	 * This method populates {@link UserResource} instance with amount, recipientlist etc.
	 * 
	 * @param userResource
	 * @param allCmds
	 * @return
	 */
	public static UserResource populateTransctionDetails(UserResource userResource, String allCmds) {
		userResource.getUserCmd().stream().forEach(s -> {
       	 boolean isCmd = Arrays.stream(allCmds.split(",")).parallel().anyMatch(s::contains);
       	 if(!isCmd && s.chars().allMatch(Character :: isDigit)) {
       		userResource.setTransferredAmount(new BigDecimal(s));
       	 } else if(!isCmd) {
       		 if(userResource.getReceiver() == null) {
       			userResource.setReceiver(new ArrayList<>());
       		 } 
       		userResource.getReceiver().add(s);
       	 }
        });
		
		return userResource;
	}
}
