package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

public class User_payment extends Activity implements JsonResponse {

    TextView t1;
    Button b1;
    String amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);
        t1=(TextView)findViewById(R.id.tvamount);
        b1=(Button)findViewById(R.id.btpay);


        t1.setText(View_cart_items.gt);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) User_payment.this;
                String q = "/User_payment?bmaster_ids="+ View_cart_items.bids+"&amount="+ View_cart_items.gt;
                q=q.replace(" ","%20");
                JR.execute(q);

            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                Toast.makeText(getApplicationContext()," Payment Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), View_my_orders.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(), " failed",Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e){
            // TODO: handle exception
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), View_cart_items.class);
        startActivity(b);
    }
}