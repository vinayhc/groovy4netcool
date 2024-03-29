WINDOWS

Prerequisite: Java run time should be installed and JAVA_HOME environment variable should be defined.

1. Install the latest release of Groovy runtime. Windows Installer for Groovy is recommended. http://dist.codehaus.org/groovy/distributions/installers/windows/nsis/groovy1.1-RC1-installer.exe

Notes: - Do not use a directory name with space (like Program Files), as it seems to cause problems.

- Windows installer sets the GROOVY_HOME environment variable and adds the GROOVY_HOME\bin directory to the PATH. If the windows installer is not used, these steps must be done manually.

2. Copy the Sybase JDBC driver binary (jconn2.jar) to GROOVY_HOME\lib directory. jconn2.jar file can be found OMNIHOME\java\jars directory of your netcool installation, or can be downloaded from the Sybase website.

3. Set the netcool data source parameters in the NetcoolDatasource?.groovy file.

    static def datasourceParams = [ "netcool": ["url":"jdbc:sybase:Tds:<NameOrIpOfYourNetcoolServer?>:<OmnibusServerPort?>?LITERAL_PARAMS=true", "username":"<NameOfTheNetcoolUser?>", "password":"<NetcoolUserPassword?>"] ]; 

4. You can run the groovy scripts from the command line as

    groovy -cp <directory of the netcool scripts> sample.groovy 

------------------------------------------------------------------------------------------------
UNIX

Prerequisite: Java run time should be installed and JAVA_HOME environment variable should be defined.

1. Install the latest release of Groovy runtime. Windows Installer for Groovy is recommended. http://groovy.codehaus.org/Download

2. set the GROOVY_HOME environment variable to the directory groovy is installed (i.e. /opt/groovy etc.) and add GROOVY_HOME/bin directory to PATH.

3. Copy the Sybase JDBC driver binary (jconn2.jar) to GROOVY_HOME\lib directory. jconn2.jar file can be found OMNIHOME/java/jars directory of your netcool installation, or can be downloaded from the Sybase website.

3. Set the netcool data source parameters in the NetcoolDatasource??.groovy file.

    static def datasourceParams = [ "netcool": ["url":"jdbc:sybase:Tds:<NameOrIpOfYourNetcoolServer??>:<OmnibusServerPort??>?LITERAL_PARAMS=true", "username":"<NameOfTheNetcoolUser??>", "password":"<NetcoolUserPassword??>"] ]; 

4. You can run the groovy scripts from the command line as

    groovy -cp <directory of the netcool scripts> sample.groovy 

