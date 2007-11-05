/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Utility class to work with netcool events
*/

import groovy.sql.Sql;
import org.apache.commons.lang.StringUtils;

class ncEvent {
	def ds;
	def eventFields = [:];
	def eventFieldList = [:];
	Object get (String fieldName) {
		return eventFields.get(fieldName);
	}
	void set (String fieldName, Object fieldValue) {
		eventFields.put(fieldName, fieldValue);
		if (eventFieldList[fieldName] == 4)	{
			query = "update alerts.status set ${fieldName}=${fieldValue} where Serial=" + eventFields.Serial; 
		} else if (eventFieldList[fieldName] == 12)	{
			query = "update alerts.status set ${fieldName}='${fieldValue}' where Serial=" + eventFields.Serial; 
		}
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + Serial + "> exists.") 
		}
		
	}
	def ncEvent(netcoolds,s) {
		ds = netcoolds;
		ds.query("select * from alerts.status") { rs ->
			def meta = rs.metaData
			for (i in 0..<meta.columnCount) {
				eventFieldList.put(meta.getColumnLabel(i+1),meta.getColumnType(i+1));
			}
		}		
		def selectSql = "select * from alerts.status where Serial=" + s;
		ds.eachRow(selectSql) { r ->
			eventFieldList.each { f ->
				if (f.value == 4) {
					eventFields.put(f.key, r[f.key]);	
				} else if (f.value == 12) {
					eventFields.put(f.key, r[f.key].trim());	
				}
			}
		}
	}
	def update(updateFields) {
		def query = "update alerts.status set "; 
		updateFields.each { f ->
			//println f.key + ": " + f.value + ": " + eventFieldList[f.key]
			if (f.key != "Serial" && f.key != "Identifier") {
				if (eventFieldList[f.key] == 4) {
					query = query + f.key + "=" + f.value + ", "
				} else if (eventFieldList[f.key] == 12) {
					query = query + f.key + "='" + f.value + "', "
				}
				//println(query);
			}
		}
		query = StringUtils.substringBeforeLast(query,",") + " where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + Serial + "> exists.") 
		}
	}
	def addToJournal(statusText) {
		int date = (new Date().getTime())/1000;
		def keyField = eventFields.Serial + ":0:" + date;
		def query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	eventFields.Serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";		
		ds.executeUpdate(query);		
	}	
}

class NetcoolAdapter {
	def ds;
	def NetcoolAdapter(dsName) {
		ds = NetcoolDatasource.getDatasource(dsName);
	}
	def NetcoolAdapter() {
		ds = NetcoolDatasource.getDatasource("netcool");
	}
	def getEvent(s)
	{
		def netcoolEvent = new ncEvent(ds,s);
	}
	def getEvents(filter) {
		def selectSql = "select * from alerts.status where " + filter;
		List eventList = ds.rows(selectSql)
	}
	def getSerial(eventIdentifier) {
		def Serial;
		def selectSql = "select Serial from alerts.status where Identifier= '" +eventIdentifier + "'";
		ds.eachRow(selectSql){Serial = it.Serial;};
		if(Serial == null){
		    throw new Exception("No event with Identififer <" + eventIdentifier + "> exists.") 
		}
		return Serial;
	}		
	def getUID(userName) {
		def uId;
		def selectSql = "select UID from master.names where Name='" + userName + "'";
		ds.eachRow(selectSql){uId = it.UID;};
		if(uId==null)
		{
			throw new Exception("no such user in netcool database with name " + userName);
		}
		return uId;
	}
	def now() {
		Integer t = (new Date().getTime())/1000
	}
	def createEvent(fields) {
		def status = ds.dataSet('alerts.status');
		status.add(fields);
		def s = getSerial(fields.Identifier);
		def newEvent = getEvent(s)
	}
}

