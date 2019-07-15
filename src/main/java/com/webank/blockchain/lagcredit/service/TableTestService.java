package com.webank.blockchain.lagcredit.service;

import java.math.BigInteger;
import java.util.List;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.blockchain.lagcredit.constants.GasConstants;
import com.webank.blockchain.lagcredit.contracts.TableTest;
import com.webank.blockchain.lagcredit.tools.BaseBean;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TableTestService {
	
	@Autowired 
    private Web3j web3j;
    
	@Autowired 
    private Credentials credentials;
    
	@Autowired
	private StaticGasProvider staticGasProvider;
	
	public TableTest deploy() {
		TableTest tableTest = null;
        try {
        	tableTest = TableTest.deploy(web3j, credentials,staticGasProvider).send();
            log.info("tableTest address is {}", tableTest.getContractAddress());
            return tableTest;
        } catch (Exception e) {
          log.error("deploy lacg contract fail: {}", e.getMessage());
        }  
        return tableTest;
    }
	
	public void create(String contractAddress) {
		TableTest tableTest = load(contractAddress);
		tableTest.create();
	}
	
	public TableTest load(String contractAddress) {
		TableTest tableTest = null;
        try {
        	tableTest = TableTest.load(contractAddress, web3j, credentials, staticGasProvider);
            log.info("tableTest address is {}", tableTest.getContractAddress());
            return tableTest;
        } catch (Exception e) {
          log.error("deploy lacg contract fail: {}", e.getMessage());
        }  
        return tableTest;
    }
	
	public void insert(String contractAddress, String name, BigInteger item_id, String item_name) {
		TableTest tableTest = load(contractAddress);
		try {
			tableTest.insert(name, item_id, item_name).send();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public List<Object> select(String contractAddress, String name){
		TableTest tableTest = load(contractAddress);
		try {
			Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> tupel = tableTest.select(name).send();
			log.info("v1:" + BaseBean.print(tupel.getValue1()));
			log.info("v2:" + BaseBean.print(tupel.getValue2()));
			log.info("v3:" + BaseBean.print(tupel.getValue3()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
}
