package com.bank.account.mgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * 
 * Starting for SMS Interface for managing bank accounts.
 * 
 * @see EnableJpaRepositories
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class BankAccountMgtViaSmsApplication {

	/**
     * Main starting point of the service.
     *
     * @param args an array of arguments passed to the application
     */
	public static void main(String[] args) {
		
		SpringApplication.run(BankAccountMgtViaSmsApplication.class, args);
	}
	
	
}
