package com.rfm.packagegeneration.dto;

import java.util.List;

import com.rfm.packagegeneration.utility.Pair;

public class ProductPromotionRange {
private WeekDays weekDay;
	
	private List<Pair<String,String>> allowedTimes;

	
	
	public ProductPromotionRange() {
		super();
		
	}

	public ProductPromotionRange(WeekDays weekDay, List<Pair<String, String>> allowedTimes) {
		super();
		this.weekDay = weekDay;
		this.allowedTimes = allowedTimes;
	}

	public WeekDays getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDays weekDay) {
		this.weekDay = weekDay;
	}

	public List<Pair<String, String>> getAllowedTimes() {
		return allowedTimes;
	}

	public void setAllowedTimes(List<Pair<String, String>> allowedTimes) {
		this.allowedTimes = allowedTimes;
	}
	
	
}
