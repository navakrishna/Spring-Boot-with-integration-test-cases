package com.bank.account.mgt.command;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.account.mgt.constants.BankConstants;
import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.TransferManager;

@RunWith(SpringJUnit4ClassRunner.class)
public class SendCommandTest {
  
	@Mock
	private TransferManager transferManager;
	
	
	@InjectMocks
	private SendCommand sendCommand;
	
	@Test
	public void executeCommand_SUCCESS_CASE() {
		sendCommand.setAllCmds("SEND");
		UserResource userRes = new UserResource();;
		userRes.setBalance(new BigDecimal("100"));
		userRes.setUserName("testUser");
		
		List<String> usrCmd = new ArrayList<>();
		usrCmd.add("SEND");
		userRes.setUserCmd(usrCmd);
		
		List<String> receiverList = new ArrayList<>();
		receiverList.add("test_recipient");
		userRes.setReceiver(receiverList);
		
		
		assertSame(BankConstants.SEND_RESPONSE_OK, sendCommand.executeCommand(userRes).getResponse());
	}
	
	@Test(expected=Exception.class)
	public void executeCommand_INTERNAL_ERROR() {
		sendCommand.setAllCmds(null);
		UserResource userRes = new UserResource();;
		userRes.setBalance(new BigDecimal("100"));
		userRes.setUserName("testUser");
		
		List<String> usrCmd = new ArrayList<>();
		usrCmd.add("SEND");
		userRes.setUserCmd(usrCmd);
		
		List<String> receiverList = new ArrayList<>();
		receiverList.add("test_recipient");
		userRes.setReceiver(receiverList);
		
		
		assertSame(BankConstants.SEND_RESPONSE_OK, sendCommand.executeCommand(userRes).getResponse());
	}
	
}
