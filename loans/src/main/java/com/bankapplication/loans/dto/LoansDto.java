package com.bankapplication.loans.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoansDto {

    @NotEmpty(message = "Mobile number can not be empty")
    @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Mobile number can not be empty")
    @Pattern(regexp = "(^|[0-9]{12})", message = "Mobile Number should be 10 digits")
    private String loanNumber;

    @NotEmpty(message = "loan Type can not be empty")
    private String loanType;

    @Positive(message = "total loan can not be empty or null")
    private int totalLoan;

    @PositiveOrZero(message = "amount paid should be equal or greater than zero")
    private int amountPaid;

    @PositiveOrZero(message = "Out standing amount should be equal or greater than zero")
    private int outStandingAmount;

    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(int totalLoan) {
        this.totalLoan = totalLoan;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getOutStandingAmount() {
        return outStandingAmount;
    }

    public void setOutStandingAmount(int outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

}
