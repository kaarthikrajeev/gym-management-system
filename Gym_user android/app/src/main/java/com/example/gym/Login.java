package com.example.gym;




import org.json.JSONArray;
import org.json.JSONObject;





import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse{
	EditText e1,e2;
	Button b1;
	String username,password;
	TextView t1,t2;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 e1=(EditText)findViewById(R.id.uname);
		 e2=(EditText)findViewById(R.id.pass);
		 b1=(Button)findViewById(R.id.login);
		 sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 t1=(TextView)findViewById(R.id.textView2);
		 t2=(TextView)findViewById(R.id.textView3);
		 t1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(), Registration.class));
				
			}
		});

		t2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), Forgot_password.class));

			}
		});

		 b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					username=e1.getText().toString();
					password=e2.getText().toString();
					
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse) Login.this;
					String q="/login/?username="+username+"&password="+password;
					
					q.replace("", "%20");
					jr.execute(q);
					
					Toast.makeText(getApplicationContext(), "Username"+username+"\nPassword"+password, Toast.LENGTH_SHORT).show();
					
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
            String status = jo.getString("status");
            Log.d("result", status);
            Toast.makeText(getApplicationContext(), status,Toast.LENGTH_LONG).show();
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                String logid = ja.getJSONObject(0).getString("login_id");
                String type = ja.getJSONObject(0).getString("user_type");
                Toast.makeText(getApplicationContext(), logid, Toast.LENGTH_LONG).show();
                Editor ed=sh.edit();	
				ed.putString("logid", logid);
				
				ed.commit();
               
                //startService(new Intent(getApplicationContext(), LocationService.class));

                if (type.equals("user"))
                {
                	Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));
                //startService(new Intent(getApplicationContext(),Notiservise.class));
                }
//                else if (type.equals("merchant"))
//                    startActivity(new Intent(getApplicationContext(), MerchantHome.class));
                else
                {
                    Toast.makeText(getApplicationContext(), "Login failed..!!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
		
	}
	public void onBackPressed() {
        
		  startActivity(new Intent(getApplicationContext(), Ipsettings.class));
    return;
}  


}
