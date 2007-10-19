def sql = NetcoolDatasource.getDatasource("netcool");
node = "link";
def selectSql = "select * from alerts.status where Node like '" + node + "'";
println(selectSql);
sql.eachRow(selectSql)
{
	println(it.Identifier+" "+it.Summary);
};
