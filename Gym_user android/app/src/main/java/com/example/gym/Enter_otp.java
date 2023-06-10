package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Enter_otp extends Activity {

    Button b1;
    EditText e1;
    String otp;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        Toast.makeText(getApplicationContext(),"OTP : "+sh.getString("data_o",""),Toast.LENGTH_LONG).show();
        b1=(Button) findViewById(R.id.btotp);
        e1=(EditText) findViewById(R.id.etotp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp=e1.getText().toString();
//                Toast.makeText(getApplicationContext(),"otpsss : "+otp,Toast.LENGTH_LONG).show();

                String OTPs=sh.getString("data_o","");


                int oo= Integer.parseInt(otp);
                int bb= Integer.parseInt(OTPs);

                if(oo==bb){
                    startActivity(new Intent(getApplicationContext(),Payments.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}