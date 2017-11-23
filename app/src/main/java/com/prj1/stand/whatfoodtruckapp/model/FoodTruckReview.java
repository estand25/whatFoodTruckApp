package com.prj1.stand.whatfoodtruckapp.model;

/**
 * Food Truck Review - Model
 * Created by Stand on 11/22/2017.
 */

public class FoodTruckReview {
	private String id = "";
	private String title = "";
	private String text = "";
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public FoodTruckReview(String id, String title, String text) {
		this.id = id;
		this.title = title;
		this.text = text;
	}
}
