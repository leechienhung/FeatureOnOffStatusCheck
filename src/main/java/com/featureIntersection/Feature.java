package com.featureIntersection;

import lombok.Data;

@Data
public class Feature {
	
	public String featureName;
	public boolean enabled;
	public int[] intersectionList;
}
