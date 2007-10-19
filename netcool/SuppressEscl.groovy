import groovy.sql.Sql;
class SuppressEscl
{
	static void execute(datasourceName, identifier, priority, username)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def priorityMap = [:];
		def priorityInt = -1;
		def query = "select Value, Conversion from alerts.conversions where Colname = 'SuppressEscl'";
		sql.eachRow(query){
			def value = it.Value;
			String conversion = it.Conversion.trim();
			priorityMap.put(value, conversion);
			if(priority == conversion){
				priorityInt = value;
			}	
		}
		println priorityMap;
		if(priorityInt == -1){
			throw new Exception(priority + " is not a valid suppressEscl conversion");	
		}
		def serial;
		def oldPriority;
		query = "select Serial, SuppressEscl from alerts.status where Identifier= '" +identifier + "'";
		sql.eachRow(query){serial = it.Serial;oldPriority = it.SuppressEscl;};
		if(serial == null){
		    throw new Exception("No event with Identifier <" + identifier + "> exists.") 
		}
		if(oldPriority == null){
		    throw new Exception("The suppressEscl field of the event <" + identifier + "> has not been set.");
		}
		query = "update alerts.status set SuppressEscl=" + priorityInt + " where Identifier='" + identifier + "'";
		sql.executeUpdate(query);
		
		def statusText = "Alert prioritized from " + priorityMap[oldPriority]+ " to " + priority + " by " + username;
		int date = (new Date().getTime())/1000;
		def keyField = serial + ":0:" + date;
		query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";
		
		sql.executeUpdate(query);
		
	}
}