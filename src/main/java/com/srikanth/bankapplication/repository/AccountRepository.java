package com.srikanth.bankapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.srikanth.bankapplication.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	@Query("SELECT MAX(a.accountId) FROM Account a")
	Optional<Long> findMaxAccountId();

}
