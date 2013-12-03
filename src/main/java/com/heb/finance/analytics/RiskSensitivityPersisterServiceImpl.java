package com.heb.finance.analytics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.heb.finance.analytics.model.RiskSensitivity;

public class RiskSensitivityPersisterServiceImpl implements RiskSensitivityPersisterService {

	static final Logger LOG = Logger.getLogger("SchemaSetup");

	private RiskSensitivityPathPersister riskSensitivityPathPersister;
	private ExecutorService executor;

	public RiskSensitivityPersisterServiceImpl(RiskSensitivityPathPersister riskSensitivityPathPersister) {
		this.riskSensitivityPathPersister = riskSensitivityPathPersister;		
		this.executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void persist(final RiskSensitivity riskSensitivity) {		
		riskSensitivityPathPersister.insert(riskSensitivity);
	}
}
