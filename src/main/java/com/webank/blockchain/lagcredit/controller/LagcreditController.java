package com.webank.blockchain.lagcredit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.blockchain.lagcredit.contracts.LAGCredit;
import com.webank.blockchain.lagcredit.service.LagCreditService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LagcreditController {
	
	 @Autowired
	 private LagCreditService lagCreditService;
	 
    @GetMapping("/testDeploy")
    public String testdeploy(){
        LAGCredit lagCredit = lagCreditService.deploy();
        String contractAddress = lagCredit.getContractAddress();
        log.info("LAGCredit address : {}", contractAddress);
        return contractAddress;
    }
}
