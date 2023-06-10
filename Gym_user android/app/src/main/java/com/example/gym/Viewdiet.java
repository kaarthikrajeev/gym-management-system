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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Viewdiet extends Activity implements JsonResponse,OnItemClickListener{
	ListView l1;
	String log_id;
	String[] physician_name,physician_id,diet_details,dates,val;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewdiet);
		l1=(ListView)findViewById(R.id.diets);
		l1.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		log_id = sh.getString("logid","");
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Viewdiet.this;
		String q="/viewdiet/?log_id="+log_id; 
		jr.execute(q);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewdiet, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try{
			
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				
				physician_name= new String[ja.length()];
				diet_details=new String[ja.length()];
				dates= new String[ja.length()];
				physician_id= new String[ja.length()];
				
				val= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					physician_name[i]=ja.getJSONObject(i).getString("physician_name");
					diet_details[i]=ja.getJSONObject(i).getString("diet_details");
					physician_id[i]=ja.getJSONObject(i).getString("physician_id");
					dates[i]=ja.getJSONObject(i).getString("diet_date");
					
					
					val[i]="\nPhysician: "+physician_name[i]+"\nDiet Details : "+diet_details[i]+"\nNumber Of Days: "+dates[i];
				}

				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_category,val));
//				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.customtext,val));
//				customequipments clist=new customequipments(this,image,name,description);
//				 l1.setAdapter(clist);

			}
			else
			{
				Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
		}

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		final String pid = physician_id[arg2];
		
		Intent i1=new Intent(getApplicationContext(),Message.class);
		i1.putExtra("physician_id", pid);
	
		
		startActivity(i1);
		
	}

}
