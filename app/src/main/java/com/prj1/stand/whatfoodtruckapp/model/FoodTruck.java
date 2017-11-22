package com.prj1.stand.whatfoodtruckapp.model;

/**
 * Object model for FoodTruck Scheme from foodtruck-api
 *
 * Created by Stand on 7/22/2017.
 */

public class FoodTruck {
	private String id = "";
	
	private String name = "";
	private String foodType = "";
	private Double avgCost = 0.0;
	private Double latitude = 0.0;
	private Double longitube = 0.0;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFoodType() {
		return foodType;
	}
	
	public Double getAvgCost() {
		return avgCost;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public Double getLongitube() {
		return longitube;
	}
	
	public FoodTruck(String id, String name, String foodType, Double avgCost, Double latitude, Double longitube) {
		this.id = id;
		this.name = name;
		this.foodType = foodType;
		this.avgCost = avgCost;
		this.latitude = latitude;
		this.longitube = longitube;
	}
}
