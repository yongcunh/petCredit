pragma solidity ^0.4.24;
pragma experimental ABIEncoderV2;
//import "./UsersTable.sol";
import "./PetTable.sol";

contract PetMarket{
    
    //UsersTable private usersTable = UsersTable(0x27ca8845e94ca5484c17d45bdc708ae1617e769c);
    PetTable private petTable; //petTable 地址, 使用amdb方式, 對寵物資料進行CRUD的操作
    
    address private admin; //管理員的地址
    int public salesCount;  //售賣次數
    int[] priceHisotry ; //售賣價格歷史記錄
    int256 totalPrice; //總售賣價格
    
    //bank
    mapping (address => int) private balances; 
    
    modifier onlyAdmin() {
        if(admin == msg.sender){
            _;
        }
    }
    
	//初始化 petMarket contract, 同時配置petTable的合約地址
    constructor(address _petTalbeAddress) public {
        admin = msg.sender;
        petTable = PetTable(_petTalbeAddress);
		salesCount = 0;
        totalPrice = 0;
    }
     
    //判斷自己是否為管理員
    function isAdmin() public constant onlyAdmin() returns(bool){
        return true;
    }
    
    //建立用戶及始化資金
    function createUser() public{
        //usersTable.insert(userName, 1000);
        balances[msg.sender] = 1000;
    }
	
    //填寫寵物資料及上架
    function sale(string name, string petType, int price, string description, string url, int birthday) public returns(bool){
        petTable.insert(name, petType, price, description, "true", url, birthday);
        return true;
    }
    
	//下架, 如為管理員, 可以強制下架
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
    
	//重新上架
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
	
	//修改價錢, 如為管理員, 可以強制修改
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
    
	//取得自已的餘額
    function getBalance() public constant returns(int){
        // Entry entry = usersTable.selectEntry(id);
        // if(!isAdmin() && entry.getAddress("user_address") != msg.sender) throw;
        // return entry.getInt("balance");
        return balances[msg.sender];
    }
    
	//返回全部寵物列表
    function getPetList() public constant returns(string[] memory ids,string[] memory name_list, string[] memory type_list,
	    int[] price_list, string[] memory description_list, string[] memory active_list, address[] memory owner_address_list){
        return petTable.selectAll();
    }
    
	//返回指定寵物的詳細資料
    function getPetDetail(string id) public constant returns(string, string, string, int, string, string, address){
        Entry entry = petTable.selectEntry(id);
        
		
        return _getPetDetail(entry);
    }
    
	/*折開兩個方法主要是因為這樣寫法可以增加多一個地址的輸出, 
	已經是極限了, 如果再加多一個返回的參數, 就會爆Stack too deep, try removing local variables
	*/
    function _getPetDetail(Entry entry) internal constant returns(string, string, string, int, string, string, address){
		string memory id = entry.getString("id");
		string memory name = entry.getString("name");
		string memory petType =entry.getString("petType");
		int  price =entry.getInt("price");
		string memory description=entry.getString("description");
		string memory active = entry.getString("active");
		address owner_address = entry.getAddress("owner_address");
		
		
        return (id, name, petType, price, description, active, owner_address);
    }
    
	//買寵物
	function buyPet(string id) public{  
       _buyPet(msg.sender, id);
    }
	
	//內部買寵物的處理邏輯
    function _buyPet(address buyer, string id) internal {
        //判斷寵物是否已上架
        Entry entry = petTable.selectEntry(id);
        require(keccak256(entry.getString("active")) == keccak256("true"));
        //判斷餘額是否足夠購買
        require(balances[msg.sender] >= entry.getInt("price"));
        //自已不可以同自己交易
        require(msg.sender != entry.getAddress("owner_address"));
        //更新買賣雙方的餘額
        balances[msg.sender] -= entry.getInt("price");
        balances[entry.getAddress("owner_address")] += entry.getInt("price");
        
        //更新寵物的資料
        entry.set("active", "false"); 
        entry.set("owner_address", msg.sender);   
        petTable.update(id, entry);
        
		//記錄統計資料
        salesCount++;
        priceHisotry.push(entry.getInt("price"));
        totalPrice+=entry.getInt("price");
        
    }
	
	//統計價格區間數量
    function priceStatistics(int start, int end) public constant returns(uint){
        uint count = 0;
        for(uint i = 0; i < priceHisotry.length; i++){
            if(priceHisotry[i] >= start && priceHisotry[i] <= end){
                count++;
            }
        }
        
        return count;
    }
    
	//平均售賣價格
    function avgPrice() public constant returns(int){
        return totalPrice / salesCount;
    }
    
	//售賣次數
    function getSalesCount() public constant returns(int){
        return salesCount;
    }
    
	//總售賣價格
    function getTotalPrice ()public constant returns(int){
        return totalPrice;
    }
     
    
}