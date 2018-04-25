package com.bank.account.mgt.exception;

/**
 * {@link BankAccountMgtException} is custom exception class which is used in all validation
 * layers.
 *
 */
public class BankAccountMgtException extends RuntimeException {

	private String resourceType;
	private String resourceId;

	public BankAccountMgtException(String message, String resourceId, String resourceType) {
		super(message);
		this.resourceId = resourceId;
		this.resourceType = resourceType;
	}

	public BankAccountMgtException(Throwable cause, String message, String resourceId, String resourceType) {
		super(message, cause);
		this.resourceId = resourceId;
		this.resourceType = resourceType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
