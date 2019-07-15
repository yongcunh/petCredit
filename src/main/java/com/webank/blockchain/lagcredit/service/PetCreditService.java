package com.webank.blockchain.lagcredit.service;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.TransactionDecoder;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.blockchain.lagcredit.constants.GasConstants;
import com.webank.blockchain.lagcredit.contracts.LAGCredit;
import com.webank.blockchain.lagcredit.contracts.PetCredit;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PetCreditService {
	
	@Autowired 
    private Web3j web3j;
    @Autowired 
    private Credentials credentials;
	
    String address = "";
    
    public PetCredit deploy() {
    	PetCredit petCredit = null;
        try {
        	petCredit = PetCredit.deploy(web3j, credentials, new StaticGasProvider(
                    GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT)).send();
            log.info("LAGC address is {}", petCredit.getContractAddress());
            return petCredit;
        } catch (Exception e) {
          log.error("deploy lacg contract fail: {}", e.getMessage());
        }  
        return petCredit;
    }
    
    public PetCredit load(String creditAddress){
    	PetCredit petCredit = PetCredit.load(creditAddress, web3j, credentials, new StaticGasProvider(
                    GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return petCredit;
    }
    
    public void testMsg(String creditAddress) {
    	PetCredit petCredit = load(creditAddress);
    	if(petCredit != null) {
    		try {
    			TransactionReceipt receipt = petCredit.setTestMessage("testing").send();
    			log.info("receipt:" + receipt.toString());
				log.info("result:" + petCredit.getTestMessage().send());
			} catch (Exception e) {
				log.error("getTestMsg fail: {}", e.getMessage());
			}
    	}
    	
    }
	
}
