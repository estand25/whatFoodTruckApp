package com.prj1.stand.whatfoodtruckapp.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.prj1.stand.whatfoodtruckapp.activities.FoodTruckListActivity;
import com.prj1.stand.whatfoodtruckapp.activities.ReviewsActivity;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruckReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Data Service - Handles retrieving info. (All Food Trucks and Reviews) from the api
 *
 * Created by Stand on 11/22/2017.
 */

public class DataService {
	private static DataService instance = new DataService();
	
	public static DataService getInstance() {
		return instance;
	}
	
	private DataService() {
	}
	
	// Request all the Food Truck
	public ArrayList<FoodTruck> downloadAllFoodTruck(Context context, final FoodTruckListActivity.TrucksDownloaded listener){
		
		String url = Constants.GET_FOOD_TRUCK;
		final ArrayList<FoodTruck> foodTruckList = new ArrayList<>();
		
		final JsonArrayRequest getTrucks = new JsonArrayRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						
						try{
							JSONArray foodTrucks = response;
							for(int x = 0; x < foodTrucks.length();x++){
								JSONObject foodTruck = foodTrucks.getJSONObject(x);
								String name = foodTruck.getString("name");
								String id = foodTruck.getString("_id");
								String foodType = foodTruck.getString("foodtype");
								Double avgCost = foodTruck.getDouble("avgcost");
								
								JSONObject geometry = foodTruck.getJSONObject("geometry");
								JSONObject coordinates = geometry.getJSONObject("coordinates");
								
								Double latitude = coordinates.getDouble("lat");
								Double longitude = coordinates.getDouble("long");
								
								FoodTruck newFoodTruck = new FoodTruck(id,name,foodType,avgCost,latitude,longitude);
								foodTruckList.add(newFoodTruck);
							}
							
						}catch (JSONException je){
							Log.v("JSON","EXEC" + je.getLocalizedMessage());
						}
						listener.success(true);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.v("API", "Error " + error.getLocalizedMessage());
				listener.success(false);
			}
		});
		
		Volley.newRequestQueue(context).add(getTrucks);
		
		return foodTruckList;
	}
	
	// Request all the Food Truck Review
	public ArrayList<FoodTruckReview> downloadReviews(Context context, FoodTruck foodTruck, final ReviewsActivity.ReviewInterface listener){
		
		String url = Constants.GET_REVIEWS + foodTruck.getId();
		final ArrayList<FoodTruckReview> reviewList = new ArrayList<>();
		
		final JsonArrayRequest getReviews = new JsonArrayRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try{
							JSONArray reviews = response;
							for(int x = 0; x < reviews.length();x++){
								JSONObject review = reviews.getJSONObject(x);
								String id = review.getString("_id");
								String title = review.getString("title");
								String text = review.getString("text");
								
								FoodTruckReview newFoodTruckReview = new FoodTruckReview(id,title,text);
								reviewList.add(newFoodTruckReview);
							}
							
						}catch (JSONException je){
							Log.v("JSON","EXEC" + je.getLocalizedMessage());
						}
						listener.success(true);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.v("API", "Error " + error.getLocalizedMessage());
				listener.success(false);
			}
		});
		
		Volley.newRequestQueue(context).add(getReviews);
		
		return reviewList;
	}
}
