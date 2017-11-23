package com.prj1.stand.whatfoodtruckapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruckReview;


/**
 * Created by Stand on 11/22/2017.
 */

public class ReviewHolder extends RecyclerView.ViewHolder {
	private TextView title;
	private TextView text;
	
	public ReviewHolder(View itemView){
		super(itemView);
		
		this.title = (TextView) itemView.findViewById(R.id.review_title);
		this.text = (TextView) itemView.findViewById(R.id.review_text);
	}
	
	public void UpdateUI(FoodTruckReview review){
		title.setText(review.getTitle());
		text.setText(review.getText());
	}
}
