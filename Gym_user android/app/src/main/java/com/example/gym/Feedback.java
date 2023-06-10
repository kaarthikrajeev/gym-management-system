package com.example.gym;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Feedback extends Activity implements JsonResponse {
	EditText e1;
	Button b1;
	ListView l1;
	String message,log_id,physician_id;
	String[] date,messgages,reply,val;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		b1=(Button)findViewById(R.id.smessage);
		e1=(EditText)findViewById(R.id.msg);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		log_id = sh.getString("logid","");
		l1=(ListView)findViewById(R.id.mview);
	
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Feedback.this;
		
		String q="/vfeedback/?log_id="+log_id; 
		jr.execute(q);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				log_id = sh.getString("logid","");
				
				message=e1.getText().toString();
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Feedback.this;
				String q="/sfeedback/?message="+message+"&log_id="+log_id;
				
				q.replace("", "%20");
				jr.execute(q);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try
		{
	
			String method=jo.getString("method");
			
				if(method.equalsIgnoreCase("view")){
					
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						
						
						date=new String[ja.length()];
						messgages= new String[ja.length()];
						reply=new String[ja.length()];
						val= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
						
							date[i]=ja.getJSONObject(i).getString("feedback_date");
							messgages[i]=ja.getJSONObject(i).getString("feedback_description");
							reply[i]=ja.getJSONObject(i).getString("feedback_reply");
							val[i]="\nDate : "+date[i]+"\nFeedback : "+messgages[i]+"\nReply : "+reply[i];
						}

						l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_category,val));


					}
				}
				if(method.equalsIgnoreCase("send")){
					
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Complaint Sended", Toast.LENGTH_LONG).show();
//					
						startActivity(new Intent(getApplicationContext(), Feedback.class));
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
	public void onBackPressed() {
        
		  startActivity(new Intent(getApplicationContext(), Home.class));
        return;
    }   
	}

