package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_workouts extends Activity implements JsonResponse{

    ImageView img1;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_workouts);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t2=(TextView) findViewById(R.id.tveqname);
        t3=(TextView) findViewById(R.id.tveqdes);
        t4=(TextView) findViewById(R.id.tvtitle);
        t5=(TextView) findViewById(R.id.tvworkdes);
        t6=(TextView) findViewById(R.id.tvbenefits);
        t7=(TextView) findViewById(R.id.tvdays);
        t8=(TextView) findViewById(R.id.tvmin);

        img1=(ImageView) findViewById(R.id.imageView);


        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse) User_view_workouts.this;
        String q="/User_view_workouts?logid="+sh.getString("logid","");
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

                t2.setText(ja.getJSONObject(0).getString("name"));
                t3.setText(ja.getJSONObject(0).getString("description"));
                t4.setText(ja.getJSONObject(0).getString("title"));
                t5.setText(ja.getJSONObject(0).getString("wdescription"));
                t6.setText(ja.getJSONObject(0).getString("benefits"));
                t7.setText(ja.getJSONObject(0).getString("day"));
                t8.setText(ja.getJSONObject(0).getString("duration"));


                String pth = "http://"+sh.getString("ip", "")+"/"+ja.getJSONObject(0).getString("image");
                pth = pth.replace("~", "");

                Log.d("-------------", pth);
                Picasso.with(getApplicationContext())
                        .load(pth)
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher).into(img1);


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
