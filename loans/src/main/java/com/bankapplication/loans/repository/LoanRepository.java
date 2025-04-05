package com.bankapplication.loans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankapplication.loans.entity.LoansEntity;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoansEntity,Long> {
    List<LoansEntity> findByMobileNumber(String mobileNumber);
    Optional<LoansEntity> findByLoanNumber(String loanNumber);
}
