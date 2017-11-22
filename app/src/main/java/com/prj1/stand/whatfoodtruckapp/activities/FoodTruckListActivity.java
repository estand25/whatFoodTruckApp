package com.prj1.stand.whatfoodtruckapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodTruckListActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_truck_list_activity);
		
		//String url = "http://localhost:3005/api/v1/foodtruck";
		String url = "http://10.0.2.2:3005/api/v1/foodtruck";
		//String url = "http://192.168.1.3.3:3005/api/v1/foodtruck";
		final ArrayList<FoodTruck> foodTruckList = new ArrayList<>();
		
		final JsonArrayRequest getTrucks = new JsonArrayRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				//Log.v("API", "Response list: " + response.toString());
				System.out.println("Response list: " + response.toString());
				try{
					JSONArray foodTrucks = response;
					for(int x = 0; x < foodTrucks.length();x++){
						JSONObject foodTruck = foodTrucks.getJSONObject(x);
						String name = foodTruck.getString("name");
						String id = foodTruck.getString("id");
						String foodType = foodTruck.getString("foodtype");
						Double avgCost = foodTruck.getDouble("avgcost");
						
						JSONObject geometry = foodTruck.getJSONObject("geometry");
						JSONObject coordinates = foodTruck.getJSONObject("coordinates");
						
						Double latitude = coordinates.getDouble("lat");
						Double longitude = coordinates.getDouble("long");
						
						FoodTruck newFoodTruck = new FoodTruck(id,name,foodType,avgCost,latitude,longitude);
						foodTruckList.add(newFoodTruck);
					}
					
				}catch (JSONException je){
					Log.v("JSON","EXEC" + je.getLocalizedMessage());
				}
				
				System.out.println("This is the food truck name - " + foodTruckList.get(0).getName());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.v("API", "Error " + error.getLocalizedMessage());
			}
		});
		
		Volley.newRequestQueue(this).add(getTrucks);
	}
}
