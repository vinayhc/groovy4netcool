/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Retrieving and processing events from the Netcool server
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

def nc = new ncEvent()

// define the query 
def selectSql = "select * from alerts.status where Identifier like 'berkay' and Severity = 3"

// walk through each of the records in the result set with eachRow.
nc.ds.eachRow(selectSql)
{
	println it.Identifier.trim() + " - " + it.Summary.trim() + " - " + it.Severity;
};
