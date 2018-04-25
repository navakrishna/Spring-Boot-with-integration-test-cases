package com.bank.account.mgt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.mgt.domain.UserDbEntity;


/**
 * @author Nava Krishna
 * 
 * Inherited from JpaRepository interface .
 * Implements custom methods {@link findByUserName} {@link findByDeviceId}} along with inherited methods.
 *
 */
public interface UserDAO extends JpaRepository<UserDbEntity, String>{

	/**
	 * Retrieves UserDbEntity instance from username field of UserDbEntity.
	 * 
	 * @param userName
	 * @return
	 */
	Optional<UserDbEntity> findByUserName(String userName);
	
	/**
	 * Retrieves UserDbEntity instance from deviceId field of UserDbEntity.
	 * 
	 * @param deviceId
	 * @return
	 */
	Optional<UserDbEntity> findByDeviceId(String deviceId);
	
}
