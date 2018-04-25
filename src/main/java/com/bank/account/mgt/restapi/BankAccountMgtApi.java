package com.bank.account.mgt.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.mgt.controller.SMSHandler;

/**
 * 
 * API for SMS  interface for managing accounts.
 *
 */
@RestController
@RequestMapping("/smsCommand")
public class BankAccountMgtApi {

	private SMSHandler smsHandler;

	@Autowired
	public BankAccountMgtApi(SMSHandler smsHandler) {
		this.smsHandler = smsHandler;
	}

	@RequestMapping(value = "/command", method = RequestMethod.GET)
	public ResponseEntity<String> processSMSCommand(@RequestParam("sms") String smsCommand,
			@RequestParam("deviceId") String deviceId) {
		return ResponseEntity.ok(smsHandler.handleSmsRequest(smsCommand, deviceId));
	}

}
