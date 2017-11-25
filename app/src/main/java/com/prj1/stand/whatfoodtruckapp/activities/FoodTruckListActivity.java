package com.prj1.stand.whatfoodtruckapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.adapter.FoodTruckAdapter;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.data.DataService;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;
import com.prj1.stand.whatfoodtruckapp.view.ItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodTruckListActivity extends AppCompatActivity {
	private FoodTruckAdapter adapter;
	private ArrayList<FoodTruck> trucks = new ArrayList<>();
	private static FoodTruckListActivity foodTruckListActivity;
	private FloatingActionButton addTruckBtn;
	public static final String EXTRA_ITEM_TRUCK = "TRUCK";
	SharedPreferences prefs;
	
	public static FoodTruckListActivity getFoodTruckListActivity() {
		return foodTruckListActivity;
	}
	
	public static void setFoodTruckListActivity(FoodTruckListActivity foodTruckListActivity) {
		FoodTruckListActivity.foodTruckListActivity = foodTruckListActivity;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		foodTruckListActivity.setFoodTruckListActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_truck_list_activity);
		
		addTruckBtn = (FloatingActionButton) findViewById(R.id.addTruckBtn);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		addTruckBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAddTruck();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TrucksDownloaded listener = new TrucksDownloaded() {
			@Override
			public void success(Boolean success) {
				if(success){
					setUpRecycler();
				}
			}
		};
		
		setUpRecycler();
		trucks = DataService.getInstance().downloadAllFoodTruck(this,listener);
	}
	
	private void setUpRecycler(){
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyeler_foodtruck);
		recyclerView.setHasFixedSize(true);
		
		adapter = new FoodTruckAdapter(trucks);
		recyclerView.setAdapter(adapter);
		
		LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new ItemDecorator(0,0,0,10));
	}
	
	public interface TrucksDownloaded{
		void success(Boolean success);
	}
	
	public void loadFoodTruckDetailActivity(FoodTruck truck){
		Intent intent = new Intent(FoodTruckListActivity.this, FoodTruckDetailActivity.class);
		intent.putExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK,truck);
		startActivity(intent);
	}
	
	public void loadAddTruck(){
		if(prefs.getBoolean(Constants.IS_LOGGED_IN, false)){
			Intent intent = new Intent(FoodTruckListActivity.this, AddTruckActivity.class);
			startActivity(intent);
		}
		else {
			Intent intent = new Intent (FoodTruckListActivity.this, LoginActivity.class);
			Toast.makeText(getBaseContext(), "Please login to add food truck",Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
	}
}
