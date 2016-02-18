This example script demonstrates the different methods to update existing events in the Netcool object server

```
// first, create an instance of the NetcoolAdapter utility class
// the name of the data source can be passed as a parameter 
// the default is "netcool" defined in the NetcoolDataSource.groovy
def ncAdapter = new NetcoolAdapter()

// if the value of Serial field is not available but Identifier is, 
// get the Serial using getSerial method that takes the Identifier as parameter
// serial = ncAdapter.getSerial("Router1Down");
def serial = 719

// get the event from the server and create an object using the getEvent method
def myEvent = ncAdapter.getEvent(serial)

// event fields are now accessible as myEvent.severity, myEvent.summary, etc.
println("Identifier: " + myEvent.Identifier + " Summary: " + myEvent.Summary)

// set the values for event fields
// setting the value of the property updates the event on the server immediately
myEvent.Severity = 4 
myEvent.Summary = "the new summary 2"
// each statement above is an individual update call to the server

// another way to update the event is to create a map object
// that contains all the fields that should be updated and use the update() method
// this makes a single update call to the server to update all the fields in the map
// and may be preferred if there are multiple fields to update
def updateFields =[:] //this is one way to define a map object in groovy
updateFields.NodeAlias = myEvent.Node + " test"
updateFields.Summary = "Testing: updated using update method"
updateFields.TaskList = 0
myEvent.update(updateFields)

```