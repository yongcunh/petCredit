package com.webank.blockchain.lagcredit.service;

import java.math.BigInteger;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.blockchain.lagcredit.BaseTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableTestServiceTest   extends BaseTest {
	
	String contractAddress = "0xa3a7f96b83783e515adbbc1526b33afc784b3801";
	
	@Autowired
	TableTestService tableTestService;
	
	@Test
	public void testDeploy() {
		tableTestService.deploy();
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
