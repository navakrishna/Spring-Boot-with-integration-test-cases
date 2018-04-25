package com.bank.account.mgt.restapi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bank.account.mgt.advice.BankAccountMgtAdvice;
import com.bank.account.mgt.constants.ErrorMessage;
import com.bank.account.mgt.controller.SMSHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BankAccountMgtApiStandaloneTest.BankAccountMgtApiConfiguration.class)
public class BankAccountMgtApiStandaloneTest {

	private MockMvc mockMvc;

	@Autowired
	private SMSHandler smsHandler;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new BankAccountMgtApi(this.smsHandler))
				.setControllerAdvice(new BankAccountMgtAdvice()).build();

		Mockito.reset(this.smsHandler);
	}

	@Test
	public void processSMSCommand_SUCCESS_CASE() throws Exception {
		when(smsHandler.handleSmsRequest("BALANCE", "device-abc-5tj7")).thenReturn("3000");

		mockMvc.perform(get("/smsCommand/command?sms=BALANCE&deviceId=device-abc-5tj7")).andExpect(status().isOk())
				.andExpect(content().string("3000"));
		verify(smsHandler, times(1)).handleSmsRequest("BALANCE", "device-abc-5tj7");

	}
	
	@Test
	public void processSMSCommand_INVALID_COMMAND() throws Exception {
		when(smsHandler.handleSmsRequest("XYZ", "device-abc-5tj7")).thenReturn(ErrorMessage.BANK_INVALID_COMMAND);

		mockMvc.perform(get("/smsCommand/command?sms=XYZ&deviceId=device-abc-5tj7")).andExpect(status().isOk())
				.andExpect(content().string(ErrorMessage.BANK_INVALID_COMMAND));
		verify(smsHandler, times(1)).handleSmsRequest("XYZ", "device-abc-5tj7");
	}
	
	@Test
	public void processSMSCommand_ERR_NO_USER() throws Exception {
		when(smsHandler.handleSmsRequest("BALANCE", "device-abc-5tj7_1")).thenReturn(ErrorMessage.BANK_INVALID_COMMAND_RESOURCETYPE);

		mockMvc.perform(get("/smsCommand/command?sms=BALANCE&deviceId=device-abc-5tj7_1")).andExpect(status().isOk())
				.andExpect(content().string(ErrorMessage.BANK_INVALID_COMMAND_RESOURCETYPE));
		verify(smsHandler, times(1)).handleSmsRequest("BALANCE", "device-abc-5tj7_1");
	}

	public static class BankAccountMgtApiConfiguration {

		@Bean
		@Autowired
		public SMSHandler smsHandler() {
			return mock(SMSHandler.class);
		}
	}
}
