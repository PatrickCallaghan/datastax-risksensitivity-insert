use dse_demo_analytics; 

create table if not exists risk_sensitivities ( 
	hierarchy_path text, 
	risk_sens_name text, 
	value double, 
	PRIMARY KEY (hierarchy_path, risk_sens_name)
) WITH compaction={'class': 'LeveledCompactionStrategy'};

truncate risk_sensitivities;