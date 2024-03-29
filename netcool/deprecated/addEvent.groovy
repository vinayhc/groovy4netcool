/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Adding an event to the Netcool server
*  The Serial or Identifier of the event is required
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

def ds = NetcoolDatasource.getDatasource("netcool")
def ncEvent = ds.dataSet('alerts.status')
Integer t = (new Date().getTime())/1000
ncEvent.add (
	Identifier: "berkay434", 
	Node: "berkay", 
	Severity: 5, 
	Summary: "created by berkay", 
	LastOccurrence: t, 
	FirstOccurrence: t
)
