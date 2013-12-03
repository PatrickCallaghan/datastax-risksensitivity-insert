To run this demo 

Set up the schema 

		mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetupSingle"
		
To run the insert

		mvn clean compile exec:java -Dexec.mainClass="com.heb.finance.analytics.Main" -DstopSize=100000