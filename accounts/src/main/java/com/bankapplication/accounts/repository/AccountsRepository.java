package com.bankapplication.accounts.repository;

import com.bankapplication.accounts.entity.AccountsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {

    List<AccountsEntity> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    void deleteByCustomerId(long customerId);

}
