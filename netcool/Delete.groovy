import groovy.sql.Sql;
class Delete
{
	static void execute(datasourceName, identifier)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def serial;
		def selectSql = "select Serial from alerts.status where Identifier= '" +identifier + "'";
		sql.eachRow(selectSql){serial = it.Serial;};
		if(serial == null){
		    throw new Exception("No event with identifier <" + identifier + "> exists.") 
		}
		def query = "delete from alerts.details where Identifier= '" + identifier+ "'";
		sql.execute(query);
		query = "delete from alerts.status where Serial=" + serial;
		sql.execute(query);
		query = "delete from alerts.journal where Serial=" + serial;
		sql.execute(query);
	}
}