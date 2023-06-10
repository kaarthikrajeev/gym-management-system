package com.example.gym;

import org.json.JSONArray;
import org.json.JSONObject;




import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class View_equipments extends Activity implements JsonResponse,OnItemClickListener {
	ListView l1;
	String[] equipment_id,description,name,image,val;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_equipments);
		l1=(ListView)findViewById(R.id.equips);
		l1.setOnItemClickListener(this);
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse) View_equipments.this;
		String q="/equipments/";
		
		q.replace("", "%20");
		jr.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_equipments, menu);
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
				
				equipment_id= new String[ja.length()];
				description=new String[ja.length()];
				name= new String[ja.length()];
				image=new String[ja.length()];
				
				val= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					equipment_id[i]=ja.getJSONObject(i).getString("equipment_id");
					description[i]=ja.getJSONObject(i).getString("description");
					
					name[i]=ja.getJSONObject(i).getString("name");
					image[i]=ja.getJSONObject(i).getString("image");
					
					
//					val[i]="\nBooking ID : "+image_path[i]+"\nDate : "+validity[i]+"\nUsage Type : "+architect_ids[i]+"\nStatus : "+proposal_ids[i]+"\nStatus : "+amounts[i];
				}

//				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.customtext,val));
				customequipments clist=new customequipments(this,image,name,description);
				 l1.setAdapter(clist);

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
		
		final String eid = equipment_id[arg2];
	
		Intent i1=new Intent(getApplicationContext(),View_usage.class);
		i1.putExtra("equipment_id", eid);
	
		
		startActivity(i1);

	}
	public void onBackPressed() {
        
		  startActivity(new Intent(getApplicationContext(), Home.class));
      return;
  }  

}
