pragma solidity ^0.4.25;
import "./Table.sol";
 

contract PetCredit {
    event CreateResult(int count);
    event InsertResult(int count);
    event UpdateResult(int count);
    event RemoveResult(int count);


    // struct Pet {
    //     //int id;  
    //     string name;  
    //     string cate;  
    //     int bdate;  
    //     int price;  
    //     string desc;  
    //     bool active;  
    //     string extend;  
    //     address owner;
    // } 

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
  // Pet[] public lists;
        int public salesCount;  
     
     //bank
    mapping (address => int) private balances; 
    
    constructor() public { 
        admin = msg.sender;
        balances[admin]=10000;

        TableFactory tf = TableFactory(0x1001);  // TableFactory's address is fixed at 0x1001
        // To create a table t_test3. Table's key_field as name. Table's value_field as item_id and item_name.
        // key_field indicates the row that AMDB's primary key value_field represents in the table. The row can be multiple and spearated by commas.
        int count = tf.createTable("t_test3", "idx", "id,name,cate,bdate,price,desc,active,extend,owner");
        // emit CreateResult(count);

    }

// inquiry data
    function select(string memory name) public constant returns(bytes32[], int[], bytes32[]){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test3");

        // If the condition is empty, it means no filtering. You can use conditional filtering as needed.
        Condition condition = table.newCondition();

        Entries entries = table.select("name", condition);
        bytes32[] memory user_name_bytes_list = new bytes32[](uint256(entries.size()));
        int[] memory item_id_list = new int[](uint256(entries.size()));
        bytes32[] memory item_name_bytes_list = new bytes32[](uint256(entries.size()));

        for(int i=0; i<entries.size(); ++i) {
            Entry entry = entries.get(i);

            user_name_bytes_list[uint256(i)] = entry.getBytes32("id");
            item_id_list[uint256(i)] = entry.getInt("price");
            item_name_bytes_list[uint256(i)] = entry.getBytes32("cate");
        }

        return (user_name_bytes_list, item_id_list, item_name_bytes_list);
    }

    //add 1 new pet
    function add(string memory id,string memory name, string memory cate,  int bdate, int price, string memory desc, bool active, string memory extend) public returns (int)    {
       
       TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test3");

        Entry entry = table.newEntry();

        entry.set("id", id);
        entry.set("name", name);
        entry.set("cate", cate);
        entry.set("bdate", bdate);
        entry.set("price", price);
        entry.set("desc", desc);
        entry.set("active", active);
        entry.set("extend", extend);
        entry.set("owner", msg.sender);  

        int count = table.insert("name", entry);
// salesCount++;
         emit InsertResult(count);
        return count;

//      // lists.push(Pet(name,cate,bdate,price,desc,active,extend,msg.sender)); 
//      //   return lists.length-1;
    } 
     
 
 //get all pet in store
    function getCount() public constant returns (int) { 
        // return salesCount;
      TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test3");

        // If the condition is empty, it means no filtering. You can use conditional filtering as needed.
        Condition condition = table.newCondition();

         Entries entries = table.select("name", condition); 

       return entries.size();
    }
    

    function get(string memory id) public returns(Entry){ 
         TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test3");

        // If the condition is empty, it means no filtering. You can use conditional filtering as needed.
        Condition condition = table.newCondition();

        Entries entries = table.select("name", condition); 

            Entry entry = entries.get(0);
            return entry;
    }
    function _update(string memory id,Entry entry) private returns(Entry){ 
         TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test3");

        // If the condition is empty, it means no filtering. You can use conditional filtering as needed.
        Condition condition = table.newCondition();

        Entries entries = table.select("name", condition); 

        table.update(id,entry, condition); 
    }
     //buy active pet
    function buy(string memory id) public{  
            Entry entry = get(id);

        require( balances[msg.sender]>=entry.getInt("price"));
        if(!entry.getBoolean("active"))throw;
           balances[msg.sender]-=entry.getInt("price");
           balances[entry.getAddress("owner")]+=entry.getInt("price");
        entry.set("active", false); 
        entry.set("owner", msg.sender);   
        _update(id,entry); 
           salesCount++;
    }
     
     //seller or admin set their pet active
    function setActive(string memory id,bool active) public      { 
        Entry entry = get(id);

        require( entry.getAddress("owner")==msg.sender||admin==msg.sender);
        entry.set("active", active); 
        _update(id,entry);  
    }
      
     
  //    //avg, sum
  //   function priceState( ) public constant returns  ( int   ) { 
  //       require(isAdmin());
  //       int tmp=0;
  //      TableFactory tf = TableFactory(0x1001);
  //       Table table = tf.openTable("t_test3");

  //       // If the condition is empty, it means no filtering. You can use conditional filtering as needed.
  //       Condition condition = table.newCondition();

  //        Entries entries = table.select("name", condition); 
  //        return entries.size();
  // // for(int i=0; i<entries.size(); ++i) {
  // //           Entry entry = entries.get(i);
  // //   tmp+=entry.getInt("price"); 

  // //       } 
  // //           return (tmp);
  //   }
     //see how rich someone is
    function getCredit(address owner ) public constant returns (int   ) { 
     return balances[owner];
    }
    //admin charge control
    function addCredit(address owner1,int credit ) public   returns (int   ) { 
      require(isAdmin());
        balances[owner1]+=credit;
     return balances[owner1] ;
    }
    //admin force set pet owner
    function setOwner(string memory id,address owner ) public  { 
       require(isAdmin());
           Entry entry = get(id);

        require( entry.getAddress("owner")==msg.sender||admin==msg.sender);
        entry.set("owner", owner); 
        _update(id,entry);  
 
    }
    
 
    function isAdmin() public view returns (bool)  {
        return admin==msg.sender;
    }
    function me() public view returns (address)  {
        return  msg.sender;
    }
    
}