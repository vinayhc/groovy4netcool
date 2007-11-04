/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Setting the severity of an event 
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEvent class 
// the name of the data source can be passed as a parameter if there is more than one.
// def myEvent = new ncEvent("netcool")
def myEvent = new ncEvent()

// if the value of Serial field is not available but Identifier is, 
// get the Serial using the Identifier
// myEvent.Serial = myEvent.getSerial("Router1Down");

// get an instance of an event by passing serial as the parameter
myEvent.getEvent(713)

// event fields are now accessible as myEvent.severity, myEvent.summary, etc.
println("Identifier: " + myEvent.Identifier + " Summary: " + myEvent.Summary)

// set the values for event fields.
myEvent.Severity = 4
myEvent.Summary = "the new summary 2"

// call the update method to update the event in Netcool
myEvent.update()

println("Identifier: " + myEvent.Identifier + " Summary: " + myEvent.Summary)

