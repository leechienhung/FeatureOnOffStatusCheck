package com.featureIntersection;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FeatureConfig {
	
	private static Logger logger = Logger.getLogger(FeatureConfig.class.getName());
	
	public static String FEATURE_KEY_LIST = "featureKeyList";
	
	public static enum FEATURE_NAME {
		ARC_AVC, 
		BONJOUR_GATEWAY, 
		CLIENT_ISOLATION, 
		DHCP_NAT, 
		DYNAMIC_VLAN, 
		GUEST_ACCESS, 
		IPV6, 
		MESH, 
		WE_CHAT, 
		WEB_AUTH, 
		WLAN_ACCESS_VLAN;

		public String value() {
			return this.name().toLowerCase();
		}
	}
	
	public static enum FEATURE_KEY {
		featureName, 
		enabled, 
		intersectionList;
	}
	
	public static JSONObject getFeatureIntersectionConfig() {
		JSONObject featureIntersectionConfig;
	
		JSONParser parser = new JSONParser();	
			Object object = null;
			try {
				object = parser.parse(new FileReader("/home/maxlee/workspace_spring_integration_samples/featureIntersection/src/main/resources/featureIntersection.json"));
			} catch (IOException | ParseException e) {
			};
			featureIntersectionConfig = (JSONObject)object;
			logger.info(featureIntersectionConfig.toString());
		return featureIntersectionConfig;
	}




}
