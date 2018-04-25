package com.bank.account.mgt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * {@link BankAccountMgtLogging} logs the information before service layer
 * methods are invoked.
 *
 */
@Aspect
@Component
public class BankAccountMgtLogging {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * It invoked before any service method is called to log user input parameters.
	 * 
	 * @param jointpoint
	 * @param userResource
	 */
	@Before("execution(* com.bank.account.mgt.service.*.*(..)) && " + "args(userResource,..)")
	public void beforeAnyServiceMethod(JoinPoint jointpoint, String userResource) {

		logger.debug("Before method: " + jointpoint.getSignature().getName() + ". Class: "
				+ jointpoint.getTarget().getClass().getSimpleName());

		logger.debug("Logging user details =" + userResource);
	}

}
