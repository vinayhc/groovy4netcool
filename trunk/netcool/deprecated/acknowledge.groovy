/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Acknowledging an event 
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEvent class, 
// the name of the data source can be passed as the parameter
myEvent = new ncEvent()

// if the value of Serial field is not available but Identifier is, 
// get the Serial using the Identifier
// myEvent.Serial = myEvent.getSerial("Router1Down");

// get an instance of an event by passing serial as the parameter
myEvent.getEvent(499)

// set the acknowledged property of the event object
myEvent.Acknowledged = 1

// call the update method to update the event in the netcool server.
myEvent.update()

// write to the journal using addToJournal method
userName = "berkay"
myEvent.addToJournal("Alert acknowledged by " + userName)
