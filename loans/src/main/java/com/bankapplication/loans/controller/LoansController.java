package com.bankapplication.loans.controller;

import com.bankapplication.cards.constants.LoansConstants;
import com.bankapplication.loans.dto.LoansContactInfoDto;
import com.bankapplication.loans.dto.LoansDto;
import com.bankapplication.cards.dto.ResponseDto;
import com.bankapplication.loans.service.ILoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

    private ILoanService iLoanService;

    @Value("${loans.build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    public LoansController(ILoanService iLoanService) {
        this.iLoanService = iLoanService;
    }

    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDto> createAccount(@RequestParam String mobileNumber) {
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<LoansDto>> fetchCustomerDetails(@RequestParam String mobileNumber){
        List<LoansDto> loansDto = iLoanService.fetchLoanDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto);
    }

    @PostMapping("/updateLoan")
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody LoansDto loansDto){
        boolean isUpdated = iLoanService.updateLoanDetails(loansDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(LoansConstants.STATUS_500,LoansConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomerDetails(@RequestParam String loanNumber){
        boolean deleteCustomerDetails = iLoanService.deleteLoanDetails(loanNumber);
        if(deleteCustomerDetails){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(LoansConstants.STATUS_500, LoansConstants.MESSAGE_500));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
    }


}
