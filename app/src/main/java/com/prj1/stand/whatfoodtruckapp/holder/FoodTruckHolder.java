package com.prj1.stand.whatfoodtruckapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;


/**
 * FoodTruckHolder - Handlers the populating of the UI
 *
 * Created by Stand on 11/22/2017.
 */

public class FoodTruckHolder extends RecyclerView.ViewHolder {
	
	private TextView truckName;
	private TextView foodtype;
	private TextView avgCost;
	
	public FoodTruckHolder(View itemView){
		super(itemView);
		
		this.truckName = (TextView) itemView.findViewById(R.id.food_truck_name);
		this.foodtype = (TextView) itemView.findViewById(R.id.food_truck_type);
		this.avgCost = (TextView) itemView.findViewById(R.id.food_truck_cost);
	}
	
	public void updateUI(FoodTruck foodTruck){
		truckName.setText(foodTruck.getName());
		foodtype.setText(foodTruck.getFoodType());
		avgCost.setText("$ " + foodTruck.getAvgCost().toString());
	}
	
}
