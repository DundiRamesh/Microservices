package com.bankapplication.loans.service.impl;


import com.bankapplication.cards.exception.LoanAlreadyExistsException;
import com.bankapplication.cards.exception.ResourceNotFoundException;
import com.bankapplication.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankapplication.loans.service.ILoanService;
import com.bankapplication.loans.entity.LoansEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.bankapplication.loans.dto.LoansDto;
import com.bankapplication.loans.mapper.LoansMapper;

@Service
public class LoansServiceImpl implements ILoanService {

    @Autowired
    LoanRepository loanRepository;

    /**
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {

        List<LoansEntity> loans = loanRepository.findByMobileNumber(mobileNumber);
        if(loans.isEmpty()){
            loanRepository.save(createNewLoan(mobileNumber));
        } else {
            handleExistingLoans(mobileNumber, loans);

        }
    }

    private void handleExistingLoans(String mobileNumber, List<LoansEntity> loans) {
        for(LoansEntity loan : loans){
            if(loan.isActive()){
                throw new LoanAlreadyExistsException("Loan already exists and is active for the given mobile number " + mobileNumber);
            }
        }
        loanRepository.save(createAnotherLoan(mobileNumber));
    }

    private LoansEntity createAnotherLoan(String mobileNumber) {
        LoansEntity loansEntity = new LoansEntity();
        long number = (long) (new Random().nextDouble() * 1_000_000_000_000L);
        loansEntity.setLoanNumber(number+"");
        loansEntity.setMobileNumber(mobileNumber);
        loansEntity.setLoanType("Car Loan");
        loansEntity.setTotalLoan(100000);
        loansEntity.setAmountPaid(0);
        loansEntity.setOutStandingAmount(loansEntity.getTotalLoan()-loansEntity.getAmountPaid());
        return loansEntity;
    }

    private LoansEntity createNewLoan(String mobileNumber) {
        LoansEntity loansEntity = new LoansEntity();
        long number = (long) (new Random().nextDouble() * 1_000_000_000_000L);
        loansEntity.setLoanNumber(number+"");
        loansEntity.setMobileNumber(mobileNumber);
        loansEntity.setLoanType("Home Loan");
        loansEntity.setTotalLoan(100000);
        loansEntity.setAmountPaid(0);
        loansEntity.setOutStandingAmount(loansEntity.getTotalLoan()-loansEntity.getAmountPaid());
        return loansEntity;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public List<LoansDto> fetchLoanDetails(String mobileNumber) {
        List<LoansDto> listOfLoans = new ArrayList<>();
        List<LoansEntity> loansEntity = loanRepository.findByMobileNumber(mobileNumber);
        if(loansEntity.isEmpty()){
            throw new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber);
        } else {
            for(LoansEntity loan : loansEntity){
                    LoansDto loansDto = LoansMapper.maptoLoansDto(loan,new LoansDto());
                    listOfLoans.add(loansDto);
            }
        }
        return listOfLoans;
    }

    /**
     * @param loansDto
     * @return
     */
    @Override
    public boolean updateLoanDetails(LoansDto loansDto) {
        LoansEntity loansEntity = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "Loan Number", loansDto.getLoanNumber())
        );
        LoansEntity updatedLoanEntity = LoansMapper.maptoLoansEntity(loansEntity, loansDto);
        if(updatedLoanEntity.getOutStandingAmount() <= 0){
            updatedLoanEntity.setActive(false);
        }
        loanRepository.save(loansEntity);
        return true;
    }

    /**
     * @param loanNumber
     * @return
     */
    @Override
    public boolean deleteLoanDetails(String loanNumber) {
        LoansEntity loansEntity = loanRepository.findByLoanNumber(loanNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "Loan Number", loanNumber)
        );
        loansEntity.setActive(false);
        loanRepository.save(loansEntity);
        return true;
    }
}
