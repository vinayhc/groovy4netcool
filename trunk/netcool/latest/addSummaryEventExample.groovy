/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  retrieve events from Netcool server with a filter
*  and create a summary event 
*/ 

// first, create an instance of the NetcoolAdapter class 
// the name of the data source can be passed as a parameter if not the default "netcool"
def ncAdapter = new NetcoolAdapter()

// getEvents takes the where clause of the sql query as a parameter.
// getEvents return a list structure that contains a map object for each event
def myEvents = ncAdapter.getEvents("Summary = 'Machine has gone offline'")

// groovy list operations like size can be used
def numberOfEvents = myEvents.size()
println numberOfEvents

// to create an event, set the fields of the event in a map object
// fields is defined as a map object as below
def fields = [:]
fields.Identifier = "Stats generated for machines offline"
fields.Node = "Stats"
fields.AlertGroup = "Statistic Summary Event"
fields.AlertKey = "Machine offline summary"
fields.Summary = "There are " + numberOfEvents + " machines currently offline"
//now is a utility method that returns unix time (integer)
fields.LastOccurrence = ncAdapter.now() 
fields.Severity = 5
if (numberOfEvents > 0 ) {

// createEvent method takes the map object as a parameter and returns the created event as a map object		
	def summaryEvent = ncAdapter.createEvent(fields)
// the newly created event can be accessed as before
	println summaryEvent.Summary + " " + summaryEvent.LastOccurrence
}

	
