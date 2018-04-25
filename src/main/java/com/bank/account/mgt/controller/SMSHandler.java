package com.bank.account.mgt.controller;


/**
 * 
 * SMS commands are handled and distributed to respective command implementations.
 *
 */
@FunctionalInterface
public interface SMSHandler {
	
	
	/***
	 * @param smsContent
	 * 
	 * the incoming SMS command string.* @param senderDeviceId is a
	 * unique string that uniquely identifies the customer’s mobile
	 * device. The UserManager proves a means to identify the sender
	 * user.* 
	 * 
	 * @return The SMS content that should be returned to the user.
	 */
	
	String handleSmsRequest(String smsContent, String senderDeviceId);
}
