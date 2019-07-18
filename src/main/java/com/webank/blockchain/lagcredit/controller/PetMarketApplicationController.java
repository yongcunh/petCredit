package com.webank.blockchain.lagcredit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.blockchain.lagcredit.service.PetMarketApplicationService;

@RestController
public class PetMarketApplicationController {
	
	@Autowired
	private PetMarketApplicationService applicationService;
	
	@GetMapping("/init")
	public String init() {
		if(applicationService.initPetMarketApplication()) {
			return "sucess";
		}
		return "fail";
	}
	
}
