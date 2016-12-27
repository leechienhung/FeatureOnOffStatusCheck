package com.featureIntersection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import org.easyrules.api.RulesEngine;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.featureIntersection.FeatureConfig.FEATURE_NAME;
import com.featureIntersection.FeatureConfig.FEATURE_KEY;
import com.ruleEngine.FeatureEnableRule;
import com.ruleEngine.RulesEngineFactoryService;

@Service
public class FeatureManagementService {

	Logger logger = Logger.getLogger(FeatureManagementService.class.getName());

	@Autowired
	RulesEngineFactoryService rulesEngineFactoryService;

	//	private static RulesEngine ruleEngine;
	private static boolean init = false;
	private static JSONObject featureConfig;

	public FeatureManagementService() {
		if(!init) {
			//this.ruleEngine = rulesEngineFactoryService.getObject();
			this.featureConfig = FeatureConfig.getFeatureIntersectionConfig();
			init = true;
		}
	}

	public void print(boolean readable) {
		if(featureConfig!=null && featureConfig.containsKey(FeatureConfig.FEATURE_KEY_LIST)){
			JSONObject featureKeyList = (JSONObject) featureConfig.get(FeatureConfig.FEATURE_KEY_LIST);
			for (FEATURE_NAME featureName : FEATURE_NAME.values()) {
				if(readable) {
					System.out.format("%18s, %2s, %8s, %s\n"
							, featureName.value() 
							, featureKeyList.get(featureName.value())
							, this.getFeatureStatus(featureName)
							, this.getFeatureIntersection(featureName));		
				} else {					
					System.out.format("%18s, %2s, %s\n"
							, featureName.value() 
							, featureKeyList.get(featureName)
							, this.getFeature(featureName));

				}
			}
		}
	}

	// TODO
	public boolean setEnabled(FEATURE_NAME featureName, Boolean enable){
		Feature feature = getFeature(featureName);
		if(feature!=null) {
			FeatureEnableRule featureEnableRule = new FeatureEnableRule(feature);
			RulesEngine ruleEngine =rulesEngineFactoryService.getObject();
			ruleEngine.registerRule(featureEnableRule);
			ruleEngine.fireRules();
			return featureEnableRule.shouldBeDisallow();
		}
		return true;
	}


	private LinkedHashMap getFeatureListHashMap() {
		LinkedHashMap hashMap = new LinkedHashMap();
		if(featureConfig!=null) {
			JSONObject featureKeyNameList = (JSONObject) featureConfig.get(FeatureConfig.FEATURE_KEY_LIST);
			if (featureKeyNameList != null) {
				for (Object node : featureKeyNameList.keySet()) {
					String key = node.toString();
					hashMap.put(key, featureKeyNameList.get(key));
				}
			}
		}
		return hashMap;
	}

	private String getOrder(FEATURE_NAME enumFetureName) {
		String featureName = enumFetureName.name().toString();
		LinkedHashMap hashMap = getFeatureListHashMap();
		if(hashMap.containsKey(featureName)) {
			return hashMap.get(featureName).toString();
		}
		return "";
	}

	private Feature getFeature(FEATURE_NAME featureName) {
		String order = getOrder(featureName);
		if(!StringUtils.isEmpty(order)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(featureConfig.get(order).toString(), Feature.class);
			} catch (JsonParseException e) {
				throw new RuntimeException("getFeature JsonMappingException " + e.getMessage() + ":" + featureName);
			} catch (JsonMappingException e) {
				throw new RuntimeException("getFeature JsonMappingException " + e.getMessage() + ":" + featureName);
			} catch (IOException e) {
				throw new RuntimeException("getFeature IOException " + e.getMessage() + ":" + featureName);
			}
		} 
		return null;
	}

	private Boolean getFeatureStatus(FEATURE_NAME featureName) {
		String order = getOrder(featureName);
		if(!StringUtils.isEmpty(order)) {
			JSONObject featureJson = (JSONObject) featureConfig.get(order);
			return (Boolean) featureJson.get(FEATURE_KEY.enabled.name().toString());
		}
		return false;
	}

	private List<String> getFeatureIntersection(FEATURE_NAME featureName) {
		String order = getOrder(featureName);
		if(!StringUtils.isEmpty(order)) {						
			JSONObject featureJson = (JSONObject) featureConfig.get(order);
			return Arrays.asList(featureJson.get(FEATURE_KEY.intersectionList.name().toString()).toString().split(","));
		} 

		return new ArrayList();
	}
}
