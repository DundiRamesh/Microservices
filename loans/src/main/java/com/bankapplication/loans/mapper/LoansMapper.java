package com.bankapplication.loans.mapper;

import com.bankapplication.loans.dto.LoansDto;
import com.bankapplication.loans.entity.LoansEntity;

public class LoansMapper {

    public static LoansDto maptoLoansDto(LoansEntity loansEntity, LoansDto loansDto){

        loansDto.setLoanNumber(loansEntity.getLoanNumber());
        loansDto.setLoanType(loansEntity.getLoanType());
        loansDto.setMobileNumber(loansEntity.getMobileNumber());
        loansDto.setTotalLoan(loansEntity.getTotalLoan());
        loansDto.setAmountPaid(loansEntity.getAmountPaid());
        loansDto.setOutStandingAmount(loansEntity.getOutStandingAmount());
        loansDto.setActive(loansEntity.isActive());
        return loansDto;
    }

    public static LoansEntity maptoLoansEntity(LoansEntity loansEntity, LoansDto loansDto){

        int amountPaid = loansEntity.getAmountPaid();
        loansEntity.setLoanNumber(loansDto.getLoanNumber());
        loansEntity.setLoanType(loansDto.getLoanType());
        loansEntity.setMobileNumber(loansDto.getMobileNumber());
        loansEntity.setTotalLoan(loansDto.getTotalLoan());
        loansEntity.setAmountPaid(amountPaid + loansDto.getAmountPaid());
        loansEntity.setOutStandingAmount(loansEntity.getTotalLoan()-loansEntity.getAmountPaid());
        return loansEntity;
    }

}
