pragma solidity ^0.4.24;
//pragma experimental ABIEncoderV2;
import "./UsersTable.sol";
import "./PetTable.sol";

contract PetMarket{
    
    UsersTable usersTable = UsersTable(0x58fa2e7a9a13e36e74469b0747c8388a64469380);
    PetTable petTable = PetTable(0x4e3e0cbf93e9e3fae5d4ba67d9fa513e4c3d6293);
    
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
    
	function cancel() public returns(uint){
        //admin or owner
        
    }
	
    function modifyPrice(string id, int price) public returns(bool){
        //entry = petTable.select();
        
        //update price
        
        return true;
    }
    
    function getBalance() public constant returns(int){
        //UsersTable.select()
        
        return 0;
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
    
   
    
    function getSaleCount() public constant returns(uint){
        return salesCount;
    }
     
}