package com.example.gym;


import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class View_usage extends Activity implements JsonResponse{
	ListView l1;
	String eqid;
	String[] title,desc,benefits,val;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_usage);
		l1=(ListView)findViewById(R.id.usage);
		eqid = getIntent().getExtras().getString("equipment_id");
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)View_usage.this;
		
		String q="/vequipments/?equipment_id="+eqid; 
		jr.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_usage, menu);
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
				
				title= new String[ja.length()];
				desc=new String[ja.length()];
				benefits= new String[ja.length()];
				
				
				val= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					title[i]=ja.getJSONObject(i).getString("title");
					desc[i]=ja.getJSONObject(i).getString("description");
					
					benefits[i]=ja.getJSONObject(i).getString("benefits");
					
					
					val[i]="\nTitle: "+title[i]+"\nDescription : "+desc[i]+"\nBenefits : "+benefits[i];
				}

				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_category,val));
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

}
