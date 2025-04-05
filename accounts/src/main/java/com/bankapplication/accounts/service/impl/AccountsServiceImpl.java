package com.bankapplication.accounts.service.impl;

import com.bankapplication.accounts.constants.AccountsConstants;
import com.bankapplication.accounts.dto.AccountsDto;
import com.bankapplication.accounts.dto.CustomerDto;
import com.bankapplication.accounts.entity.AccountsEntity;
import com.bankapplication.accounts.entity.CustomerEntity;
import com.bankapplication.accounts.exception.CustomeAlreadyExistsException;
import com.bankapplication.accounts.exception.MobileNumberException;
import com.bankapplication.accounts.exception.ResourceNotFoundException;
import com.bankapplication.accounts.mapper.AccountsMapper;
import com.bankapplication.accounts.mapper.CustomerMapper;
import com.bankapplication.accounts.repository.AccountsRepository;
import com.bankapplication.accounts.repository.CustomerRepository;
import com.bankapplication.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        CustomerEntity customerEntity = CustomerMapper.mapToCustomerEntity(new CustomerEntity(), customerDto);
        List<CustomerEntity> byMobileNumber = customerRepository.findByMobileNumber(customerEntity.getMobileNumber());
        if(!byMobileNumber.isEmpty()){
            throw new CustomeAlreadyExistsException("Customer already register with the given number " + customerEntity.getMobileNumber());
        }
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private AccountsEntity createNewAccount(CustomerEntity savedCustomer) {
        AccountsEntity accountsEntity = new AccountsEntity();
        accountsEntity.setCustomerId(savedCustomer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        accountsEntity.setAccountNumber(randomAccNumber);
        accountsEntity.setAccountType(AccountsConstants.SAVINGS);
        accountsEntity.setBranchAddress(AccountsConstants.ADDRESS);
        System.out.println(randomAccNumber);
        return accountsEntity;
    }

    @Override
    public CustomerDto fetchCustomerDetails(String mobileNumber) {
        List<CustomerEntity> byMobileNumber = customerRepository.findByMobileNumber(mobileNumber);
        if(!byMobileNumber.isEmpty()){
            CustomerEntity customerEntity = byMobileNumber.get(0);
            CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customerEntity, new CustomerDto());
            customerDto.setAccountsDto(getAccountsDetails(customerEntity.getCustomerId(),mobileNumber));
            return customerDto;
        } else{
            throw new MobileNumberException("Mobile Number not found with the given number" + mobileNumber);
        }
    }

    private AccountsDto getAccountsDetails(long customerId, String mobileNumber) {
        List<AccountsEntity> byCustomerId = accountsRepository.findByCustomerId(customerId);
        if(!byCustomerId.isEmpty()){
            return AccountsMapper.mapToAccountsDto(byCustomerId.get(0),new AccountsDto());
        } else {
            throw new MobileNumberException("Account not found with the given mobile number " + mobileNumber);
        }
    }

    @Override
    public boolean updateCustomeDetails(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            AccountsEntity accountsEntity = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts","AccountNumber",accountsDto.getAccountNumber()+"")
            );
            AccountsEntity toSaveAccount = AccountsMapper.mapToAccountEntity(accountsEntity, customerDto.getAccountsDto());
            accountsRepository.save(toSaveAccount);
            long customerId = accountsEntity.getCustomerId();
            CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId",customerId+"")
            );
            CustomerEntity toSaveCustomer = CustomerMapper.mapToCustomerEntity(customerEntity, customerDto);
            customerRepository.save(toSaveCustomer);
            isUpdate = true;
        } else {
            throw  new ResourceNotFoundException("AccountsDetails","AccountDetails","Should not be null");
        }
        return isUpdate;
    }

    @Override
    public boolean deleteCustomerDetails(String mobileNumber) {
        List<CustomerEntity> byMobileNumber = customerRepository.findByMobileNumber(mobileNumber);
        if(byMobileNumber.size() == 1){
            CustomerEntity customerEntity = byMobileNumber.get(0);
            accountsRepository.deleteByCustomerId(customerEntity.getCustomerId());
            customerRepository.deleteById(customerEntity.getCustomerId());
            return true;
        } else {
            throw new MobileNumberException("Duplicate numbers found with given mobile number " + mobileNumber);
        }
    }
}
