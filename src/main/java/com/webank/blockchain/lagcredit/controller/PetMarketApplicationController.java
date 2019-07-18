package com.webank.blockchain.lagcredit.controller;

import com.webank.blockchain.lagcredit.constants.GasConstants;
import com.webank.blockchain.lagcredit.contracts.PetMarket;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.blockchain.lagcredit.service.PetMarketApplicationService;
import com.webank.blockchain.lagcredit.tools.UserKeyUtils;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
public class PetMarketApplicationController {

	static final int PUBLIC_KEY_SIZE = 64;
	static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
	
    @Autowired
    private PetMarketApplicationService applicationService;

    @GetMapping("/init")
    public String init() {
        applicationService.initPetMarketApplication();
        if (applicationService.petMarketContractAddress != null) {
            return "success: " + applicationService.petMarketContractAddress;
        }
        return "fail";
    }


    @Autowired
    private Web3j web3j;
    @Autowired
    private Credentials credentials;
    
    
    //    private String tableContractAddress;
//    private String applicationService.petMarketContractAddress;

//    @GetMapping("/deploy")
//    public String testdeploy() {
////        if(applicationService.petMarketContractAddress!=null)return applicationService.petMarketContractAddress;
//        PetMarket petMarket = null;
//        try {
//            petMarket = PetMarket.deploy(web3j, credentials, new StaticGasProvider(
//                    GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT), deployTable()).send();
//            applicationService.petMarketContractAddress = petMarket.getContractAddress();
//            log.info("address is {}", petMarket.getContractAddress());
//            return petMarket.getContractAddress();
//        } catch (Exception e) {
//            log.error("deploy lacg contract fail: {}", e.getMessage());
//        }
//        return null;
//    }
//
//
//    @GetMapping("/deployTable")
//    public String deployTable() {
//        PetTable petTable = null;
//        try {
//            petTable = PetTable.deploy(web3j, credentials, new StaticGasProvider(
//                    GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT)).send();
//            petTable.create().send();
//            log.info("LAGC address is {}", petTable.getContractAddress());
////            tableContractAddress =petTable.getContractAddress();
//            return petTable.getContractAddress();
//        } catch (Exception e) {
//            log.error("deploy lacg contract fail: {}", e.getMessage());
//        }
//        return null;
//    }

    public PetMarket load() {
        if (applicationService.petMarketContractAddress == null)
            init();
        PetMarket petMarket = PetMarket.load(applicationService.petMarketContractAddress, web3j, credentials, new StaticGasProvider(
                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return petMarket;
    }
    
    public PetMarket load(Credentials credentials ) {
        if (applicationService.petMarketContractAddress == null)
            init();
        PetMarket petMarket = PetMarket.load(applicationService.petMarketContractAddress, web3j, credentials, new StaticGasProvider(
                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return petMarket;
    }
    
    @GetMapping("/isAdmin")
    public Boolean isAdmin() {
        PetMarket PetMarket = load();
        if (PetMarket != null) {
            try {
                Boolean receipt = PetMarket.isAdmin().send();
                return receipt;
            } catch (Exception e) {
                log.error("getTestMsg fail: {}", e.getMessage());
            }
        }
        return null;
    }
    
    public Credentials userCredentials(String privateKey) throws Exception{
    	Credentials credentials = GenCredential.create(privateKey);
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }
        return credentials;
    }
    
    @GetMapping("/sale")
    public int sale(String name, String petType, BigInteger price, String description, String url, BigInteger birthday) throws Exception {
        PetMarket PetMarket = load();
        TransactionReceipt receipt = PetMarket.sale(name, petType, new BigInteger("1"), description, url, new BigInteger("1")).send();

        return getPetList().getValue1().size() + 999;
    }

    @GetMapping("/genUserKey")
    public String genUserKey() throws Exception {
    	ECKeyPair keyPair = Keys.createEcKeyPair();
        String publicKey = Numeric.toHexStringWithPrefixZeroPadded(keyPair.getPublicKey(), PUBLIC_KEY_LENGTH_IN_HEX);
        String privateKey = Numeric.toHexStringNoPrefix(keyPair.getPrivateKey());
        String address = "0x" + Keys.getAddress(publicKey);
        
        return "publicKey:" + publicKey + "\n privateKey:" + privateKey + "\naddress:" + address;
    	
    }
    
    @GetMapping("/createUser")
    public TransactionReceipt createUser(String privateKey) throws Exception {
    	PetMarket PetMarket = load();
    	
    	if(privateKey != null && !privateKey.isEmpty()) {
    		//使用用戶自己的private key建立帳號
    		PetMarket = load(userCredentials(privateKey));
    	}else {
    		//如果沒有傳入privateKey時, 就用系統的
    		PetMarket = load();
    	}
        
        TransactionReceipt receipt = PetMarket.createUser().send();

        return receipt;
    }
    
    
    
    @GetMapping("/cancel")
    public TransactionReceipt cancel(String id) throws Exception {
        PetMarket PetMarket = load();
        TransactionReceipt receipt = PetMarket.cancel(id).send();

        return receipt;
    }

    @GetMapping("/active")
    public TransactionReceipt active(String id) throws Exception {
        PetMarket PetMarket = load();
        TransactionReceipt receipt = PetMarket.active(id).send();

        return receipt;
    }

    @GetMapping("/modifyPrice")
    public TransactionReceipt modifyPrice(String id, BigInteger price) throws Exception {
        PetMarket PetMarket = load();
        TransactionReceipt receipt = PetMarket.modifyPrice(id, price).send();

        return receipt;
    }

    @GetMapping("/getBalance")
    public BigInteger getBalance() throws Exception {
        PetMarket PetMarket = load();
        BigInteger receipt = PetMarket.getBalance().send();

        return receipt;
    }

    @GetMapping("/getPetList")
    public Tuple7<List<String>, List<String>, List<String>, List<BigInteger>, List<String>, List<String>, List<String>> getPetList() throws Exception {
        PetMarket PetMarket = load();
        Tuple7<List<String>, List<String>, List<String>, List<BigInteger>, List<String>, List<String>, List<String>> receipt = PetMarket.getPetList().send();
        return receipt;
    }

    @GetMapping("/getPetDetail")
    public Tuple7<String, String, String, BigInteger, String, String, String> getPetDetail(String id) throws Exception {
        PetMarket PetMarket = load();
        Tuple7<String, String, String, BigInteger, String, String, String> receipt = PetMarket.getPetDetail(id).send();
        return receipt;
    }

    @GetMapping("/buyPet")
    public TransactionReceipt buyPet(String id) throws Exception {
        PetMarket PetMarket = load();
        TransactionReceipt receipt = PetMarket.buyPet(id).send();

        return receipt;
    }

    @GetMapping("/priceStatistics")
    public BigInteger priceStatistics(BigInteger a, BigInteger b) throws Exception {
        PetMarket PetMarket = load();
        BigInteger receipt = PetMarket.priceStatistics(a, b).send();

        return receipt;
    }

    @GetMapping("/avgPrice")
    public BigInteger avgPrice() throws Exception {
        PetMarket PetMarket = load();
        return PetMarket.avgPrice().send();
    }

    @GetMapping("/getSalesCount")
    public BigInteger getSalesCount() throws Exception {
        PetMarket PetMarket = load();
        return PetMarket.getSalesCount().send();
    }

    @GetMapping("/getTotalPrice")
    public BigInteger getTotalPrice() throws Exception {
        PetMarket PetMarket = load();
        return PetMarket.getTotalPrice().send();
    }
}
