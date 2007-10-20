/* assigning an event to a user
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEventOperations class, 
// the name of the data source is passed as the parameter 
def myEvent = new ncEventOperations("netcool")

// if the value of Serial field is not available, get the Serial using the Identifier
// myEvent.serial = myEvent.getSerial("berkay12");

// set the event serial
myEvent.serial = 499

// we have the username but we need the UID
def userName = "berkay"
def UID = myEvent.getUID(userName)

// call the setSeverity method and pass the severity value.
myEvent.assignToUser(UID)

// write to the journal
myEvent.addToJournal("Alert assigned to user " + userName)