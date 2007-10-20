/* Taking an event out of the Task List
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEventOperations class, 
// the name of the data source is passed as the parameter 
def myEvent = new ncEventOperations("netcool")

// if the value of Serial field is not available, get the Serial using the Identifier
// myEvent.serial = myEvent.getSerial("berkay23");

// set the event serial if already known
myEvent.serial = 636;

// call the setSeverity method and pass the severity value.
myEvent.removeFromTaskList()
