pragma solidity ^0.4.24;
pragma experimental ABIEncoderV2;
import "./Table.sol";

contract PetTable {
	event CreateResult(int count);
    event InsertResult(int count);
    event UpdateResult(int count);
    event RemoveResult(int count);

    uint t_pet_seq = 1000; //t_pet table's sequence
     
	//記錄所有pet的id index
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
    
    //建立table, 當第一次部署合約時, 才需要執行
    function create() public onlyAdmin() returns(int) {
        TableFactory tf = TableFactory(0x1001); //The fixed address is 0x1001 for TableFactory
        //第一個參數t_pet 為table name, 成個節點通用, 所以一但建立後, 就算在其他節點或者其他合約, 都不能建立, 會爆table already exist
        //第二個參數id 為table key, 最基本的搜索至少包括這個key
		//第三個參數為表的各個欄位
		int count = tf.createTable("t_pet", "id", "name, petType, birthday, price, description, active, url, owner_address");
		emit CreateResult(count);

	    return count;
    }
	
	//根據TableTest.sol 寫的查詢方法, 可以找到指定key中的數據,並以Tuple方法返回, 不過由於返回的參數數量有限, 所以只能返回一些基本資料
    function select(string idx) public onlyAdmin() constant returns(string[] memory ids,string[] memory name_list, string[] memory type_list, int[] price_list, string[] memory description_list, string[] memory active_list, address[] memory owner_address_list){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Condition condition = table.newCondition();
        
        Entries entries = table.select(idx, condition);
        
		ids=new string[](uint256(entries.size()));
		name_list=new string[](uint256(entries.size()));
		type_list=new string[](uint256(entries.size()));
		price_list=new int[](uint256(entries.size()));
		description_list=new string[](uint256(entries.size()));
		active_list=new string[](uint256(entries.size()));
		owner_address_list=new address[](uint256(entries.size()));

		for(int i=0; i<entries.size(); ++i) {
			Entry entry = entries.get(i);

    		ids[uint256(i)]=entry.getString("id");
    		name_list[uint256(i)]=entry.getString("name");
    		type_list[uint256(i)]=entry.getString("petType");
    		price_list[uint256(i)]=entry.getInt("price");
    		description_list[uint256(i)]=entry.getString("description");
    		active_list[uint256(i)]=entry.getString("active");
    		owner_address_list[uint256(i)]=entry.getAddress("owner_address");
		}
 
    }
	
	//搜索指定key, 返回Entry
    function selectEntry(string id) public  constant returns(Entry){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Condition condition = table.newCondition();
        
        Entries entries = table.select(id, condition);
        
	    if(entries.size() > 0){
	        return entries.get(0);
	    }
 
    }
	
	//insert records
    function insert(string name, string petType, int price, string description, string active, string url, int birthday) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Entry entry = table.newEntry();
        string memory id = uint2str(t_pet_seq++);
        entry.set("id", id);
        entry.set("name",name);
        entry.set("petType",petType);
        entry.set("price",price);
        entry.set("description", description);
        entry.set("active", active);
        entry.set("url", url);
        entry.set("birthday", birthday);
        entry.set("owner_address", msg.sender);
        
        int count = table.insert(id, entry);
        emit InsertResult(count);
		
		id_index.push(id);
		
        return count;
    }
    //update records
    function update(string id, string name, string petType, int price, string description, string active, string url, int birthday) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Entry entry = table.newEntry();
        entry.set("id", id);
        entry.set("name",name);
        entry.set("petType",petType);
        entry.set("price",price);
        entry.set("description", description);
        entry.set("active", active);
        entry.set("url", url);
        entry.set("birthday", birthday);
        entry.set("owner_address", msg.sender);
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);

        int count = table.update(id, entry, condition);
        emit UpdateResult(count);
        
        return count;
    }
    
    function update(string id, Entry entry) public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);
        
        int count = table.update(id, entry, condition);
        emit UpdateResult(count);
        
        return count;
    }

    
    //remove records
    function remove(string id) public onlyAdmin() returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Condition condition = table.newCondition();
        condition.EQ("id", id);
        
        int count = table.remove(id, condition);
        emit RemoveResult(count);
        
        return count;
    }
    
    //查詢全部記錄, 由於amdb的select必須指定key, 所以建立了id_index array 記錄所有pet的id, 再遍歷id_index找出t_pet全部記錄
    function selectAll() public constant returns(string[] memory ids,string[] memory name_list, string[] memory type_list,
	    int[] price_list, string[] memory description_list, string[] memory active_list, address[] memory owner_address_list){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_pet");
        
        Condition condition = table.newCondition();
        
        ids=new string[](uint256(id_index.length));
		name_list=new string[](uint256(id_index.length));
		type_list=new string[](uint256(id_index.length));
		price_list=new int[](uint256(id_index.length));
		description_list=new string[](uint256(id_index.length));
		active_list=new string[](uint256(id_index.length));
		owner_address_list=new address[](uint256(id_index.length));
		
		for(uint i = 0; i < id_index.length; ++i){
		    bytes memory tempEmptyStringTest = bytes(id_index[i]); 
            //判斷id_index指定元素是否有內容
			if (tempEmptyStringTest.length == 0) {
                // emptyStringTest is an empty string
            } else {
                Entries entries = table.select(id_index[i], condition);
        			if(entries.size() > 0){
        			    Entry entry = entries.get(0);
        
                		ids[uint256(i)]=entry.getString("id");
                		name_list[uint256(i)]=entry.getString("name");
                		type_list[uint256(i)]=entry.getString("petType");
                		price_list[uint256(i)]=entry.getInt("price");
                		description_list[uint256(i)]=entry.getString("description");
                		active_list[uint256(i)]=entry.getString("active");
                		owner_address_list[uint256(i)]=entry.getAddress("owner_address");
        			}
            }
		    
		}
    }
    
    function getIdIndex() constant  returns(string[]){
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