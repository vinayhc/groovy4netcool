/* acknowledging an event 
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEventOperations class, 
// the name of the data source is passed as the parameter 
def myEvent = new ncEventOperations("netcool")

// if the value of Serial field is not available, get the Serial using the Identifier
myEvent.serial = myEvent.getSerial("berkay12");

// set the event serial
// myEvent.serial = 499

// call the setSeverity method and pass the severity value.
myEvent.acknowledge()

// write to the journal
def userName = "berkay"
myEvent.addToJournal("Alert acknowledged by " + userName)
