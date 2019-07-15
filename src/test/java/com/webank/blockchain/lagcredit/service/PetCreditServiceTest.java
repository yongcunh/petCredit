package com.webank.blockchain.lagcredit.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.blockchain.lagcredit.BaseTest;
import com.webank.blockchain.lagcredit.contracts.LAGCredit;
import com.webank.blockchain.lagcredit.contracts.PetCredit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PetCreditServiceTest  extends BaseTest {
	
	String address = "0xb044d89b97d0daeaf3e851b399f50ac19a7c41f7";
	
	   
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
