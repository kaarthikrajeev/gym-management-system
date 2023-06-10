package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Forgot_password extends Activity implements  JsonResponse{

    EditText e1, e2, e3;
    Button b1;
    String uname, email, phone;
    public static String logid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        e1 = (EditText) findViewById(R.id.etuname);
        e2 = (EditText) findViewById(R.id.etemail);
        e3 = (EditText) findViewById(R.id.etphone);

        b1 = (Button) findViewById(R.id.btconfirm);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = e1.getText().toString();
                email = e2.getText().toString();
                phone = e3.getText().toString();

                if (uname.equalsIgnoreCase("")) {

                    e1.setError("enter username");
                    e1.setFocusable(true);

                } else if (email.equalsIgnoreCase("")) {

                    e2.setError("enter Email");
                    e2.setFocusable(true);

                } else if (phone.equalsIgnoreCase("")) {

                    e3.setError("enter Phone Number");
                    e3.setFocusable(true);

                } else {
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) Forgot_password.this;
                    String q = "/Driver_forgot_password?uname=" + uname + "&email=" + email + "&phone=" + phone;
                    jr.execute(q);


                }
            }
        });
    }

    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                logid = ja.getJSONObject(0).getString("loginid");

//                Toast.makeText(getApplicationContext(), "success ", Toast.LENGTH_LONG).show();
                Intent next = new Intent(getApplicationContext(), Set_new_password.class);
                startActivity(next);
            } else {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "exp : " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}