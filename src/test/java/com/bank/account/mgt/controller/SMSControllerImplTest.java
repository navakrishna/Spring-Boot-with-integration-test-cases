package com.bank.account.mgt.controller;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bank.account.mgt.command.ICommand;
import com.bank.account.mgt.command.SMSCommandKey;
import com.bank.account.mgt.controller.impl.SMSHandlerImpl;
import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.UserManager;

/**
 * Nava Krishna
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SMSControllerImplTest {

	@Mock
	private UserManager userManagerMock;

	@Mock
	private SMSCommandKey smsCommandKeyMock;

	@InjectMocks
	private SMSHandlerImpl smsHandler;

	@Mock
	private ICommand icommandMock;

	@Test
	public void handleSmsRequest_SUCCESS_CASE() {

		when(smsCommandKeyMock.getCommand("SEND")).thenReturn(icommandMock);
		when(userManagerMock.getUserNameForDeviceId("device-abc-5tj7")).thenReturn("testUser");
		smsHandler.setAllCmds("SEND");
		when(icommandMock.executeCommand(any(UserResource.class))).thenReturn(populateUserResource("Ok"));
		assertSame("Ok", smsHandler.handleSmsRequest("SEND", "device-abc-5tj7"));

	}

	@Test(expected = BankAccountMgtException.class)
	public void handleSmsRequest_ON_INVALID_COMMAND() {
		when(smsCommandKeyMock.getCommand("XYZ")).thenReturn(icommandMock);
		when(userManagerMock.getUserNameForDeviceId("device-abc-5tj7")).thenReturn("testUser");
		smsHandler.setAllCmds("SEND");
		smsHandler.handleSmsRequest("XYZ", "device-abc-5tj7");
	}

	private UserResource populateUserResource(String response) {
		UserResource userResource = new UserResource();
		userResource.setUserName("testUser");
		userResource.setBalance(new BigDecimal(5000));
		userResource.setResponse(response);
		return userResource;
	}

}
