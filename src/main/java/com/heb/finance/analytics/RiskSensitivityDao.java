package com.heb.finance.analytics;

import java.util.Map;
import java.util.logging.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class RiskSensitivityDao {

	static final Logger LOG = Logger.getLogger("RiskSensitivityDao");
	private Cluster cluster;
	private Session session;
	
	private String INSERT = "insert into risk_sensitivities (hierarchy_path, risk_sens_name, value) VALUES (?, ?, ?)";
	private PreparedStatement insertStmt;

	public RiskSensitivityDao(String clusterName, String[] contactPoints, String keyspace) {

		this.cluster = Cluster.builder().addContactPoints(contactPoints).build();
		this.session = cluster.connect(keyspace);
		
		this.insertStmt = this.session.prepare(INSERT);
	}

	public void shutdown(){
		this.cluster.shutdown();
	}

	public void insertBound(String riskSensitivityName, Map<String, Double> pathValueMap) {
		BoundStatement bound = new BoundStatement(insertStmt);
		
		for (String key : pathValueMap.keySet()){			
			session.execute(bound.bind(key, riskSensitivityName, pathValueMap.get(key)));
		}
	}

	public void insertBatch(String riskSensitivityName, Map<String, Double> pathValueMap) {		
		BatchStatement batch = new BatchStatement();
		
		for (String key : pathValueMap.keySet()){			
			batch.add(insertStmt.bind(key, riskSensitivityName, pathValueMap.get(key)));
		}
		session.execute(batch);
	}			
}
