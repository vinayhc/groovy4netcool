/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Assigning an event to a user
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEvent class, 
// the name of the data source can be passed as the parameter
myEvent = new ncEvent()

// if the value of Serial field is not available but Identifier is, 
// get the Serial using the Identifier
// myEvent.serial = myEvent.getSerial("Router1Down");

// get an instance of an event by passing serial as the parameter
myEvent.getEvent(499)

// we have the username but we need the UID
userName = "test1User"
UID = myEvent.getUID(userName)

// call the setSeverity method and pass the severity value.
myEvent.ownerUID = UID
myEvent.acknowledged = 0

myEvent.update()

// write to the journal
myEvent.addToJournal("Alert assigned to user " + userName)

