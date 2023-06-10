package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_rate_employee extends Activity implements JsonResponse{
    String provider_id;
    SharedPreferences sh;
    Float rate;
    EditText e1;
    Button b1;
    RatingBar r1;
    ListView l1;
    String desca,log_id;
    String[] review_date,review_desc,rating,val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate_employee);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        log_id = sh.getString("logid","");

        e1=(EditText)findViewById(R.id.etreview);
        r1=(RatingBar)findViewById(R.id.rtrate);
        b1=(Button)findViewById(R.id.btnrate);
        l1=(ListView)findViewById(R.id.rev);

        provider_id = User_view_employee.instructor_ids;
        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)User_rate_employee.this;
        String q="/User_view_rate_employee/?provider_id="+provider_id+"&log_id="+log_id;
        jr.execute(q);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                rate=r1.getRating();
                desca=e1.getText().toString();
                if(desca.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e1.setError("");
                    e1.setFocusable(true);
                }


                else {
                    provider_id = User_view_employee.instructor_ids;

                    JsonReq jr= new JsonReq();
                    jr.json_response=(JsonResponse)User_rate_employee.this;
                    String q="/User_rate_employee/?provider_id="+provider_id+"&rate="+rate+"&desca="+desca+"&log_id="+log_id;
                    jr.execute(q);

                }
            }
        });


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


                    review_date=new String[ja.length()];
                    review_desc= new String[ja.length()];
                    rating= new String[ja.length()];



                    val= new String[ja.length()];


                    for(int i=0;i<ja.length();i++)
                    {

                        review_date[i]=ja.getJSONObject(i).getString("review_date");
                        review_desc[i]=ja.getJSONObject(i).getString("review_desc");
                        rating[i]=ja.getJSONObject(i).getString("rating");


                        val[i]="\nDate : "+review_date[i]+"\nDescription : "+review_desc[i]+"\nRating : "+rating[i];
                    }

                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_category,val));


                }


            }
            if(method.equalsIgnoreCase("send")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    Toast.makeText(getApplicationContext(), "Reviewed Successfully", Toast.LENGTH_LONG).show();
//
                    startActivity(new Intent(getApplicationContext(), Home.class));
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

}
