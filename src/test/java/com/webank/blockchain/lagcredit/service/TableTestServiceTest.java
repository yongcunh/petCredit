package com.webank.blockchain.lagcredit.service;

import java.math.BigInteger;

import com.webank.blockchain.lagcredit.contracts.TableTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.blockchain.lagcredit.BaseTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableTestServiceTest   extends BaseTest {
	
	String contractAddress = "0x72f1de1a333e36c2c3ec45ac83d0a65d644fd754";
	
	@Autowired
	TableTestService tableTestService;
	
	@Test
	public void testDeploy() {
		TableTest tableTest = tableTestService.deploy();
//		contractAddress = tableTest.getContractAddress();
		log.info("TableTest address : {}", tableTest.getContractAddress());
	}
	
	@Test
	public void create() {
		tableTestService.create(contractAddress);
	}
	
	@Test 
	public void testInsert() {
		tableTestService.insert(contractAddress, "mega", new BigInteger("1"), "test1");
		tableTestService.insert(contractAddress, "mega", new BigInteger("2"), "test2");
		tableTestService.insert(contractAddress, "mega", new BigInteger("3"), "test3");
		tableTestService.insert(contractAddress, "t2", new BigInteger("1"), "test1");
		tableTestService.insert(contractAddress, "t2", new BigInteger("1"), "test2");
		tableTestService.insert(contractAddress, "t3", new BigInteger("5123123"), "test1asfasfasf");
	}
	
	@Test
	public void testSelect() {
		log.info("select mega");
		tableTestService.select(contractAddress, "mega");
		log.info("select t2");
		tableTestService.select(contractAddress, "t2");
		log.info("select t3");
		tableTestService.select(contractAddress, "t3");
		log.info("select empty");
		//測試結果返回空
		tableTestService.select(contractAddress, "");
		log.info("select null");
		//會爆錯
		tableTestService.select(contractAddress, null);
		log.info("select %%");
		//不可以用%%, 返回空
		tableTestService.select(contractAddress, "%%");
	}
}
