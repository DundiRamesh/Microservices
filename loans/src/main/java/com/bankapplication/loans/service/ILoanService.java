package com.bankapplication.loans.service;

import com.bankapplication.loans.dto.LoansDto;

import java.util.List;

public interface ILoanService {

    void createLoan(String mobileNumber);

    List<LoansDto> fetchLoanDetails(String mobileNumber);

    boolean updateLoanDetails(LoansDto loansDto);

    boolean deleteLoanDetails(String mobileNumber);

}
