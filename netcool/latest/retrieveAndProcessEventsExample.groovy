/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  retrieve events from Netcool server with a filter.
*/ 

// first, create an instance of the NetcoolAdapter class 
// the name of the data source can be passed as a parameter if not the default "netcool"
def ncAdapter = new NetcoolAdapter()

// getEvents takes the where clause of the sql query as a parameter.
// getEvents return a list structure that contains a map object for each event
def myEvents = ncAdapter.getEvents("Identifier like 'link' AND Severity > 3")

// if any events are found process them. 
// in groovy, the list structure can be processed using each 
if (myEvents.size() > 0 ) {
	myEvents.each {
		// "it" refers to the object processed
		println "${it.Identifier} : ${it.Node} : ${it.Summary} : ${it.Severity}"
		
		// lets update the Summary to state that these are critical links
		// get the event as an object first using the getEvent method and pass the Serial
		def event = ncAdapter.getEvent(it.Serial)
		if (event.Severity == 5) {
			event.Summary = event.Summary + " critical link"
		}
	}
}

