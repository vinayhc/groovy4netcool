Prerequisite: Java run time should be installed and JAVA\_HOME environment variable should be defined.

1. Install the latest release of Groovy runtime. Windows Installer for Groovy is recommended. http://groovy.codehaus.org/Download

2. set the GROOVY\_HOME environment variable to the directory groovy is installed (i.e. /opt/groovy etc.) and add GROOVY\_HOME/bin directory to PATH.

3. Copy the Sybase JDBC driver binary (jconn2.jar) to GROOVY\_HOME\lib directory. jconn2.jar file can be found OMNIHOME/java/jars directory of your netcool installation, or can be downloaded from the Sybase website.

3. Set the netcool data source parameters in the NetcoolDatasource?.groovy file.

> static def datasourceParams = ["netcool": ["url":"jdbc:sybase:Tds:<NameOrIpOfYourNetcoolServer?>:<OmnibusServerPort?>?LITERAL\_PARAMS=true", "username":"<NameOfTheNetcoolUser?>", "password":"<NetcoolUserPassword?>"](.md) ];

4. You can run the groovy scripts from the command line as

> groovy -cp <directory of the netcool scripts> sample.groovy