package com.bank.account.mgt.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bank.account.mgt.exception.BankAccountMgtException;


/**
 * {@link BankAccountMgtAdvice} class handles the exceptions thrown in application
 *
 */
@ControllerAdvice
public class BankAccountMgtAdvice {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountMgtAdvice.class);
	
	
	
	/**
     * Handles custom exceptions when validation fails.
     *
     * @param ex {@link BankAccountMgtException}
     * @param request {@link HttpServletRequest}
     */
    @ExceptionHandler(BankAccountMgtException.class)
    public ResponseEntity<String> handleBankAccountMgtException(BankAccountMgtException ex) {
    	
        LOGGER.debug(ex.getMessage(), ex.getResourceId(), ex.getResourceType());
        
        return new ResponseEntity<String>(ex.getResourceType(), HttpStatus.EXPECTATION_FAILED);
    }
	
	
	/**
     * fall-back handler – a catch-all type of logic that deals with all other
     * exceptions that don’t have specific handlers.
     *
     * @param ex {@link Exception}
     */

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleAll(Exception ex) {
    	
    	LOGGER.error("An unexpected error occurred", ex);
    	
    	return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
