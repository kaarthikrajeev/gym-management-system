package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Customer_make_payment extends Activity implements JsonResponse {
    EditText e1,e2;
    Button b1;
    SharedPreferences sh;
    String amounts,dates;
    public static String log_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_make_payment);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("logid","");

        e1=(EditText) findViewById(R.id.amt);
        e1.setText(sh.getString("total",""));
//        e2=(EditText) findViewById(R.id.amt);
        b1=(Button) findViewById(R.id.btn2);

//        e1.setText(View_products.rate);
        e1.setEnabled(false);
//        e2.setText(User_view_request.dates);
//        e2.setEnabled(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amounts=e1.getText().toString();
                if(amounts.equalsIgnoreCase("")){
                    e1.setError("Amount Required");
                    e1.setFocusable(true);
                }
                else {

//                dates=e2.getText().toString();
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) Customer_make_payment.this;
                    String q = "/Customer_make_payment?&amounts=" + sh.getString("total","")+ "&log_id=" + log_id + "&product_id=" + View_products.product_id+"&qnty="+sh.getString("qnty","");

                    q.replace("", "%20");
                    jr.execute(q);
                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){


                Toast.makeText(getApplicationContext(), "PAYMENT SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
//            else if(status.equalsIgnoreCase("duplicate"))
//            {
//                startActivity(new Intent(getApplicationContext(),User_home.class));
//                Toast.makeText(getApplicationContext(), " already Buy...", Toast.LENGTH_LONG).show();
//            }
            else
            {
                startActivity(new Intent(getApplicationContext(),Customer_make_payment.class));
                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Home.class);
        startActivity(b);

    }
}