/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Setting the severity of an event 
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to 
*  access to the Netcool server. 
*/ 

// first, create an instance of the NetcoolAdapter class 
// the name of the data source can be passed as a parameter if not the default "netcool"
def ncAdapter = new NetcoolAdapter("netcool")

// if the value of Serial field is not available but Identifier is, 
// get the Serial using the Identifier
// serial = ncAdapter.getSerial("Router1Down");
serial = 719

// create an instance of a netcool event object 
def myEvent = ncAdapter.getEvent(serial)

// event fields are now accessible as myEvent.severity, myEvent.summary, etc.
println("Identifier: " + myEvent.Identifier + " Summary: " + myEvent.Summary)

// set the values for event fields.
// setting the value of the property updates the event on the server immediately
// each statement below is an update call to the server.
myEvent.Severity = 4 
myEvent.Summary = "the new summary 2"

// another way to update the event is to create a map object
// that contains all the fields that should be updated and use the update() method
// this makes a single update call to the server to update all the fields in the map
def updateFields =[:] //this is one way to define a map object in groovy
updateFields.NodeAlias = myEvent.Node + " test"
updateFields.Summary = "Testing: updated using update method"
updateFields.TaskList = 0
myEvent.update(updateFields)

