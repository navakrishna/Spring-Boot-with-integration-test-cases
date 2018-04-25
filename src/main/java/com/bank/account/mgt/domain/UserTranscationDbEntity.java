package com.bank.account.mgt.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Nava Krishna
 * 
 * User Entity resembles user table in database.
 *
 */
@Entity
@Table(name="UserTranscation")
public class UserTranscationDbEntity  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userTranscId")
	private Long userTranscId;
	
	@Column(name="receiver")
	private String receiver;

	@Column(name="amounttransferred")
	private BigDecimal transferredAmount;
	
	@ManyToOne
    @JoinColumn(name = "username")
	private UserDbEntity userTransc;

	/**
	 * @return
	 */
	public Long getUserTranscId() {
		return userTranscId;
	}

	/**
	 * @param userTranscId
	 */
	public void setUserTranscId(Long userTranscId) {
		this.userTranscId = userTranscId;
	}

	/**
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return
	 */
	public BigDecimal getTransferredAmount() {
		return transferredAmount;
	}

	/**
	 * @param transferredAmount
	 */
	public void setTransferredAmount(BigDecimal transferredAmount) {
		this.transferredAmount = transferredAmount;
	}

	/**
	 * @return
	 */
	public UserDbEntity getUserTransc() {
		return userTransc;
	}

	/**
	 * @param userTransc
	 */
	public void setUserTransc(UserDbEntity userTransc) {
		this.userTransc = userTransc;
	}

	
}
