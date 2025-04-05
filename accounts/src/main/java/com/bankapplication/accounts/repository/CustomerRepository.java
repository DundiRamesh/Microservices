package com.bankapplication.accounts.repository;

import com.bankapplication.accounts.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    List<CustomerEntity> findByMobileNumber(String mobileNumber);

}
