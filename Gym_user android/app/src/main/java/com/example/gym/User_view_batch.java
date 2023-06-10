package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_batch extends Activity implements JsonResponse{

    TextView t1,t2,t3;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_batch);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        t1=(TextView) findViewById(R.id.tvbname);
        t2=(TextView) findViewById(R.id.tvtime);
        t3=(TextView) findViewById(R.id.tvinst);

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse) User_view_batch.this;
        String q="/User_view_batch?logid="+sh.getString("logid","");
        q.replace("", "%20");
        jr.execute(q);



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

                t1.setText(ja.getJSONObject(0).getString("batch_name"));
                t2.setText(ja.getJSONObject(0).getString("start_time")+" - "+ja.getJSONObject(0).getString("end_time"));
                t3.setText(ja.getJSONObject(0).getString("gfirst_name")+" "+ja.getJSONObject(0).getString("glast_name"));


            } else {
                Toast.makeText(getApplicationContext(), "Sorry No Data..", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), Home.class));
        return;
    }


}
