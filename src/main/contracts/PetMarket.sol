pragma solidity ^0.4.24;
//pragma experimental ABIEncoderV2;
import "./UsersTable.sol";
import "./PetTable.sol";

contract PetMarket{
    
    UsersTable private usersTable = UsersTable(0x27ca8845e94ca5484c17d45bdc708ae1617e769c);
    PetTable private petTable = PetTable(0x22a8519ab696e18fd6dd77a1c4eaab5b7d50fbe2);
    
    address private admin; //管理員的地址
    uint public salesCount;  //sales count
    int[] priceHisotry ; //sale price history
    
    modifier onlyAdmin() {
        if(admin == msg.sender){
            _;
        }
    }
    
    constructor() public {
        admin = msg.sender;
        salesCount = 0;
    }
     
    
    function isAdmin() public constant onlyAdmin() returns(bool){
        return true;
    }
    
    
    function createUser(string userName) public{
        usersTable.insert(userName, 1000);
    }
    
    function sale(string name, string petType, int price, string description, string url, int birthday) public returns(bool){
        petTable.insert(name, petType, price, description, "true", url, birthday);
		salesCount++;
        return true;
    }
    
	function cancel(string id) public{
        Entry entry = petTable.selectEntry(id);
        entry.set("active", "false");
        if(isAdmin()){
            petTable.update(id, entry);   
        }else{
            address owner_address = entry.getAddress("owner_address");
            if(owner_address != msg.sender)throw;
            petTable.update(id, entry);   
        }
        
    }
    
    function active(string id) public{
        Entry entry = petTable.selectEntry(id);
        entry.set("active", "true");
        if(isAdmin()){
            petTable.update(id, entry);   
        }else{
            address owner_address = entry.getAddress("owner_address");
            if(owner_address != msg.sender)throw;
            petTable.update(id, entry);   
        }
        
    }
	
    function modifyPrice(string id, int price) public {
        Entry entry = petTable.selectEntry(id);
        entry.set("price", price);
        if(isAdmin()){
            petTable.update(id, entry);   
        }else{
            address owner_address = entry.getAddress("owner_address");
            if(owner_address != msg.sender)throw;
            petTable.update(id, entry);   
        }
    }
    
    function getBalance(string id) public constant returns(int){
        Entry entry = usersTable.selectEntry(id);
        if(!isAdmin() && entry.getAddress("user_address") != msg.sender) throw;
        return entry.getInt("balance");
        
    }
    
    function getPetList() public constant returns(string){
        //selectAll
        return "id";
    }
    
    function getPetDetail(string id) public constant returns(string){
        //select 
        
        return "id";
    }
    
    function buyPet(string id) public returns(bool){
        //price enght?
        
        //pet active?
        
        //update pet
        
        //update user balance
        
        
        return true;
    }
    
    function priceStatistics(uint start, int end) public constant returns(uint){
        
        
        return 0;
    }
    
    function priceAvg() public constant returns(uint){
        
        return 0;
    }
    
   
    
    function getSalesCount() public constant returns(uint){
        return salesCount;
    }
     
    function address2str(address x) internal pure returns (string) {
        bytes memory b = new bytes(20);
        for (uint i = 0; i < 20; i++)
            b[i] = byte(uint8(uint(x) / (2**(8*(19 - i)))));
        
        return string(b);
    } 
}