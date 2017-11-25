package com.prj1.stand.whatfoodtruckapp.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.data.DataService;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;

public class ModifyTruckActivity extends AppCompatActivity {
	private EditText truckName;
	private EditText foodType;
	private EditText avgCost;
	private EditText lat;
	private EditText lon;
	
	private Button modifyTruckBtn;
	private Button modifyCancelTruckBtn;
	
	String authToken;
	SharedPreferences prefs;
	
	FoodTruck foodTruck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_truck);
		
		truckName = (EditText) findViewById(R.id.modify_truck_name);
		foodType = (EditText) findViewById(R.id.modify_truck_foodType);
		avgCost = (EditText) findViewById(R.id.modify_truck_avg_cost);
		lat = (EditText) findViewById(R.id.modify_truck_lati);
		lon = (EditText) findViewById(R.id.modify_truck_long);
		
		modifyTruckBtn = (Button) findViewById(R.id.modify_truck_btn);
		modifyCancelTruckBtn = (Button) findViewById(R.id.modify_truck_cancel_btn);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		authToken = prefs.getString(Constants.AUTH_TOKEN, "Does  not exist");
		
		foodTruck = getIntent().getParcelableExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK);
		
		truckName.setText(foodTruck.getName());
		foodType.setText(foodTruck.getFoodType());
		avgCost.setText(foodTruck.getAvgCost().toString());
		lat.setText(foodTruck.getLatitude().toString());
		lon.setText(foodTruck.getLongitude().toString());
		final String truckId = foodTruck.getId();
		
		final ModifyTruckInterface listener = new ModifyTruckInterface() {
			@Override
			public void success(Boolean success) {
				finish();
			}
		};
		
		modifyTruckBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final FoodTruck currentFoodTruck = new FoodTruck(truckId,
						truckName.getText().toString(),
						foodType.getText().toString(),
						Double.parseDouble(avgCost.getText().toString()),
						Double.parseDouble(lat.getText().toString()),
						Double.parseDouble(lon.getText().toString()));
				
				DataService.getInstance().modifyTruck(currentFoodTruck,getBaseContext(),listener,authToken);
			}
		});
		
		modifyCancelTruckBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public interface ModifyTruckInterface {
		void success(Boolean success);
	}
}
