package com.prj1.stand.whatfoodtruckapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.constants.Constants;
import com.prj1.stand.whatfoodtruckapp.data.DataService;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;

import java.net.InterfaceAddress;

public class AddReviewActivity extends AppCompatActivity {
	private EditText reviewTitle;
	private EditText reviewText;
	private Button addReviewBtn;
	private Button cancelReviewBtn;
	private FoodTruck foodTruck;
	private String authToken;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_review);
		
		reviewTitle = (EditText) findViewById(R.id.review_title);
		reviewText = (EditText) findViewById(R.id.review_text);
		addReviewBtn = (Button) findViewById(R.id.add_review_btn);
		cancelReviewBtn = (Button) findViewById(R.id.cancel_review_btn);
		foodTruck = getIntent().getParcelableExtra(FoodTruckDetailActivity.EXTRA_ITEM_TRUCK);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		authToken = prefs.getString(Constants.AUTH_TOKEN,"Does not exit");
		
		final AddReviewInterface listener = new AddReviewInterface() {
			@Override
			public void success(Boolean success) {
				finish();
			}
		};
		
		addReviewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// DataService
				final String title = reviewTitle.getText().toString();
				final String text = reviewText.getText().toString();
				DataService.getInstance().addReview(title,text,foodTruck,getBaseContext(),listener,authToken);
			}
		});
		
		cancelReviewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public interface AddReviewInterface{
		void success(Boolean success);
	}
}
