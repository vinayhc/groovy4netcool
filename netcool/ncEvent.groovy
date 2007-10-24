/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Utility class that provides the methods to work with netcool events
*/

import groovy.sql.Sql;

class ncEvent {
	// the fields of the Netcool alerts.status table that will be available in the scripts 
	// should be defined here. 
	Integer serial,ownerUID,ownerGID,type,acknowledged,severity,suppressEscl,flash,expireTime;
	String summary,manager,alertKey,node,nodeAlias,location,agent,alertGroup,identifier,customer,service;
	def ds;
	def update() {
		def query = "update alerts.status set Summary='" + summary + 
		"', Manager='" + manager + 
		"', AlertKey='" + alertKey + 
		"', Node='" + node + 
		"', NodeAlias='" + nodeAlias + 
		"', Customer='" + customer + 
		"', Service='" + service + 
		"', URL='" + url + 
		"', Agent='" + agent + 
		"', AlertGroup='" + alertGroup + 
		"', Acknowledged=" + acknowledged + 
		", Type=" + type + 
		", OwnerUID=" + ownerUID + 
		", Flash=" + flash + 
		", ExpireTime=" + expireTime + 
		", OwnerUID=" + ownerUID + 
		", Severity=" + severity + 
		", SuppressEscl=" + suppressEscl + 
		", OwnerGID=" + ownerGID + 
		" where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}	
	def ncEvent(dsName) {
		ds = NetcoolDatasource.getDatasource(dsName);
	}
	def ncEvent() {
		ds = NetcoolDatasource.getDatasource("netcool");
	}
	def getEvent(s)
	{
		def selectSql = "select * from alerts.status where Serial=" + s;
		ds.eachRow(selectSql)
		{
			serial = it.Serial;
			identifier = it.Identifier.trim();
			summary = it.Summary.trim();
			suppressEscl = it.SuppressEscl;
			acknowledged = it.Acknowledged;
			manager = it.Manager.trim();
			alertKey = it.AlertKey.trim();
			node = it.Node.trim();
			agent = it.Agent.trim();
			type = it.Type;
			severity = it.Severity;
			alertGroup = it.AlertGroup.trim();
			ownerUID = it.OwnerUID;
			ownerGID = it.OwnerGID;
		};
	}	
	def getSerial(eventIdentifier) {
		def selectSql = "select Serial from alerts.status where Identifier= '" +eventIdentifier + "'";
		ds.eachRow(selectSql){serial = it.Serial;};
		println("serial "+serial);
		if(serial == null){
		    throw new Exception("No event with Identififer <" + eventIdentifier + "> exists.") 
		}
		return serial;
	}		
	def changeSeverity(sev) {
		def query = "update alerts.status set Acknowledged=0, Severity=" + sev + " where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def addToTaskList() {
		def query = "update alerts.status set TaskList=1 where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def removeFromTaskList() {
		def query = "update alerts.status set TaskList=0 where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def suppressEscl(priority) {	
		def query = "update alerts.status set SuppressEscl=" + priority + " where Serial = " + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def addToJournal(statusText) {
		int date = (new Date().getTime())/1000;
		def keyField = serial + ":0:" + date;
		def query = "insert into alerts.journal( Serial , KeyField, Chrono, Text1) values (" + 
		        	serial + " ,'" + keyField + "' , " + date + " ,  '" + statusText + "')";		
		ds.executeUpdate(query);		
	}
	def acknowledge() {
		def query = "update alerts.status set Acknowledged=1 where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def deacknowledge() {
		def query = "update alerts.status set Acknowledged=0 where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def assignToUser(userID) {
		def query = "update alerts.status set Acknowledged=0, OwnerUID=" + userID + " where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
		}
	}
	def assignToGroup(groupID) {
		def query = "update alerts.status set OwnerUID=65534,Acknowledged=0,OwnerGID=" + groupID + "where Serial=" + serial;
		println(query);
		if(ds.executeUpdate(query) == 0){
		   throw new Exception("No event with Serial <" + serial + "> exists.") 
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

