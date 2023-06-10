package com.example.gym;




import org.json.JSONArray;
import org.json.JSONObject;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Payment extends Activity implements JsonResponse, OnItemSelectedListener {
	EditText t1;
	Button b1;
	Spinner s1;
	String amount,user_id,batch_id,batch_name;
	String[] batches,batches_id;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse) Payment.this;
		String q="/viewpayment/";
		
		q.replace("", "%20");
		jr.execute(q);
		t1=(EditText)findViewById(R.id.cash);
		b1=(Button)findViewById(R.id.pay);
		s1=(Spinner)findViewById(R.id.batch);
		s1.setOnItemSelectedListener(this);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amount=t1.getText().toString();
				user_id = getIntent().getExtras().getString("user_id");
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Payment.this;
				String q="/payment/?user_id="+user_id+"&amount="+amount+"&batch_id="+batch_id;
				
				q.replace("", "%20");
				jr.execute(q);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try
		{
	
			String method=jo.getString("method");
			
				if(method.equalsIgnoreCase("batch")){
					
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						
						
						batches_id=new String[ja.length()];
						batches= new String[ja.length()];
					
						
						
					
						
						
						for(int i=0;i<ja.length();i++)
						{
						
							batches_id[i]=ja.getJSONObject(i).getString("batch_id");
							batches[i]=ja.getJSONObject(i).getString("batch_name");
							
							
							
						}

						s1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,batches));


					}
				}
				if(method.equalsIgnoreCase("paid")){
					
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_LONG).show();
//					
						startActivity(new Intent(getApplicationContext(), Login.class));
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Failed....", Toast.LENGTH_LONG).show();
					}
				}
				
		}
				catch(Exception e){
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
				}
		


		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		batch_id=batches_id[arg2];
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	  public void onBackPressed() {
        
		  startActivity(new Intent(getApplicationContext(), Login.class));
          return;
      }   

}
