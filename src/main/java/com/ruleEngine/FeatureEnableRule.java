package com.ruleEngine;

import lombok.Data;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.spring.SpringRule;

import com.featureIntersection.Feature;

@SpringRule
@Data
public class FeatureEnableRule {
	private Feature feature;
	private boolean shouldBeDisallow;
	
	public FeatureEnableRule(Feature feature) {
		this.feature = feature;
	}
	
	@Condition
	public boolean when() {
		return true;
	}
	
	@Action
	public void then() {
		shouldBeDisallow = true;
		System.out.println("Feature Enabled.");
	}
	
	public boolean shouldBeDisallow() {
		return shouldBeDisallow;
	}
}
