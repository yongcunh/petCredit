package com.webank.blockchain.lagcredit.config;

import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.webank.blockchain.lagcredit.constants.GasConstants;

@Configuration
public class StaticGasProviderConfig {
	
	@Bean
	public StaticGasProvider staticGasProvider() {
		return new StaticGasProvider(
                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT);
	}
}
