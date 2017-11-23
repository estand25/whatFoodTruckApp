package com.prj1.stand.whatfoodtruckapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.holder.ReviewHolder;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruckReview;

import java.util.ArrayList;

/**
 * ReviewAdapter - The retrievals info from the api to pass to the UI
 *
 * Created by Stand on 11/22/2017.
 */

public class ReviewAdapater extends RecyclerView.Adapter<ReviewHolder>{
	private ArrayList<FoodTruckReview> reviews;
	
	public ReviewAdapater(ArrayList<FoodTruckReview> reviews){
		this.reviews = reviews;
	}
	
	@Override
	public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View reviewCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review,parent, false);
		return new ReviewHolder(reviewCard);
	}
	
	@Override
	public int getItemCount() {
		return reviews.size();
	}
	
	@Override
	public void onBindViewHolder(ReviewHolder holder, int position) {
		final FoodTruckReview review = reviews.get(position);
		holder.UpdateUI(review);
		
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			
			}
		});
	}
}
