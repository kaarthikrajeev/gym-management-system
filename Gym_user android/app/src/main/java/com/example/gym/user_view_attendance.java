package com.example.gym;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class user_view_attendance extends Activity implements JsonResponse{

    ListView l1;
    public static String category_id;
    public static String[] attendance_id,date,starting_hour,ending_hour,value;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_attendance);
        l1=(ListView)findViewById(R.id.lvproducts);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) user_view_attendance.this;
        String q = "/user_view_attendance?login_id="+sh.getString("logid","");
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("user_view_attendance")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    attendance_id=new String[ja1.length()];
                    date=new String[ja1.length()];
                    starting_hour=new String[ja1.length()];
                    ending_hour=new String[ja1.length()];

                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        attendance_id[i]=ja1.getJSONObject(i).getString("attendance_id");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        starting_hour[i]=ja1.getJSONObject(i).getString("starting_hour");
                        ending_hour[i]=ja1.getJSONObject(i).getString("ending_hour");

                        value[i]="Date : "+date[i]+"\nTime : "+starting_hour[i]+" - "+ending_hour[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_category,value);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));

//                    CustMedimage clist=new CustMedimage(this,image,medicines,prices,quantitys,pname,ptype);
//                    l1.setAdapter(clist);

                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Data Added Yet!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), Home.class);
        startActivity(b);
    }

}
