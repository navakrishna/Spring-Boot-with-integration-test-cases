package com.bank.account.mgt.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bank.account.mgt.dao.UserDAO;
import com.bank.account.mgt.dao.UserTranscationDAO;
import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.domain.UserTranscationDbEntity;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.impl.TransferManagerImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TransferManagerImplTest {

	@Mock
	private UserDAO userDAOMock;
	
	@Mock
	private UserTranscationDAO userTranscationDAOMock;
	
	@Mock
	private UserManager userManagerMock;
	
	@InjectMocks
	private TransferManagerImpl transferMgrImpl;
	
	
	@Test
	public void sendMoney_SUCCESS_CASE() {
		Optional<UserDbEntity> optEntity = Optional.of(populateUserDBEntity("testUser", "5000", "device-tyhs6667"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity);
		when(userManagerMock.getBalance("testUser")).thenReturn(new BigDecimal(5000));
		
		Optional<UserDbEntity> optEntity_recipient = Optional.of(populateUserDBEntity("test_recipient", "100", "device-tyhs6668"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity_recipient);
		transferMgrImpl.sendMoney("testUser", "test_recipient", new BigDecimal("120"));

	}
	
	@Test(expected = BankAccountMgtException.class)
	public void sendMoney_INSUFFIICIENT_BALANCE() {
		Optional<UserDbEntity> optEntity = Optional.of(populateUserDBEntity("testUser", "5000", "device-tyhs6667"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity);
		when(userManagerMock.getBalance("testUser")).thenReturn(new BigDecimal(5000));
		
		Optional<UserDbEntity> optEntity_recipient = Optional.of(populateUserDBEntity("test_recipient", "120", "device-tyhs6668"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity_recipient);
		transferMgrImpl.sendMoney("testUser", "test_recipient", new BigDecimal("10000"));
	}
	
	
	@Test(expected = BankAccountMgtException.class)
	public void sendMoney_INVALID_TRANSFER() {
		Optional<UserDbEntity> optEntity = Optional.of(populateUserDBEntity("testUser", "5000", "device-tyhs6667"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity);
		when(userManagerMock.getBalance("testUser")).thenReturn(new BigDecimal(5000));
		
		Optional<UserDbEntity> optEntity_recipient = Optional.of(populateUserDBEntity("testUser", "120", "device-tyhs6668"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity_recipient);
		transferMgrImpl.sendMoney("testUser", "testUser", new BigDecimal("20"));
	}
	
	@Test(expected = Exception.class)
	public void sendMoney_INTERNAL_ERROR() {
		Optional<UserDbEntity> optEntity = Optional.of(populateUserDBEntity("testUser", "5000", "device-tyhs6667"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity);
		when(userManagerMock.getBalance("testUser")).thenReturn(new BigDecimal(5000));
		
		Optional<UserDbEntity> optEntity_recipient = Optional.of(populateUserDBEntity("testUser", "120", "device-tyhs6668"));
		when(userDAOMock.findByUserName(any(String.class))).thenReturn(optEntity_recipient);
		transferMgrImpl.sendMoney(null, "testUser", new BigDecimal("20"));
	}
	
	@Test
	public void getAllTranscations_SUCCESS_CASE() {
	    
		when(userTranscationDAOMock.findByUserTranscAndReceiver(any(UserDbEntity.class), any(String.class)))
				.thenReturn(populateUserTranscDBEntity("test_recipient", "120", "100"));
		assertTrue(!transferMgrImpl.getAllTransactions("testUser", "test_recipient").isEmpty());
	}
	
	@Test
	public void getAllTranscations_NO_TRANSCATIONS() {
		List<BigDecimal> amounts = new ArrayList<>();
		amounts.add(new BigDecimal("120"));
		amounts.add(new BigDecimal("100"));
	    populateUserTranscDBEntity("test_recipient", "120", "100");
	    
		when(userTranscationDAOMock.findByUserTranscAndReceiver(any(UserDbEntity.class), any(String.class)))
				.thenReturn(new ArrayList<UserTranscationDbEntity>());
		assertTrue(transferMgrImpl.getAllTransactions("testUser", "test_recipient").isEmpty());
	}
	
	
	private UserDbEntity populateUserDBEntity(String userName, String amountToBeTransfered, String deviceId) {
		UserDbEntity userDbEntity = new UserDbEntity();
		userDbEntity.setUserName(userName);
		userDbEntity.setBalance(new BigDecimal(amountToBeTransfered));
		userDbEntity.setDeviceId(deviceId);
		
		return userDbEntity;
	}
	
	private List<UserTranscationDbEntity> populateUserTranscDBEntity(String recipient, String...transctionAmount) {
		Function<String, UserTranscationDbEntity> fun = e -> {
			UserTranscationDbEntity userTransDbEntity = new UserTranscationDbEntity();
			userTransDbEntity.setReceiver(recipient);
			userTransDbEntity.setTransferredAmount(new BigDecimal(e));
			return userTransDbEntity;
		};
		
		return Arrays.stream(transctionAmount).map(fun).collect(Collectors.toList());
		
	}
	
	
}
