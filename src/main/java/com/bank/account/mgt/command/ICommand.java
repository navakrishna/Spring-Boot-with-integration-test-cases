package com.bank.account.mgt.command;

import com.bank.account.mgt.controller.impl.SMSHandlerImpl;
import com.bank.account.mgt.dto.UserResource;

/**
 *{@link ICommand} interface which exposes one method to execute different commands
 * which is consumed by {@link SMSHandlerImpl}.
 *
 *
 */
public interface ICommand {

	 /**
	 * It executes different command inputs based on respective command 
	 * implementations.for eg: if send command is triggered for transferring amount.
	 * It process the send command the respective logic is in it's implementation.
	 * 	 
	 * @param userRes
	 * @return UserResource
	 * 
	 * 	 * 
	 */
	public UserResource executeCommand(UserResource userRes);
	 
}
