/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  retrieve events from Netcool server with a filter.
*/ 

def ncAdapter = new NetcoolAdapter()

def myEvents = ncAdapter.getEvents("Summary = 'Machine has gone offline'")
if (myEvents.size() > 0 ) {
	myEvents.each { 
		def numberOfCriticalEvents = 0;
		println "${it.Identifier} : ${it.Node} : ${it.Summary} : ${it.Severity}"
		def eventObj = getEvent(it.Serial)
		if (eventObj.Severity == 5) {
			numberOfCriticalEvents++
			
	}
}

