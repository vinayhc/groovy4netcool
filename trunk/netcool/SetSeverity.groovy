import groovy.sql.Sql;
class SetSeverity
{
	static void execute(datasourceName, identifier, severity, username)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def severityMap = [:];
		def severityInt = -1;
		def query = "select Value, Conversion from alerts.conversions where Colname = 'Severity'";
		sql.eachRow(query){
			def value = it.Value;
			String conversion = it.Conversion.trim();
			severityMap.put(value, conversion);
			if(severity == conversion){
				severityInt = value;
			}	
		}
		if(severityInt == -1){
			throw new Exception(severity + " is not a valid severity conversion");	
		}
		def serial;
		def oldSeverity;
		query = "select Serial, Severity from alerts.status where Identifier= '" +identifier + "'";
		sql.eachRow(query){serial = it.Serial;oldSeverity = it.Severity;};
		if(serial == null){
		    throw new Exception("No event with Identifier <" + identifier + "> exists.") 
		}
		if(oldSeverity == null){
		    throw new Exception("The severity field of the event <" + identifier + "> has not been set.");
		}
		query = "update alerts.status set Severity=" + severityInt + ", Acknowledged=0 where Identifier='" + identifier + "'";
		sql.executeUpdate(query);
		
		def statusText = "Alert prioritized from " + severityMap[oldSeverity]+ " to " + severity + " by " + username;
		int date = (new Date().getTime())/1000;
		def keyField = serial + ":0:" + date;
		query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";
		
		sql.executeUpdate(query);
		
	}
}