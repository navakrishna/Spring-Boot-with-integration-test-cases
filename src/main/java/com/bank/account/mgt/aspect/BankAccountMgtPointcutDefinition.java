package com.bank.account.mgt.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class BankAccountMgtPointcutDefinition {
	

	@Pointcut("within(com.bank.account.mgt.service..*)")
	public void serviceLayer() {
	}

	
}
