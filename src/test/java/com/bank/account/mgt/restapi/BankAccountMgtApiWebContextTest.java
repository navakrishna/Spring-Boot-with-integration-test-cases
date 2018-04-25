package com.bank.account.mgt.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bank.account.mgt.constants.BankConstants;
import com.bank.account.mgt.constants.ErrorMessage;
import com.bank.account.mgt.dao.UserDAO;
import com.bank.account.mgt.dao.UserTranscationDAO;
import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.domain.UserTranscationDbEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class BankAccountMgtApiWebContextTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserTranscationDAO userTranscationDao;

	@Before
	public void setUp() {
		UserDbEntity userDbEntity = new UserDbEntity();
		userDbEntity.setUserName("testUser");
		userDbEntity.setBalance(new BigDecimal(5000));
		userDbEntity.setDeviceId("device-tyhs6667");

		UserDbEntity userDbEntity_recipient = new UserDbEntity();
		userDbEntity_recipient.setUserName("FFRITZ");
		userDbEntity_recipient.setBalance(new BigDecimal(100.00));
		userDbEntity_recipient.setDeviceId("device-tyhs688");

		UserTranscationDbEntity userTranDbEntity = new UserTranscationDbEntity();
		userTranDbEntity.setReceiver("FFRITZ");
		userTranDbEntity.setUserTransc(userDbEntity);

		userTranDbEntity.setTransferredAmount(new BigDecimal(100.00));

		userDao.saveAndFlush(userDbEntity);
		userDao.saveAndFlush(userDbEntity_recipient);
		userTranscationDao.saveAndFlush(userTranDbEntity);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

	}

	@Test
	public void processSMSCommand_SHOW_BALANCE() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=BALANCE&deviceId=device-tyhs6667")).andExpect(status().isOk())
				.andExpect(content().string("5000"));

	}

	@Test
	public void processSMSCommand_SEND() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=SEND-100-FFRITZ&deviceId=device-tyhs6667"))
				.andExpect(status().isOk()).andExpect(content().string(BankConstants.SEND_RESPONSE_OK));
	}

	@Test
	public void processSMSCommand_TOTAL_SENT() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=TOTAL-SENT-FFRITZ&deviceId=device-tyhs6667"))
				.andExpect(status().isOk()).andExpect(content().string("100"));
	}

	@Test
	public void processSMSCommand_ERR_INVALID_DEVICE() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=BALANCE&deviceId=device-tyhs66678"))
				.andExpect(status().isExpectationFailed())
				.andExpect(content().string(ErrorMessage.BANK_INVALID_DEVICEID_RESOURCETYPE));
	}

	@Test
	public void processSMSCommand_ERR_NO_USER() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=TOTAL-SENT-FFRITZA&deviceId=device-tyhs6667"))
				.andExpect(status().isExpectationFailed())
				.andExpect(content().string(ErrorMessage.BANK_INVALID_USERS_RESOURCETYPE));
	}

	@Test
	public void processSMSCommand_ERR_INSUFFICIENT_FUNDS() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=SEND-10000-FFRITZ&deviceId=device-tyhs6667"))
				.andExpect(status().isExpectationFailed())
				.andExpect(content().string(ErrorMessage.BANK_INSUFFICIENT_FUND_RESOURCETYPE));
	}

	@Test
	public void processSMSCommand_INTERNAL_SERVER_ERROR() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=XYZSEND&deviceId=device-tyhs6667"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Internal Server Error"));
	}
	
	@Test
	public void processSMSCommand_ERR_INVALID_COMMAND() throws Exception {
		mockMvc.perform(get("/smsCommand/command?sms=XYZ&deviceId=device-tyhs6667"))
				.andExpect(status().isExpectationFailed())
				.andExpect(content().string(ErrorMessage.BANK_INVALID_COMMAND_RESOURCETYPE));
	}

}
