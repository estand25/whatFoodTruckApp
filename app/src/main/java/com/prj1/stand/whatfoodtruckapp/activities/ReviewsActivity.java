package com.prj1.stand.whatfoodtruckapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.widget.Toast;

import com.prj1.stand.whatfoodtruckapp.R;
import com.prj1.stand.whatfoodtruckapp.adapter.FoodTruckAdapter;
import com.prj1.stand.whatfoodtruckapp.adapter.ReviewAdapater;
import com.prj1.stand.whatfoodtruckapp.data.DataService;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruck;
import com.prj1.stand.whatfoodtruckapp.model.FoodTruckReview;
import com.prj1.stand.whatfoodtruckapp.view.ItemDecorator;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
	
	private FoodTruck foodTruck;
	private ArrayList<FoodTruckReview> reviews = new ArrayList<>();
	private ReviewAdapater adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reviews);
		
		foodTruck = getIntent().getParcelableExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK);
		System.out.println(foodTruck.getName());
		
		ReviewInterface listener = new ReviewInterface() {
			@Override
			public void success(Boolean success) {
				if(success){
					setUpRecycler();
					if(reviews.size() == 0){
						Toast.makeText(getBaseContext(), "No reviews for this food", Toast.LENGTH_SHORT ).show();
					}
				}
			}
		};
		
		reviews = DataService.getInstance().downloadReviews(this, foodTruck, listener);
	}
	
	public interface ReviewInterface {
		void success(Boolean success);
	}
	
	private void setUpRecycler(){
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_reviews);
		recyclerView.setHasFixedSize(true);
		
		adapter = new ReviewAdapater(reviews);
		recyclerView.setAdapter(adapter);
		
		LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new ItemDecorator(0,0,0,10));
	}
}
