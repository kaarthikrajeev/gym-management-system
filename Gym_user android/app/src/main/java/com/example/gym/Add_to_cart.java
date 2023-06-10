package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

public class Add_to_cart extends Activity implements JsonResponse {

    Button b1;
    EditText e1,e2,e3;
    public static String price,qnty,total;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        e1=(EditText)findViewById(R.id.etprice);
        e2=(EditText)findViewById(R.id.etqnty);
        e3=(EditText)findViewById(R.id.ettotal);

        e1.setText(View_products.rate);

        b1=(Button)findViewById(R.id.btcart);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(e2.getText().toString().equalsIgnoreCase("")){
//                    e1.setText("0");
                }
                else if(e2.getText().toString().equalsIgnoreCase(".")){
                    e2.setText("0");
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (e2.getText().toString().length() >= 1) {
//                    Toast.makeText(getApplicationContext(),"Lnth : "+e2.getText().toString().length(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(),"E1 : "+e1.getText().toString(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(),"E2 : "+e2.getText().toString(),Toast.LENGTH_LONG).show();

                    double totalrate = Double.parseDouble(e1.getText().toString()) * (Double.parseDouble(e2.getText().toString()));
                    e3.setText(String.valueOf(totalrate));

                } else
                    e3.setText("0");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                price=e1.getText().toString();
                qnty=e2.getText().toString();
                total=e3.getText().toString();

                if(qnty.equalsIgnoreCase("") )
                {
                    e2.setError("No value for Quantity");
                    e2.setFocusable(true);
                }
                else if(total.equalsIgnoreCase(""))
                {
                    e3.setError("No value for Total");
                    e3.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Add_to_cart.this;
                    String q = "/Add_to_cart?loginid="+sh.getString("logid","")+"&product_id="+View_products.product_id+"&qnty="+qnty+"&amount="+total;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Add_to_cart")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_products.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_products.class));
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
        Intent b=new Intent(getApplicationContext(), View_products.class);
        startActivity(b);
    }

}
