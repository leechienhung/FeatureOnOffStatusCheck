package featureIntersection;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.featureIntersection.Feature;
import com.featureIntersection.FeatureConfig;
import com.featureIntersection.FeatureManagementService;
import com.featureIntersection.FeatureConfig.FEATURE_NAME;

public class TestFeature {

	FeatureManagementService featureIntersectionService = new FeatureManagementService();

	@Test
	public void verifyGetFeature() {
//		featureIntersectionService = new FeatureManagementService();
//		
//		FeatureConfig featureIntersectionConfig = new FeatureConfig();
//		JSONObject featureIntersectionConfigJson = featureIntersectionConfig.getFeatureIntersectionConfig();
//		String keyOrder = featureIntersectionConfig.getFeatureOrder(FEATURE_NAME.ARC_AVC.value());
//		JSONObject featureJson = (JSONObject) featureIntersectionConfigJson.get(keyOrder);
//		Feature feature = featureIntersectionService.getFeature(FEATURE_NAME.ARC_AVC.value());
//		assertEquals(featureJson.get("featureName").toString(), feature.featureName);
	}
}
