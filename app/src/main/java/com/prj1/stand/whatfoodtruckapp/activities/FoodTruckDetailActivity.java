package com.prj1.stand.whatfoodtruckapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;

public class FoodTruckDetailActivity extends FragmentActivity implements OnMapReadyCallback {
	
	private GoogleMap mMap;
	private FoodTruck foodTruck;
	private TextView truckName;
	private TextView foodType;
	private TextView avgCost;
	private Button addReviewBtn;
	private Button viewReviewBtn;
	private Button modifyTruckBtn;
	SharedPreferences prefs;
	
	public static final String EXTRA_ITEM_TRUCK = "TRUCK";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view);
		
		truckName = (TextView) findViewById(R.id.detail_food_name);
		foodType = (TextView) findViewById(R.id.detail_food_type);
		avgCost = (TextView) findViewById(R.id.detail_food_cost);
		addReviewBtn = (Button) findViewById(R.id.add_review_btn);
		viewReviewBtn = (Button) findViewById(R.id.view_reviews_btn);
		modifyTruckBtn = (Button) findViewById(R.id.modify_food_truck);
		
		foodTruck = getIntent().getParcelableExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		updateUI();
		
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		
		viewReviewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadReviewActivity(foodTruck);
			}
		});
		
		addReviewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadAddReview();
			}
		});
		
		modifyTruckBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadModifyTruck();
				updateUI();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}
	
	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		
		// Add a marker in Sydney and move the camera
		LatLng foodTruckLocation = new LatLng(foodTruck.getLatitude(),foodTruck.getLongitude());
		mMap.addMarker(new MarkerOptions().position(foodTruckLocation).title(foodTruck.getName()));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(foodTruckLocation,10));
		setUpMap();
	}
	
	private void updateUI(){
		truckName.setText(foodTruck.getName());
		foodType.setText(foodTruck.getFoodType());
		avgCost.setText("$ " + Double.toString(foodTruck.getAvgCost()));
	}
	
	private void setUpMap(){
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setTrafficEnabled(true);
		mMap.setIndoorEnabled(true);
		mMap.setBuildingsEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(true);
	}
	
	public void loadReviewActivity(FoodTruck truck){
		Intent intent = new Intent(FoodTruckDetailActivity.this, ReviewsActivity.class);
		intent.putExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK,truck);
		startActivity(intent);
	}
	
	public void loadAddReview(){
		if(prefs.getBoolean(Constants.IS_LOGGED_IN,false)){
			Intent intent = new Intent(FoodTruckDetailActivity.this,AddReviewActivity.class);
			intent.putExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK,foodTruck);
			startActivity(intent);
		}
		else {
			Intent intent = new Intent(FoodTruckDetailActivity.this, LoginActivity.class);
			Toast.makeText(getBaseContext(),"Please login to leave a review",Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
	}
	
	public void loadModifyTruck(){
		if(prefs.getBoolean(Constants.IS_LOGGED_IN,false)){
			Intent intent = new Intent(FoodTruckDetailActivity.this,ModifyTruckActivity.class);
			intent.putExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK,foodTruck);
			startActivity(intent);
		}
		else {
			Intent intent = new Intent(FoodTruckDetailActivity.this, LoginActivity.class);
			Toast.makeText(getBaseContext(),"Please login to leave a review",Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
	}
}
