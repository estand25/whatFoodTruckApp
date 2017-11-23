package com.prj1.stand.whatfoodtruckapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.activities.FoodTruckListActivity;
import com.prj1.stand.whatfoodtruckapp.holder.FoodTruckHolder;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;

import java.util.ArrayList;

/**
 * FoodTruckAdapter - The retrievals info from the api to pass to the UI
 *
 * Created by Stand on 11/22/2017.
 */

public class FoodTruckAdapter extends RecyclerView.Adapter<FoodTruckHolder>{
	
	private ArrayList<FoodTruck> trucks;
	
	public FoodTruckAdapter(ArrayList<FoodTruck> trucks){
		this.trucks = trucks;
	}
	
	@Override
	public FoodTruckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View truckCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_foodtruck, parent, false);
		return new FoodTruckHolder(truckCard);
	}
	
	@Override
	public int getItemCount() {
		return trucks.size();
	}
	
	@Override
	public void onBindViewHolder(FoodTruckHolder holder, int position) {
		final FoodTruck truck = trucks.get(position);
		holder.updateUI(truck);
		
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FoodTruckListActivity.getFoodTruckListActivity().loadFoodTruckDetailActivity(truck);
			}
		});
	}
}
