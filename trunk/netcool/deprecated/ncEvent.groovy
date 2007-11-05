/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Utility class to work with netcool events
*/

import groovy.sql.Sql;
import org.apache.commons.lang.StringUtils;

class ncEvent {
	def ds;
	def eventFields = [:];
	def eventFieldList = [:];
	def ncEvent(dsName) {
		ds = NetcoolDatasource.getDatasource(dsName);
		ds.query("select * from alerts.status") { rs ->
			def meta = rs.metaData
			for (i in 0..<meta.columnCount) {
				eventFieldList.put(meta.getColumnLabel(i+1),meta.getColumnType(i+1));
			}
		}
	}
	def ncEvent() {
		ds = NetcoolDatasource.getDatasource("netcool");
		ds.query("select * from alerts.status") { rs ->
			def meta = rs.metaData
			for (i in 0..<meta.columnCount) {
				eventFieldList.put(meta.getColumnLabel(i+1),meta.getColumnType(i+1));
			}
		}
	}
	Object get (String fieldName) {
		return eventFields.get(fieldName);
	}
	void set (String fieldName, Object fieldValue) {
		println fieldName + fieldValue;
		eventFields.put(fieldName, fieldValue);	
		println eventFields;
	}
	def update() {
		def query = "update alerts.status set "; 
		eventFieldList.each { f ->
			if (f.key != "Serial" && f.key != "Identifier") {
				if (f.value == 4) {
					query = query + f.key + "=" + eventFields[f.key] + ", "
				} else if (f.value == 12) {
					query = query + f.key + "='" + eventFields[f.key] + "', "
				}
			}
		}
		query = StringUtils.substringBeforeLast(query,",") + " where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + Serial + "> exists.") 
		}
	}	
	def getEvent(s)
	{
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
	def getSerial(eventIdentifier) {
		def selectSql = "select Serial from alerts.status where Identifier= '" +eventIdentifier + "'";
		ds.eachRow(selectSql){eventFields.Serial = it.Serial;};
		println("Serial " + eventFields.Serial);
		if(eventFields.Serial == null){
		    throw new Exception("No event with Identififer <" + eventIdentifier + "> exists.") 
		}
		return eventFields.Serial;
	}		
	def changeSeverity(sev) {
		def query = "update alerts.status set Acknowledged=0, Severity=" + sev + " where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def addToTaskList() {
		def query = "update alerts.status set TaskList=1 where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def removeFromTaskList() {
		def query = "update alerts.status set TaskList=0 where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def suppressEscl(priority) {	
		def query = "update alerts.status set SuppressEscl=" + eventFields.priority + " where Serial = " + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def addToJournal(statusText) {
		int date = (new Date().getTime())/1000;
		def keyField = eventFields.Serial + ":0:" + date;
		def query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	eventFields.Serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";		
		ds.executeUpdate(query);		
	}
	def acknowledge() {
		def query = "update alerts.status set Acknowledged=1 where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def deacknowledge() {
		def query = "update alerts.status set Acknowledged=0 where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def assignToUser(userID) {
		def query = "update alerts.status set Acknowledged=0, OwnerUID=" + userID + " where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
	}
	def assignToGroup(groupID) {
		def query = "update alerts.status set OwnerUID=65534,Acknowledged=0,OwnerGID=" + groupID + "where Serial=" + eventFields.Serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + eventFields.Serial + "> exists.") 
		}
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
}

