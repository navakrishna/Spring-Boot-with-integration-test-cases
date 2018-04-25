package com.bank.account.mgt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.mgt.domain.UserDbEntity;
import com.bank.account.mgt.domain.UserTranscationDbEntity;
/**
 * @author Nava Krishna
 * 
 * Inherited from JpaRepository interface .
 * Implements custom methods {@link findByUserTranscAndReceiver}  along with inherited methods.
 *
 */
public interface UserTranscationDAO extends JpaRepository<UserTranscationDbEntity, String> {
	
   /**
 * 
 * Finds UserTranscationDbEntity from UserTransc and Receiver fields of UserTranscationDbEntity class.
 * 
 * @param userTransc
 * @param receiver
 * @return
 */
List<UserTranscationDbEntity> findByUserTranscAndReceiver(UserDbEntity userTransc, String receiver);
}
