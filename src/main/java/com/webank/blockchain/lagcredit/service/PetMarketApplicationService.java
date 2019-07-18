package com.webank.blockchain.lagcredit.service;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.webank.blockchain.lagcredit.contracts.PetMarket;
import com.webank.blockchain.lagcredit.contracts.PetTable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PetMarketApplicationService {
	
	@Autowired 
    private Web3j web3j;
	
    @Autowired 
    private Credentials credentials;
	
    @Autowired
	private StaticGasProvider staticGasProvider;
    
    @Value("${address-config.petMarket-contract-address}")
    public String petMarketContractAddress;
    
	/**
	 * 第一次執行部署時，才需要執行, 用於部署petTable以及petMarket兩張智能合約, 之後將生成的地址記下, 在appliation.yml配置
	 */
    public String initPetMarketApplication() {
		
		PetTable petTable = null;
        PetMarket petMakret  = null;
		try {
        	//deploy petTable smart contract
        	petTable = PetTable.deploy(web3j, credentials, staticGasProvider).send();
            log.info("petTable address is {}", petTable.getContractAddress());
            //create t_pet table
            petTable.create().send();
            
            //deploy petMarket smart contarct
            petMakret = PetMarket.deploy(web3j, credentials, staticGasProvider, petTable.getContractAddress()).send();
            log.info("petMakret address is {}", petMakret.getContractAddress());
            log.info("admin address is {}", credentials.getAddress());
            petMarketContractAddress = petMakret.getContractAddress();
            return petMarketContractAddress;
        } catch (Exception e) {
          log.error("deploy contract fail: {}", e.getMessage());

        }

        return null;
		
	}
	
}
