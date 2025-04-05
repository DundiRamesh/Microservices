package com.bankapplication.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 3, max = 10, message = "Name length should be between 3 and 10")
    private String name;

    @Email(message = "Email should be the valid one")
    @NotEmpty(message = "Email can not be null or empty")
    private String email;

    @NotEmpty(message = "Mobile number can not be empty")
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountsDto getAccountsDto() {
        return accountsDto;
    }

    public void setAccountsDto(AccountsDto accountsDto) {
        this.accountsDto = accountsDto;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
