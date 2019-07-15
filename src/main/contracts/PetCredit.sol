pragma solidity ^0.4.25;
 

contract PetCredit {

    struct Pet {
        //uint id;  
        string name;  
        string cate;  
        uint bdate;  
        uint price;  
        string desc;  
        bool active;  
        string extend;  
        address owner;
    } 

//DEBUG
string public message;
 function setTestMessage(string memory newMessage)public{
 message=newMessage;

 }
 function getTestMessage()public view returns(string memory){
 return message;
 }
 
 
 address private admin;
 
 //pet store
  Pet[] public lists;
        uint256 public salesCount;  
     
     //bank
    mapping (address => uint256) private balances; 
    
    constructor() public { 
        admin = msg.sender;
        balances[admin]=10000;
    }

 
 //get all pet in store
    function getCount() public view returns (uint256) { 
       return lists.length ;
    }
    
    //add 1 new pet
    function add(  string memory name, string memory cate,  uint bdate, uint price, string memory desc, bool active, string memory extend) public returns (uint)    {
      
     lists.push(Pet(name,cate,bdate,price,desc,active,extend,msg.sender)); 
       return lists.length-1;
    } 
     
     //buy active pet
    function buy(uint i) public      { 
        if( balances[msg.sender]<lists[i].price)throw;
        if(!lists[i].active)throw;
           balances[msg.sender]-=lists[i].price;
           balances[lists[i].owner]+=lists[i].price;
           lists[i].active=false;
           lists[i].owner=msg.sender;
           salesCount++;
    }
     
     //seller or admin set their pet active
    function setActive(uint i,bool active) public      { 
        if( lists[i].owner!=msg.sender||admin!=msg.sender)throw;
           lists[i].active=active; 
    }
     
    function get(uint i) public view returns ( string memory , string memory ,  uint , uint , string memory,bool,address  ) {   
         return (lists[i].name,lists[i].cate,lists[i].bdate,lists[i].price,lists[i].desc,lists[i].active,lists[i].owner); 
    }
     
     //avg, sum
    function priceState( ) public view returns (uint ,uint   ) { 
        if(!isAdmin())throw;
        uint tmp=0;
            for (uint8 prop = 0; prop < lists.length; prop++)
            (tmp+=lists[prop].price ) ; 
            return (tmp/lists.length,tmp);
    }
     //see how rich someone is
    function getCredit(address owner ) public view returns (uint   ) { 
     return balances[owner];
    }
    //admin charge control
    function addCredit(address owner1,uint credit ) public   returns (uint   ) { 
        if(!isAdmin())throw;
        balances[owner1]+=credit;
     return balances[owner1] ;
    }
    //admin force set pet owner
    function setOwner(uint i,address owner ) public  { 
        if(!isAdmin())throw;
      lists[i].owner=owner;
    }
    
 
    function isAdmin() public view returns (bool)  {
        return admin==msg.sender;
    }
    function me() public view returns (address)  {
        return  msg.sender;
    }
    
}