package com.webank.blockchain.lagcredit.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class PetCredit extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061271060046000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550611a58806100c96000396000f3006080604052600436106100e6576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806350c4f1ee146100eb57806357344e6f1461011d578063682789a8146101745780637d27d66c1461019f5780637fd39247146102005780639507d39a1461024d57806396102cf414610417578063a87d942c1461064d578063b6db75a014610678578063c373fa1e146106a7578063cb12b48f14610816578063d96a094a1461086d578063dc9ba78c1461089a578063e188a97c14610903578063e21f37ce14610993578063e60a955d14610a23575b600080fd5b3480156100f757600080fd5b50610100610a5c565b604051808381526020018281526020019250505060405180910390f35b34801561012957600080fd5b5061015e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610adf565b6040518082815260200191505060405180910390f35b34801561018057600080fd5b50610189610b28565b6040518082815260200191505060405180910390f35b3480156101ab57600080fd5b506101ea600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610b2e565b6040518082815260200191505060405180910390f35b34801561020c57600080fd5b5061024b60048036038101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610bd8565b005b34801561025957600080fd5b5061027860048036038101908080359060200190929190505050610c4e565b60405180806020018060200188815260200187815260200180602001861515151581526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184810384528b818151815260200191508051906020019080838360005b838110156103085780820151818401526020810190506102ed565b50505050905090810190601f1680156103355780820380516001836020036101000a031916815260200191505b5084810383528a818151815260200191508051906020019080838360005b8381101561036e578082015181840152602081019050610353565b50505050905090810190601f16801561039b5780820380516001836020036101000a031916815260200191505b50848103825287818151815260200191508051906020019080838360005b838110156103d45780820151818401526020810190506103b9565b50505050905090810190601f1680156104015780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b34801561042357600080fd5b5061044260048036038101908080359060200190929190505050610f57565b6040518080602001806020018981526020018881526020018060200187151515158152602001806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200185810385528d818151815260200191508051906020019080838360005b838110156104d65780820151818401526020810190506104bb565b50505050905090810190601f1680156105035780820380516001836020036101000a031916815260200191505b5085810384528c818151815260200191508051906020019080838360005b8381101561053c578082015181840152602081019050610521565b50505050905090810190601f1680156105695780820380516001836020036101000a031916815260200191505b50858103835289818151815260200191508051906020019080838360005b838110156105a2578082015181840152602081019050610587565b50505050905090810190601f1680156105cf5780820380516001836020036101000a031916815260200191505b50858103825287818151815260200191508051906020019080838360005b838110156106085780820151818401526020810190506105ed565b50505050905090810190601f1680156106355780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b34801561065957600080fd5b5061066261123b565b6040518082815260200191505060405180910390f35b34801561068457600080fd5b5061068d611248565b604051808215151515815260200191505060405180910390f35b3480156106b357600080fd5b50610800600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803515159060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506112a0565b6040518082815260200191505060405180910390f35b34801561082257600080fd5b5061082b61142a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561087957600080fd5b5061089860048036038101908080359060200190929190505050611432565b005b3480156108a657600080fd5b50610901600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061169e565b005b34801561090f57600080fd5b506109186116b8565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561095857808201518184015260208101905061093d565b50505050905090810190601f1680156109855780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561099f57600080fd5b506109a861175a565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156109e85780820151818401526020810190506109cd565b50505050905090810190601f168015610a155780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b348015610a2f57600080fd5b50610a5a600480360381019080803590602001909291908035151590602001909291905050506117f8565b005b600080600080610a6a611248565b1515610a7557600080fd5b60009150600090505b6002805490508160ff161015610ac35760028160ff16815481101515610aa057fe5b906000526020600020906008020160030154820191508080600101915050610a7e565b60028054905082811515610ad357fe5b04829350935050509091565b6000600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60035481565b6000610b38611248565b1515610b4357600080fd5b81600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b610be0611248565b1515610beb57600080fd5b80600283815481101515610bfb57fe5b906000526020600020906008020160070160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6060806000806060600080600288815481101515610c6857fe5b9060005260206000209060080201600001600289815481101515610c8857fe5b906000526020600020906008020160010160028a815481101515610ca857fe5b90600052602060002090600802016002015460028b815481101515610cc957fe5b90600052602060002090600802016003015460028c815481101515610cea57fe5b906000526020600020906008020160040160028d815481101515610d0a57fe5b906000526020600020906008020160050160009054906101000a900460ff1660028e815481101515610d3857fe5b906000526020600020906008020160070160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16868054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610dff5780601f10610dd457610100808354040283529160200191610dff565b820191906000526020600020905b815481529060010190602001808311610de257829003601f168201915b50505050509650858054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e9b5780601f10610e7057610100808354040283529160200191610e9b565b820191906000526020600020905b815481529060010190602001808311610e7e57829003601f168201915b50505050509550828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f375780601f10610f0c57610100808354040283529160200191610f37565b820191906000526020600020905b815481529060010190602001808311610f1a57829003601f168201915b505050505092509650965096509650965096509650919395979092949650565b600281815481101515610f6657fe5b9060005260206000209060080201600091509050806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110125780601f10610fe757610100808354040283529160200191611012565b820191906000526020600020905b815481529060010190602001808311610ff557829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110b05780601f10611085576101008083540402835291602001916110b0565b820191906000526020600020905b81548152906001019060200180831161109357829003601f168201915b505050505090806002015490806003015490806004018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561115a5780601f1061112f5761010080835404028352916020019161115a565b820191906000526020600020905b81548152906001019060200180831161113d57829003601f168201915b5050505050908060050160009054906101000a900460ff1690806006018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561120b5780601f106111e05761010080835404028352916020019161120b565b820191906000526020600020905b8154815290600101906020018083116111ee57829003601f168201915b5050505050908060070160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905088565b6000600280549050905090565b60003373ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614905090565b60006002610100604051908101604052808a815260200189815260200188815260200187815260200186815260200185151581526020018481526020013373ffffffffffffffffffffffffffffffffffffffff1681525090806001815401808255809150509060018203906000526020600020906008020160009091929091909150600082015181600001908051906020019061133e929190611907565b50602082015181600101908051906020019061135b929190611907565b506040820151816002015560608201518160030155608082015181600401908051906020019061138c929190611907565b5060a08201518160050160006101000a81548160ff02191690831515021790555060c08201518160060190805190602001906113c9929190611907565b5060e08201518160070160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050506001600280549050039050979650505050505050565b600033905090565b60028181548110151561144157fe5b906000526020600020906008020160030154600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561149e57600080fd5b6002818154811015156114ad57fe5b906000526020600020906008020160050160009054906101000a900460ff1615156114d757600080fd5b6002818154811015156114e657fe5b906000526020600020906008020160030154600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555060028181548110151561155357fe5b9060005260206000209060080201600301546004600060028481548110151561157857fe5b906000526020600020906008020160070160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550600060028281548110151561160257fe5b906000526020600020906008020160050160006101000a81548160ff0219169083151502179055503360028281548110151561163a57fe5b906000526020600020906008020160070160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060036000815480929190600101919050555050565b80600090805190602001906116b4929190611987565b5050565b606060008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117505780601f1061172557610100808354040283529160200191611750565b820191906000526020600020905b81548152906001019060200180831161173357829003601f168201915b5050505050905090565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156117f05780601f106117c5576101008083540402835291602001916117f0565b820191906000526020600020905b8154815290600101906020018083116117d357829003601f168201915b505050505081565b3373ffffffffffffffffffffffffffffffffffffffff1660028381548110151561181e57fe5b906000526020600020906008020160070160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415806118c157503373ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614155b156118cb57600080fd5b806002838154811015156118db57fe5b906000526020600020906008020160050160006101000a81548160ff0219169083151502179055505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061194857805160ff1916838001178555611976565b82800160010185558215611976579182015b8281111561197557825182559160200191906001019061195a565b5b5090506119839190611a07565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106119c857805160ff19168380011785556119f6565b828001600101855582156119f6579182015b828111156119f55782518255916020019190600101906119da565b5b509050611a039190611a07565b5090565b611a2991905b80821115611a25576000816000905550600101611a0d565b5090565b905600a165627a7a723058201b85c8f8e4e82fff2d1c5a3397bf6a5ece7472c8bedecf7590e13356e7c3aa3d0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"priceState\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"owner\",\"type\":\"address\"}],\"name\":\"getCredit\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"salesCount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"owner1\",\"type\":\"address\"},{\"name\":\"credit\",\"type\":\"uint256\"}],\"name\":\"addCredit\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"},{\"name\":\"owner\",\"type\":\"address\"}],\"name\":\"setOwner\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"lists\",\"outputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"cate\",\"type\":\"string\"},{\"name\":\"bdate\",\"type\":\"uint256\"},{\"name\":\"price\",\"type\":\"uint256\"},{\"name\":\"desc\",\"type\":\"string\"},{\"name\":\"active\",\"type\":\"bool\"},{\"name\":\"extend\",\"type\":\"string\"},{\"name\":\"owner\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getCount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"isAdmin\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"cate\",\"type\":\"string\"},{\"name\":\"bdate\",\"type\":\"uint256\"},{\"name\":\"price\",\"type\":\"uint256\"},{\"name\":\"desc\",\"type\":\"string\"},{\"name\":\"active\",\"type\":\"bool\"},{\"name\":\"extend\",\"type\":\"string\"}],\"name\":\"add\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"me\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"buy\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"newMessage\",\"type\":\"string\"}],\"name\":\"setTestMessage\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getTestMessage\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"message\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"},{\"name\":\"active\",\"type\":\"bool\"}],\"name\":\"setActive\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    public static final String FUNC_PRICESTATE = "priceState";

    public static final String FUNC_GETCREDIT = "getCredit";

    public static final String FUNC_SALESCOUNT = "salesCount";

    public static final String FUNC_ADDCREDIT = "addCredit";

    public static final String FUNC_SETOWNER = "setOwner";

    public static final String FUNC_GET = "get";

    public static final String FUNC_LISTS = "lists";

    public static final String FUNC_GETCOUNT = "getCount";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_ADD = "add";

    public static final String FUNC_ME = "me";

    public static final String FUNC_BUY = "buy";

    public static final String FUNC_SETTESTMESSAGE = "setTestMessage";

    public static final String FUNC_GETTESTMESSAGE = "getTestMessage";

    public static final String FUNC_MESSAGE = "message";

    public static final String FUNC_SETACTIVE = "setActive";

    @Deprecated
    protected PetCredit(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PetCredit(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PetCredit(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PetCredit(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> priceState() {
        final Function function = new Function(FUNC_PRICESTATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getCredit(String owner) {
        final Function function = new Function(FUNC_GETCREDIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> salesCount() {
        final Function function = new Function(FUNC_SALESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addCredit(String owner1, BigInteger credit) {
        final Function function = new Function(
                FUNC_ADDCREDIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(owner1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(credit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addCredit(String owner1, BigInteger credit, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDCREDIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(owner1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(credit)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String addCreditSeq(String owner1, BigInteger credit) {
        final Function function = new Function(
                FUNC_ADDCREDIT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(owner1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(credit)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> setOwner(BigInteger i, String owner) {
        final Function function = new Function(
                FUNC_SETOWNER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(owner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setOwner(BigInteger i, String owner, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETOWNER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(owner)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setOwnerSeq(BigInteger i, String owner) {
        final Function function = new Function(
                FUNC_SETOWNER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(owner)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple7<String, String, BigInteger, BigInteger, String, Boolean, String>> get(BigInteger i) {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple7<String, String, BigInteger, BigInteger, String, Boolean, String>>(
                new Callable<Tuple7<String, String, BigInteger, BigInteger, String, Boolean, String>>() {
                    @Override
                    public Tuple7<String, String, BigInteger, BigInteger, String, Boolean, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, BigInteger, BigInteger, String, Boolean, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (String) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<Tuple8<String, String, BigInteger, BigInteger, String, Boolean, String, String>> lists(BigInteger param0) {
        final Function function = new Function(FUNC_LISTS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple8<String, String, BigInteger, BigInteger, String, Boolean, String, String>>(
                new Callable<Tuple8<String, String, BigInteger, BigInteger, String, Boolean, String, String>>() {
                    @Override
                    public Tuple8<String, String, BigInteger, BigInteger, String, Boolean, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, String, BigInteger, BigInteger, String, Boolean, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (String) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getCount() {
        final Function function = new Function(FUNC_GETCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isAdmin() {
        final Function function = new Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> add(String name, String cate, BigInteger bdate, BigInteger price, String desc, Boolean active, String extend) {
        final Function function = new Function(
                FUNC_ADD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(cate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(bdate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(price), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(desc), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(extend)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void add(String name, String cate, BigInteger bdate, BigInteger price, String desc, Boolean active, String extend, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(cate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(bdate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(price), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(desc), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(extend)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String addSeq(String name, String cate, BigInteger bdate, BigInteger price, String desc, Boolean active, String extend) {
        final Function function = new Function(
                FUNC_ADD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(cate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(bdate), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(price), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(desc), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(extend)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> me() {
        final Function function = new Function(FUNC_ME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> buy(BigInteger i) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void buy(BigInteger i, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String buySeq(BigInteger i) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> setTestMessage(String newMessage) {
        final Function function = new Function(
                FUNC_SETTESTMESSAGE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(newMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setTestMessage(String newMessage, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETTESTMESSAGE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(newMessage)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setTestMessageSeq(String newMessage) {
        final Function function = new Function(
                FUNC_SETTESTMESSAGE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(newMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> getTestMessage() {
        final Function function = new Function(FUNC_GETTESTMESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> message() {
        final Function function = new Function(FUNC_MESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setActive(BigInteger i, Boolean active) {
        final Function function = new Function(
                FUNC_SETACTIVE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setActive(BigInteger i, Boolean active, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETACTIVE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setActiveSeq(BigInteger i, Boolean active) {
        final Function function = new Function(
                FUNC_SETACTIVE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i), 
                new org.fisco.bcos.web3j.abi.datatypes.Bool(active)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static PetCredit load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetCredit(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PetCredit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetCredit(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PetCredit load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PetCredit(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PetCredit load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PetCredit(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PetCredit> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PetCredit.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PetCredit> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PetCredit.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PetCredit> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PetCredit.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PetCredit> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PetCredit.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}