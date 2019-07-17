package com.webank.blockchain.lagcredit.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.blockchain.lagcredit.BaseTest;
import com.webank.blockchain.lagcredit.contracts.LAGCredit;
import com.webank.blockchain.lagcredit.contracts.PetCredit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PetCreditServiceTest  extends BaseTest {
	
	String address = "0x4f90547bff5de9dd65506834537097cb362fa883";
	
	   
    @Autowired
    private PetCreditService petCreditService;
    
    @Test
    public void testdeploy(){
    	PetCredit petCredit = petCreditService.deploy();
        log.info("PetCredit address : {}", petCredit.getContractAddress());
    }
    
    @Test
    public void testTestMsg(){
    	petCreditService.testMsg(address);
    }
    
}
