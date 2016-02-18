None of the files in this project are required to use groovy to work with Netcool Omnibus server. Groovy language has inherent support for sql/jdbc.
The objective of this project is to provide some infrastructure so that one does not have to deal with JDBC and SQL, though they are available.

### JDBC Connection ###
[NetcoolDataSource.groovy](http://groovy4netcool.googlecode.com/svn/trunk/netcool/NetcoolDatasource.groovy) file sets JDBC connection to the Netcool server. The parameters for the connection (should be set in this file.
static def datasourceParams = [
> "netcool": ["url":"jdbc:sybase:Tds:**host:port**?LITERAL\_PARAMS=true", "username":"**nameoftheuser**", "password":"**passwordoftheuser**"]
> ];

### Wrapper ###
[ncEvent.groovy](http://groovy4netcool.googlecode.com/svn/trunk/netcool/ncEvent.groovy) script is a wrapper that allows working with Netcool events without using SQL.

### example ###
```
// first, create an instance of the NetcoolAdapter class 
// the name of the data source can be passed as a parameter if not the default "netcool"
// def ncAdapter = new NetcoolAdapter("netcool")
def ncAdapter = new NetcoolAdapter()

// if the value of Serial field is not available but Identifier is, 
// get the Serial using the Identifier
// serial = ncAdapter.getSerial("Router1Down");
def serial = 719

// create an instance of a netcool event object 
def myEvent = ncAdapter.getEvent(serial)


// event fields are now accessible as myEvent.severity, myEvent.summary, etc.
println("Identifier: " + myEvent.identifier + " Summary: " + myEvent.summary)

// set the values for event fields.
// setting the value of the property updates the event on the server immediately
// each statement below is an update call to the server.
myEvent.Severity = 4 
myEvent.Summary = "the new summary 2"

println("Identifier: " + myEvent.Identifier + " Summary: " + myEvent.Summary)
```