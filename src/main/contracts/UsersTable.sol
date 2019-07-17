pragma solidity ^0.4.24;
pragma experimental ABIEncoderV2;
import "./Table.sol";

contract UsersTable {
	event CreateResult(int count);
    event InsertResult(int count);
    event UpdateResult(int count);
    event RemoveResult(int count);
    
    uint t_users_seq = 1000; //t_users table's sequence
    
	//記錄所有user的id index
	string[] id_index;
	
	address private admin; //管理員的地址
	
	modifier onlyAdmin() {
        if(admin == msg.sender){
            _;
        }
    }
    
	constructor() public {
        admin = msg.sender;
    }
    
    //create table
    function create() public onlyAdmin() returns(int){
        TableFactory tf = TableFactory(0x1001); //The fixed address is 0x1001 for TableFactory
        
        int count = tf.createTable("t_users", "id", "userName, balance, user_address");
		emit CreateResult(count);

	    return count;
    }
	
	//select records
    function select(string idx) public constant returns(string[] memory ids,string[] memory userName_list, 
    int[] memory balance_list, address[] memory user_address_list){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Condition condition = table.newCondition();
        
        Entries entries = table.select(idx, condition);
        
		ids=new string[](uint256(entries.size()));
		userName_list=new string[](uint256(entries.size()));
		balance_list=new int[](uint256(entries.size()));
		user_address_list=new address[](uint256(entries.size()));

		for(int i=0; i<entries.size(); ++i) {
			Entry entry = entries.get(i);

    		ids[uint256(i)]=entry.getString("id");
    		userName_list[uint256(i)]=entry.getString("userName");
    		balance_list[uint256(i)]=entry.getInt("balance");
    		user_address_list[uint256(i)]=entry.getAddress("user_address");
		}
 
    }
	
	//select records
    function selectEntry(string id) public constant returns(Entry){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Condition condition = table.newCondition();
        
        Entries entries = table.select(id, condition);
        
	    if(entries.size() > 0){
	        return entries.get(0);
	    }
 
    }
    
	//insert records
    function insert(string userName, int balance) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Entry entry = table.newEntry();
        string memory id = uint2str(t_users_seq++);
        entry.set("id", id);
        entry.set("userName",userName);
        entry.set("balance",balance);
        entry.set("user_address", msg.sender);
        
        int count = table.insert(id, entry);
        emit InsertResult(count);
		
		id_index.push(id);
		
        return count;
    }
    
    //update records
    function update(string id, string userName, int balance) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Entry entry = table.newEntry();
        entry.set("id", id);
        entry.set("userName",userName);
        entry.set("balance",balance);
        entry.set("user_address", msg.sender);
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);

        int count = table.update(id, entry, condition);
        emit UpdateResult(count);
        
        return count;
    }
    
    function update(string id, Entry entry) public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);
        
        int count = table.update(id, entry, condition);
        emit UpdateResult(count);
        
        return count;
    }
    
    
    //remove records
    function remove(string id) public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);
        
        int count = table.remove(id, condition);
        emit RemoveResult(count);
        
        return count;
    }
    
    //select all records
    function selectAll() public constant returns(string[] memory ids,string[] memory userName_list, 
    int[] memory balance_list, address[] memory user_address_list){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_users");
        
        Condition condition = table.newCondition();
        
        ids=new string[](uint256(id_index.length));
		userName_list=new string[](uint256(id_index.length));
		balance_list=new int[](uint256(id_index.length));
		user_address_list=new address[](uint256(id_index.length));
		
		for(uint i = 0; i < id_index.length; ++i){
		    bytes memory tempEmptyStringTest = bytes(id_index[i]); // Uses memory
            if (tempEmptyStringTest.length == 0) {
                // emptyStringTest is an empty string
            } else {
                Entries entries = table.select(id_index[i], condition);
        			if(entries.size() > 0){
        			    Entry entry = entries.get(0);
        
                		ids[uint256(i)]=entry.getString("id");
                		userName_list[uint256(i)]=entry.getString("userName");
                		balance_list[uint256(i)]=entry.getInt("balance");
                		user_address_list[uint256(i)]=entry.getAddress("user_address");
        			}
            }
		    
		}
    }
    
    function getIdIndex() constant returns(string[]){
        return id_index;
    }
    
    function uint2str(uint i) internal pure returns (string){
        if (i == 0) return "0";
        uint j = i;
        uint length;
        while (j != 0){
            length++;
            j /= 10;
        }
        bytes memory bstr = new bytes(length);
        uint k = length - 1;
        while (i != 0){
            bstr[k--] = byte(48 + i % 10);
            i /= 10;
        }
        return string(bstr);
   }
	
}