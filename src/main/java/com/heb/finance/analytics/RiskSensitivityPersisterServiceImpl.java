package com.heb.finance.analytics;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.heb.finance.analytics.model.RiskSensitivity;

public class RiskSensitivityPersisterServiceImpl implements RiskSensitivityPersisterService {

	static final Logger LOG = Logger.getLogger("SchemaSetup");

	private RiskSensitivityPathPersister riskSensitivityPathPersister;

	@SuppressWarnings("serial")
	public RiskSensitivityPersisterServiceImpl(RiskSensitivityPathPersister riskSensitivityPathPersister) {
		this.riskSensitivityPathPersister = riskSensitivityPathPersister;
	}

	@Override
	public void persist(final RiskSensitivity riskSensitivity) {
		try {
			riskSensitivityPathPersister.batchInsert(riskSensitivity);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			System.exit(1);
		}
	}
}
