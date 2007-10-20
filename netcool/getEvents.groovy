/* retrieving events from netcool server
*  The name of the data source refers the connection defined to access to the Netcool server. 
*/ 

// first, create an instance of the ncEventOperations class, 
// the name of the data source is passed as the parameter 
def nc = new ncEventOperations("netcool")

// define the query 
def selectSql = "select * from alerts.status where Identifier like 'berkay' and Severity = 5"

// walk through each of the records in the result set with eachRow.
nc.ds.eachRow(selectSql)
{
	println it.Identifier+it.Summary+it.Severity;
};
