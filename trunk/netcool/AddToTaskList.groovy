import groovy.sql.Sql;
class AddToTaskList
{
	static void execute(datasourceName, identifier)
	{
		def sql = NetcoolDatasource.getDatasource(datasourceName);
		def query = "update alerts.status set TaskList=1 where Identifier='" + identifier + "'";
		if(sql.executeUpdate(query) == 0){
		   throw new Exception("No event with identifier <" + identifier + "> exists.") 
		}
	}
}