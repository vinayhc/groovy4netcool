/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Utility class that sets up the connection to the netcool server
*/

import java.util.HashMap;
import groovy.sql.Sql;

class NetcoolDatasource
{
	static def datasourceParams = [
		"netcool": ["url":"jdbc:sybase:Tds:192.168.1.44:4100?LITERAL_PARAMS=true", "username":"root", "password":"root"]
		];
	static String databaseDriver = "com.sybase.jdbc2.jdbc.SybDriver";
	static String referenceTable = "catalog.base_tables";
	static HashMap connections = new HashMap(); 
	static void addDatasource(datasourceName, databaseUrl, databaseUsername, databasePassword)
	{
		if(datasourceParams.get(datasourceName) != null){
			throw new Exception("A datasource with name <" + datasourceName + "> already defined");	
		}
		datasourceParams.put(datasourceName, ["url":databaseUrl, "username":databaseUsername, "password":databasePassword]);
	}
	
	static Sql getDatasource(datasourceName){
		def params = datasourceParams.get(datasourceName);
		if(params == null){
			throw new Exception("Datasource with name <" + datasourceName + "> is not defined");	
		}
		def conn = connections.get(datasourceName);
		if(conn == null){
			conn = Sql.newInstance(params.url, params.username, params.password, databaseDriver);
			connections.put(datasourceName, conn);
		}
		else if(!isConnected(conn)){
			conn = Sql.newInstance(params.url, params.username, params.password, databaseDriver);
			connections.put(datasourceName, conn);
		}
		return conn;
	}
	
	static boolean isConnected(conn){
		if(conn == null) return false;
		try 
		{
           conn.firstRow("select * from " + referenceTable);
           return true;
		} 
		catch (Exception e) 
		{
			return false;
		} 	
	}
}