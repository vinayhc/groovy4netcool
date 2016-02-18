This example demonstrates how to create an event in the Netcool object server. The script retrieves the events that match a filter, and based on the number of events that match the filter creates a new event.

```
/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  retrieve events from Netcool server with a filter
*  and create a summary event 
*/ 

// first, create an instance of the NetcoolAdapter utility class
// the name of the data source can be passed as a parameter 
// the default is "netcool" defined in the NetcoolDataSource.groovy
def ncAdapter = new NetcoolAdapter()

// getEvents method takes the where clause of the sql query as a parameter.
// and returns a list structure that contains a map object for each event
def myEvents = ncAdapter.getEvents("Summary = 'Machine has gone offline'")

// myEvents is a list, therefore groovy list methods like size() can be used
def numberOfEvents = myEvents.size()
println "number of events: " + numberOfEvents

// to create an event, set the fields of the event in a map object
// below, fields is defined as a map object
def fields = [:]
// a map can be populated easily using the dot subscript
// we could also do fields['Identifier'] = "xxx"
fields.Identifier = "Stats generated for machines offline"
fields.Node = "Stats"
fields.AlertGroup = "Statistic Summary Event"
fields.AlertKey = "Machine offline summary"
fields.Summary = "There are " + numberOfEvents + " machines currently offline"
//now is a utility method that returns unix time (integer)
fields.LastOccurrence = ncAdapter.now() 
fields.Severity = 5
if (numberOfEvents > 0 ) {

// createEvent method takes the map object as a parameter 
// and returns the created event as a map object		
	def summaryEvent = ncAdapter.createEvent(fields)
// the newly created event can be accessed as an event object
	println summaryEvent.Summary + " " + summaryEvent.LastOccurrence
}

```