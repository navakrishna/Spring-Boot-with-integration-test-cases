package com.bank.account.mgt.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.account.mgt.dao.UserDAO;
import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.exception.BankAccountMgtException;
import com.bank.account.mgt.service.impl.UserManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerImplTest {

	@Mock
	private UserDAO userDao;

	@InjectMocks
	private UserManagerImpl userManagerImpl;

	@Test
	public void existsUser_SUCCESS_CASE() {
		when(userDao.findByUserName("testUser")).thenReturn(Optional.of(populateUserDBEntity("testUser", "100")));
		assertTrue(userManagerImpl.existsUser("testUser"));
	}

	@Test(expected = BankAccountMgtException.class)
	public void existsUser_NO_USER() {
		when(userDao.findByUserName("test_user")).thenReturn(Optional.of(populateUserDBEntity("testUser", "100")));
		assertTrue(userManagerImpl.existsUser("testUser"));
	}

	@Test()
	public void getBalance_SUCCESS_CASE() {
		when(userDao.findByUserName("testUser")).thenReturn(Optional.of(populateUserDBEntity("testUser", "100")));
		assertTrue(userManagerImpl.getBalance("testUser").compareTo(new BigDecimal("100")) == 0);
	}

	@Test
	public void getUserNameForDeviceId_SUCCESS_CASE() {
		when(userDao.findByDeviceId("device-tyhs6667"))
				.thenReturn(Optional.of(populateUserDBEntity("testUser", "100")));
		assertSame("testUser", userManagerImpl.getUserNameForDeviceId("device-tyhs6667"));
	}

	@Test(expected = BankAccountMgtException.class)
	public void getUserNameForDeviceId_INVALID_ACCESS() {
		when(userDao.findByDeviceId("device-tyhs66678"))
				.thenReturn(Optional.of(populateUserDBEntity("testUser", "100")));
		assertSame("testUser", userManagerImpl.getUserNameForDeviceId("device-tyhs6667"));
	}

	private UserDbEntity populateUserDBEntity(String userName, String amountToBeTransfered) {
		UserDbEntity userDbEntity = new UserDbEntity();
		userDbEntity.setUserName(userName);
		userDbEntity.setBalance(new BigDecimal(amountToBeTransfered));

		return userDbEntity;
	}

}
