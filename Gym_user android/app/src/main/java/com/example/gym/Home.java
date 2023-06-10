package com.example.gym;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		b1=(Button)findViewById(R.id.equip);
		b2=(Button)findViewById(R.id.sub);
		b3=(Button)findViewById(R.id.diet);
		b4=(Button)findViewById(R.id.message);    
		b5=(Button)findViewById(R.id.feedback);
		b6=(Button)findViewById(R.id.logout);
		b10=(Button)findViewById(R.id.btinst);
		b7=(Button)findViewById(R.id.category);
		b8=(Button)findViewById(R.id.cart);
		b9=(Button)findViewById(R.id.myorders);
		b11=(Button)findViewById(R.id.bttou);
		b12=(Button)findViewById(R.id.request);
		b13=(Button)findViewById(R.id.btattendance);
		b14=(Button)findViewById(R.id.batch);
		b15=(Button)findViewById(R.id.workout);


		b15.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_workouts.class));
			}
		});

		b14.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_batch.class));
			}
		});

		b13.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),user_view_attendance.class));
			}
		});


		b12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),View_my_request.class));
			}
		});


		b10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_employee.class));
			}
		});

		b11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),View_tournament.class));
			}
		});

		b7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_category.class));
			}
		});

		b8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),View_cart_items.class));
			}
		});


		b9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),View_my_orders.class));
			}
		});
		
		b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Feedback.class));
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Complaint.class));
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Viewdiet.class));
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),User_view_plan.class));
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),View_equipments.class));
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	public void onBackPressed() {
        
		  startActivity(new Intent(getApplicationContext(), Home.class));
      return;
  }  

}
