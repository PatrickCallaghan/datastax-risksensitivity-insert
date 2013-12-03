## Running the demo 

You will need a java runtime (preferably 7) along with maven 3 to run this demo. You will need to be comfortable installing and starting Cassandra and DSE (hadoop and solr nodes included).

This demo uses quite a lot of memory so it is worth setting the MAVEN_OPTS to run maven with more memory

    export MAVEN_OPTS=-Xmx512M
    
## Schema Setup
Note : This will drop the keyspace and create a new one. All existing data will be lost. 

To specify contact points use the contactPoints command line parameter e.g. '-DcontactPoints=192.168.25.100,192.168.25.101'
The contact points can take mulitple points in the IP,IP,IP (no spaces).

To create the a single node cluster with replication factor of 1 for standard localhost setup, run the following

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetupSingle"

To create the a multi data center cluster with a standard Cassandra, Analytics and Solr set up run the following

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetupMulti" 

To run the insert

    mvn clean compile exec:java -Dexec.mainClass="com.heb.finance.analytics.Main" -DstopSize=1000000
		
The stopSize property allows us to specify the number of inserts we want to run. 

You can also specify if you want to use a normal BoundStatement or a BatchStatement. This will allow you to test the speed difference when writing 5 records in a batch compared to 5 single writes. The default is BATCH and if you wish to specify a normal BoundStatement use -DinsertType=BOUND eg 

    mvn clean compile exec:java -Dexec.mainClass="com.heb.finance.analytics.Main" -DstopSize=1000000 -DinsertType=BOUND
    
    
