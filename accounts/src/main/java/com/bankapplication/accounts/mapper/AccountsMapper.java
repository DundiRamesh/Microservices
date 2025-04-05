package com.bankapplication.accounts.mapper;

import com.bankapplication.accounts.dto.AccountsDto;
import com.bankapplication.accounts.entity.AccountsEntity;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(AccountsEntity accountsEntity, AccountsDto accountsDto){
        accountsDto.setAccountNumber(accountsEntity.getAccountNumber());
        accountsDto.setAccountType(accountsEntity.getAccountType());
        accountsDto.setBranchAddress(accountsEntity.getBranchAddress());
        return accountsDto;
    }

    public static AccountsEntity mapToAccountEntity(AccountsEntity accountsEntity, AccountsDto accountsDto){
        accountsEntity.setAccountType(accountsDto.getAccountType());
        accountsEntity.setBranchAddress(accountsDto.getBranchAddress());
        return accountsEntity;
    }

}
