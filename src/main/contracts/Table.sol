//https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/manual/smart_contract.html
pragma solidity ^0.4.24;

contract TableFactory {
    function openTable(string) public constant returns (Table);  // open table
    function createTable(string,string,string) public returns(int);  // create table
}

// inquiry conditions
contract Condition {
    //equal to
    function EQ(string, int) public;
    function EQ(string, string) public;

    //unequal to
    function NE(string, int) public;
    function NE(string, string)  public;

    //greater than
    function GT(string, int) public;
    //greater than or equal to
    function GE(string, int) public;

    //smaller than
    function LT(string, int) public;
    //smaller than or equal to
    function LE(string, int) public;

    //limit the number of return record
    function limit(int) public;
    function limit(int, int) public;
}

// single entry data record
contract Entry {
    function getInt(string) public constant returns(int);
    function getBoolean(string) public constant returns(bool);
    function getAddress(string) public constant returns(address);
    function getBytes64(string) public constant returns(byte[64]);
    function getBytes32(string) public constant returns(bytes32);

    function set(string, int) public;
    function set(string, bool) public;
    function set(string, string) public;
    function set(string, address) public;
}

// data record set
contract Entries {
    function get(int) public constant returns(Entry);
    function size() public constant returns(int);
}

// Table main type
contract Table {
    // select interface
    function select(string, Condition) public constant returns(Entries);
    //function select( Condition) public constant returns(Entries);
    // insert interface
    function insert(string, Entry) public returns(int);
    // update interface
    function update(string, Entry, Condition) public returns(int);
    // remove interface
    function remove(string, Condition) public returns(int);

    function newEntry() public constant returns(Entry);
    function newCondition() public constant returns(Condition);
}
