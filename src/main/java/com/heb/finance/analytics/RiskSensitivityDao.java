package com.heb.finance.analytics;

import java.util.Map;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class RiskSensitivityDao {

	private static String TABLE = "risk_sensitivity_aggregator";
	private Cluster cluster;
	private Session session;
	
	private String INSERT = "insert into risk_sensitivities (hierarchy_path, risk_sens_name, value) VALUES (?, ?, ?)";
	private PreparedStatement insertStmt;

	public RiskSensitivityDao(String clusterName, String url, String keyspace) {

		this.cluster = Cluster.builder().addContactPoints(url).build();
		this.session = cluster.connect(keyspace);
		
		this.insertStmt = this.session.prepare(INSERT);
	}

	public void shutdown(){
		this.cluster.shutdown();
	}

	public void insertBatch(String riskSensitivityName, Map<String, Double> pathValueMap) {
		BatchStatement batch = new BatchStatement();
		
		for (String key : pathValueMap.keySet()){
			batch.add(this.insertStmt.bind(key, riskSensitivityName, pathValueMap.get(key)));
		}
		session.execute(batch);
	}
}