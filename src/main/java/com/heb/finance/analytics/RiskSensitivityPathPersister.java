package com.heb.finance.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.heb.finance.analytics.model.RiskSensitivity;
import com.heb.finance.analytics.utils.PathConverter;

public class RiskSensitivityPathPersister{

	static final Logger LOG = Logger.getLogger("RiskSensitivityPathPersister");
	
	private final RiskSensitivityDao riskSensitivityDao;
	private final PathConverter pathConverter;
	private final InsertType insertType;
	
	enum InsertType {BATCH, BOUND};
	
	public RiskSensitivityPathPersister(RiskSensitivityDao riskSensitivityDao,
			PathConverter pathConverter){
		this(riskSensitivityDao, pathConverter, InsertType.BATCH);
	}
	
	public RiskSensitivityPathPersister(RiskSensitivityDao riskSensitivityDao,
			PathConverter pathConverter, InsertType insertType){
		this.riskSensitivityDao = riskSensitivityDao;
		this.pathConverter = pathConverter;
		this.insertType = insertType;
		
		LOG.info("Using " + insertType.toString() + " for persistence.");
	}

	public void insert(RiskSensitivity riskSensitivity) {
		
		List<String> parts = pathConverter.convertPathToParts(riskSensitivity.getPath());
		
		Map<String, Double> pathValueMap = new HashMap<String, Double>();
		
		for (String part : parts){
			pathValueMap.put(part, riskSensitivity.getValue().doubleValue());
		}
		
		if (this.insertType.equals(InsertType.BOUND)){
			this.riskSensitivityDao.insertBound(riskSensitivity.getName(), pathValueMap);
		}else {
			this.riskSensitivityDao.insertBatch(riskSensitivity.getName(), pathValueMap);
		}
		
		pathValueMap.clear();
		pathValueMap = null;
	}
	
}