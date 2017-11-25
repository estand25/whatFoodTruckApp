package com.prj1.stand.whatfoodtruckapp.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prj1.stand.whatfoodtruckapp.activities.AddReviewActivity;
import com.prj1.stand.whatfoodtruckapp.activities.AddTruckActivity;
import com.prj1.stand.whatfoodtruckapp.activities.FoodTruckListActivity;
import com.prj1.stand.whatfoodtruckapp.activities.ReviewsActivity;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruckReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	// Add review to Food Truck
	public void addReview(String title, String text, FoodTruck foodTruck, Context context, final AddReviewActivity.AddReviewInterface listener, String authToken) {
		try {
			String url = Constants.ADD_REVIEW + foodTruck.getId();
			
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("title", title);
			jsonBody.put("text", text);
			jsonBody.put("foodtruck", foodTruck.getId());
			
			final String mRequestBody = jsonBody.toString();
			final String bearer = "Bearer " + authToken;
			
			JsonObjectRequest reviewRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						String message = response.getString("message");
						Log.i("JSON Message", message);
					} catch (JSONException e) {
						Log.v("JSON", "EXC: " + e.getLocalizedMessage());
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("VOLLEY", error.toString());
				}
			}) {
				
				@Override
				public String getBodyContentType() {
					return "application/json; charset=utf-8";
				}
				
				@Override
				public byte[] getBody() {
					try {
						return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
					} catch (UnsupportedEncodingException uee) {
						VolleyLog.wtf("Unsupported Encoding while trying", mRequestBody, "utf-8");
						return null;
					}
				}
				
				@Override
				protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
					if (response.statusCode == 200) {
						listener.success(true);
					}
					return super.parseNetworkResponse(response);
				}
				
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> headers = new HashMap<>();
					headers.put("Authorization", bearer);
					
					return headers;
				}
			};
			
			Volley.newRequestQueue(context).add(reviewRequest);
		} catch (JSONException e){
			e.printStackTrace();
		}
	}
	
	// Add food truck post
	public void addTruck(String name, String foodType, Double avgCost, Double latitude, Double longitude, Context context, final AddTruckActivity.AddTruckInterface listener, String authToken) {
		try {
			String url = Constants.ADD_TRUCK;
			
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("name", name);
			jsonBody.put("foodtype", foodType);
			jsonBody.put("avgcost", avgCost);
			
			JSONObject geometry = new JSONObject();
			JSONObject coordinates = new JSONObject();
			
			coordinates.put("lat",latitude);
			coordinates.put("long",longitude);
			
			geometry.put("coordinates",coordinates);
			
			jsonBody.put("geometry", geometry);
			
			final String mRequestBody = jsonBody.toString();
			final String bearer = "Bearer " + authToken;
			
			JsonObjectRequest addTuckRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						String message = response.getString("message");
						Log.i("JSON Message", message);
					} catch (JSONException e) {
						Log.v("JSON", "EXC: " + e.getLocalizedMessage());
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("VOLLEY", error.toString());
				}
			}) {
				
				@Override
				public String getBodyContentType() {
					return "application/json; charset=utf-8";
				}
				
				@Override
				public byte[] getBody() {
					try {
						return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
					} catch (UnsupportedEncodingException uee) {
						VolleyLog.wtf("Unsupported Encoding while trying", mRequestBody, "utf-8");
						return null;
					}
				}
				
				@Override
				protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
					if (response.statusCode == 200) {
						listener.success(true);
					}
					return super.parseNetworkResponse(response);
				}
				
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> headers = new HashMap<>();
					headers.put("Authorization", bearer);
					
					return headers;
				}
			};
			
			Volley.newRequestQueue(context).add(addTuckRequest);
		} catch (JSONException e){
			e.printStackTrace();
		}
	}
	
}
