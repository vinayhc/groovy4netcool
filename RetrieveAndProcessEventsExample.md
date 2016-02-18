This example script demonstrates how to retrieve subset of events from the Netcool server using a filter and to process each event

```

// first, create an instance of the NetcoolAdapter utility class
// the name of the data source can be passed as a parameter 
// the default is "netcool" defined in the NetcoolDataSource.groovy
def ncAdapter = new NetcoolAdapter()

// getEvents method takes the where clause of the sql query as a parameter.
// and returns a list structure that contains a map object for each event
def myEvents = ncAdapter.getEvents("Identifier like 'link' AND Severity > 3")

// if any events are found, process them
if (myEvents.size() > 0 ) {
        // in groovy, the list structure can be processed using each 
	myEvents.each {
		// "it" refers to the object processed
		println "${it.Identifier} : ${it.Node} : ${it.Summary} : ${it.Severity}"
		
		// lets update the Summary field value to state that these are critical links
		// get the event as an object first using the getEvent method and pass the Serial
		def event = ncAdapter.getEvent(it.Serial)
		if (event.Severity == 5) {
                        // setting the value of an event field immediately updates the event
			event.Summary = event.Summary + " critical link"
		}
	}
}

```