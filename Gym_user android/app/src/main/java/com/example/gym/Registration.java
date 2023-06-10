package com.example.gym;





import org.json.JSONArray;
import org.json.JSONObject;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity implements JsonResponse {
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e11,e12;
	Button b1;
	RadioButton r1,r2;
	String fname,lname,age,gender,weight,height,phone,email,username,password;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		e1=(EditText)findViewById(R.id.fname);
		e2=(EditText)findViewById(R.id.lname);
		e3=(EditText)findViewById(R.id.age);
		
		e5=(EditText)findViewById(R.id.weight);
		e6=(EditText)findViewById(R.id.height);
		e7=(EditText)findViewById(R.id.phone);
		e8=(EditText)findViewById(R.id.email);
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		e11=(EditText)findViewById(R.id.username);
		e12=(EditText)findViewById(R.id.password);
		b1=(Button)findViewById(R.id.register);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				age=e3.getText().toString();
				
				weight=e5.getText().toString();
				height=e6.getText().toString();
				phone=e7.getText().toString();
				email=e8.getText().toString();
				if(r1.isChecked()){
					gender="Male";
				}
				if(r2.isChecked()){
					gender="Female";
				}
			
				username=e11.getText().toString();
				password=e12.getText().toString();
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Registration.this;
				String q="/register/?fname="+fname+"&lname="+lname+"&age="+age+"&weight="+weight+"&height="+height+"&username="+username+"&password="+password+"&phone="+phone+"&email="+email+"&gender="+gender;
				Toast.makeText(getApplicationContext(),q,Toast.LENGTH_LONG).show();
				q.replace("", "%20");
				jr.execute(q);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
			String user_id=jo.getString("user_id");
			String data_o=jo.getString("data_o");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			if(status.equalsIgnoreCase("Success"))
			{
//

				Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
//				Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
//				Intent i1=new Intent(getApplicationContext(),Payment.class);
//				i1.putExtra("user_id", user_id);
//				startActivity(i1);
				startActivity(new Intent(getApplicationContext(),Enter_otp.class));
//				Intent i1=new Intent(getApplicationContext(),Enter_otp.class);
//				i1.putExtra("data_o", data_o);
//				startActivity(i1);
				SharedPreferences.Editor ed=sh.edit();
				ed.putString("data_o", data_o);
				ed.putString("user_id", user_id);

				ed.commit();
				
			}
			else if(status.equalsIgnoreCase("duplicate")){


				startActivity(new Intent(getApplicationContext(),Registration.class));
				Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();

			}
//			else if(status.equalsIgnoreCase("duplicate"))
//			{
//				Toast.makeText(getApplicationContext(), "Duplicate", Toast.LENGTH_LONG).show();
//			}
			else
			{
				Toast.makeText(getApplicationContext(), "Not Successfull", Toast.LENGTH_LONG).show();
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
		}
	
		
	

		
	}

}
