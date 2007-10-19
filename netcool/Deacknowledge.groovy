import groovy.sql.Sql;
class Deacknowledge
{
	static void execute(datasourceName, identifier, userName)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def serial;
		def selectSql = "select Serial from alerts.status where Identifier= '" +identifier + "'";
		sql.eachRow(selectSql){serial = it.Serial;};
		if(serial == null){
		    throw new Exception("No event with Identififer <" + identifier + "> exists.") 
		}
		
		def query = "update alerts.status set Acknowledged=0 where Identifier='" + identifier + "'";
		sql.executeUpdate(query);
		
		def statusText = "Alert deacknowledged by " + userName + ".";
		int date = (new Date().getTime())/1000;
		def keyField = serial + ":0:" + date;
		query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";
		
		sql.executeUpdate(query);
	}
}