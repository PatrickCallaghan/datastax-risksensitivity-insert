package com.heb.finance.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heb.finance.analytics.model.RiskSensitivity;
import com.heb.finance.analytics.utils.PathConverter;

public class RiskSensitivityPathPersister{

	private final RiskSensitivityDao riskSensitivityDao;
	private final PathConverter pathConverter;
	
	public RiskSensitivityPathPersister(RiskSensitivityDao riskSensitivityDao,
			PathConverter pathConverter){
		this.riskSensitivityDao = riskSensitivityDao;
		this.pathConverter = pathConverter;
	}
	

	public void batchInsert(RiskSensitivity riskSensitivity) {
		
		List<String> parts = pathConverter.convertPathToParts(riskSensitivity.getPath());
		
		Map<String, Double> pathValueMap = new HashMap<String, Double>();
		
		for (String part : parts){
			pathValueMap.put(part, riskSensitivity.getValue().doubleValue());
		}
		
		this.riskSensitivityDao.insertBatch(riskSensitivity.getName(), pathValueMap);
		
		pathValueMap.clear();
		pathValueMap = null;
	}
	
}