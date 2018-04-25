package com.bank.account.mgt.command;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.TransferManager;

@RunWith(SpringJUnit4ClassRunner.class)
public class TotalSentCommandTest {

	@Mock
	private TransferManager transferManager;

	@InjectMocks
	private TotalSentCommand totalSentCommand;

	@Test
	public void executeCommand_SUCCESS_CASE() {
		totalSentCommand.setAllCmds("SEND");

		List<BigDecimal> amounts = new ArrayList<>();
		amounts.add(new BigDecimal("100"));
		amounts.add(new BigDecimal("120"));

		when(transferManager.getAllTransactions(any(String.class), any(String.class))).thenReturn(amounts);

		UserResource userRes = new UserResource();
		userRes.setUserName("testUser");
		userRes.setBalance(new BigDecimal("100"));

		List<String> usrCmd = new ArrayList<>();
		usrCmd.add("SEND");
		userRes.setUserCmd(usrCmd);

		List<String> receiverList = new ArrayList<>();
		receiverList.add("test_recipient");
		userRes.setReceiver(receiverList);

		assertTrue(amounts.stream().map(e -> e).reduce(BigDecimal::add).get().toString()
				.equalsIgnoreCase(totalSentCommand.executeCommand(userRes).getResponse()));

	}

	@Test
	public void executeCommand_INTERNAL_ERROR() {
		totalSentCommand.setAllCmds("SEND");

		List<BigDecimal> amounts = new ArrayList<>();
		amounts.add(new BigDecimal("100"));
		amounts.add(new BigDecimal("120"));

		when(transferManager.getAllTransactions(any(String.class), any(String.class))).thenReturn(amounts);

		UserResource userRes = new UserResource();
		userRes.setBalance(new BigDecimal("100"));

		List<String> usrCmd = new ArrayList<>();
		usrCmd.add("SEND");
		userRes.setUserCmd(usrCmd);

		List<String> receiverList = new ArrayList<>();
		receiverList.add("test_recipient");
		userRes.setReceiver(receiverList);

		assertTrue("0".equalsIgnoreCase(totalSentCommand.executeCommand(userRes).getResponse()));

	}
}
