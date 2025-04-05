package com.bankapplication.accounts.service;

import com.bankapplication.accounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchCustomerDetails(String mobileNumber);

    boolean updateCustomeDetails(CustomerDto customerDto);

    boolean deleteCustomerDetails(String mobileNumber);

}
