import groovy.sql.Sql;
class TakeOwnership
{
	static void execute(datasourceName, identifier, userName)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def uId;
		def selectSql = "select UID from master.names where Name='" +userName + "'";
		sql.eachRow(selectSql){uId = it.UID;};
		if(uId==null)
		{
			throw new Exception("no such user in netcool database with name " + userName);
		}

		def serial;
		selectSql = "select Serial from alerts.status where Identifier= '" +identifier + "'";
		sql.eachRow(selectSql){serial = it.Serial;};
		if(serial == null){
		    throw new Exception("No event with identifier <" + identifier + "> exists.") 
		}

		def query = "update alerts.status set OwnerUID=" + uId + ", Acknowledged=0 where Identifier='" + identifier + "'";
		sql.executeUpdate(query);
		
		def statusText = "Ownership of alert taken by " + userName + ".";
		int date = (new Date().getTime())/1000;
		def keyField = serial + ":0:" + date;
		query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";
		
		sql.executeUpdate(query);
	}
}