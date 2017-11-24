package com.prj1.stand.whatfoodtruckapp.constants;

/**
 * Created by Stand on 11/22/2017.
 */

public class Constants {
	//Routes
	public final static String GET_FOOD_TRUCK = "http://10.0.2.2:3005/api/v1/foodtruck";
	public final static String GET_REVIEWS = "http://10.0.2.2:3005/api/v1/foodtruck/Reviews/";
	public final static String REGISTER = "http://10.0.2.2:3005/api/v1/account/register";
	public final static String LOGIN = "http://10.0.2.2:3005/api/v1/account/login";
	public final static String ADD_REVIEW = "http://10.0.2.2:3005/api/v1/foodtruck/reviews/add/";
	
	//Identities
	public final static String AUTH_TOKEN = "AUTHTOKEN";
	public final static String IS_LOGGED_IN = "ISLOGGEDIN";
}
