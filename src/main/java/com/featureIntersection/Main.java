package com.featureIntersection;

import java.util.Random;
import java.util.logging.Logger;

import com.featureIntersection.FeatureConfig.FEATURE_NAME;

public class Main {

	static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		
		// Setup Service
		FeatureManagementService featureManagementService = new FeatureManagementService();
		
		// 1. Get feature list from JSON file
		featureManagementService.print(true);
		
		// 2. Randomly enable features
		FEATURE_NAME.
		featureManagementService.setEnabled(
				FEATURE_NAME.values()[new Random().nextInt(FEATURE_NAME.values().length)]
						, true);
		
	}

}
