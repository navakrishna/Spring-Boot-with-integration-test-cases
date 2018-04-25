package com.bank.account.mgt.command;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.account.mgt.dto.UserResource;
import com.bank.account.mgt.service.UserManager;

@RunWith(SpringJUnit4ClassRunner.class)
public class BalanceCommandTest {
   
	
	@Mock
	private UserManager userManager;
	
	@InjectMocks
	private BalanceCommand balanceCommand;
	
	@Test
	public void executeCommand_SUCCESS_CASE(){
		UserResource userRes = new UserResource();
		userRes.setBalance(new BigDecimal("100"));
		userRes.setUserName("testUser");
		when(userManager.getBalance(any(String.class))).thenReturn(new BigDecimal("100"));
		assertTrue(balanceCommand.executeCommand(userRes).getResponse().compareTo("100") == 0);;
		
	}
	
	@Test(expected=Exception.class)
	public void executeCommand_NULL_VALUE(){
		UserResource userRes = new UserResource();
		userRes.setBalance(new BigDecimal("100"));
		userRes.setUserName(null);
		when(userManager.getBalance(any(String.class))).thenReturn(new BigDecimal("100"));
		balanceCommand.executeCommand(userRes);
	}
	
	
}
