package com.example.gym;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

public class View_cart_items extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    TextView t1;
    Button b1,b2;
    String[] ocids;
    SharedPreferences sh;
    String[] bmaster_id,bchild_id,product_id,category,product,rate,quantity,amount,date,statuss,total,val;
    public static String product_ids, bmaster_ids = "0",gt,quantitys,amounts,bchild_ids,bquantity,bids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_items);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1=(TextView)findViewById(R.id.tvtotal);

        lv1=(ListView)findViewById(R.id.lvplace);
        lv1.setOnItemClickListener(this);

        b1=(Button)findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                startActivity(new Intent(getApplicationContext(),User_payment.class));
            }
        });

        b2=(Button)findViewById(R.id.button);

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) View_cart_items.this;
                String q = "/userremoveallproduct?login_id="+sh.getString("logid","")+"&bmaster_ids="+bmaster_ids;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_cart_items.this;
        String q = "/View_cart_items?login_id="+sh.getString("logid","");
        q=q.replace(" ","%20");
        JR.execute(q);


    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("View_cart_items")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){
                    bids=jo.getString("bid");
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    bmaster_id=new String[ja1.length()];
                    bchild_id=new String[ja1.length()];
                    product_id=new String[ja1.length()];
                    category=new String[ja1.length()];
                    product=new String[ja1.length()];
                    rate=new String[ja1.length()];
                    quantity=new String[ja1.length()];
                    amount=new String[ja1.length()];
                    date=new String[ja1.length()];
                    statuss=new String[ja1.length()];
//                    total=new String[ja1.length()];
                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        bmaster_id[i]=ja1.getJSONObject(i).getString("bmaster_id");
                        bchild_id[i]=ja1.getJSONObject(i).getString("bchild_id");
                        product_id[i]=ja1.getJSONObject(i).getString("product_id");
                        category[i]=ja1.getJSONObject(i).getString("category");
                        product[i]=ja1.getJSONObject(i).getString("product");
                        rate[i]=ja1.getJSONObject(i).getString("rate");
                        quantity[i]=ja1.getJSONObject(i).getString("bqnty");
                        amount[i]=ja1.getJSONObject(i).getString("amount");
                        date[i]=ja1.getJSONObject(i).getString("date");
//                        gt=ja1.getJSONObject(i).getString("total");
                        statuss[i]=ja1.getJSONObject(i).getString("bstatus");
//                        total[i]=ja1.getJSONObject(i).getString("total");
                        gt=ja1.getJSONObject(i).getString("total");

//					Toast.makeText(getApplicationContext(),product_id.toString()+"", Toast.LENGTH_SHORT).show();
                        val[i]="category Name : "+category[i]+"\nproduct : "+product[i]+"\nrate : "+rate[i]+"\nQuantity : "+quantity[i]+"\namount : "+amount[i]+"\ndate : "+date[i]+"\nstatus : "+statuss[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_category,val);
                    lv1.setAdapter(ar);

                    t1.setText(gt);
                    SharedPreferences.Editor e=sh.edit();
                    e.putString("tamount",gt);
                    e.putString("check","ord");
                    e.commit();

                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if(method.equalsIgnoreCase("userremoveproduct")){
                String status=jo.getString("status");
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " Removed Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_cart_items.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_cart_items.class));
                }
            }
            if(method.equalsIgnoreCase("userremoveallproduct")){
                String status=jo.getString("status");
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " Removed Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_products.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        bmaster_ids=bmaster_id[arg2];
        amounts=amount[arg2];
        bchild_ids=bchild_id[arg2];
        product_ids=product_id[arg2];
        bquantity=quantity[arg2];


        final CharSequence[] items = {"Remove Products","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(View_cart_items.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Remove Products"))
                {

                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) View_cart_items.this;
                    String q = "/userremoveproduct?bchild_ids="+bchild_ids+"&amounts="+amounts+"&bmaster_ids="+bmaster_ids;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }


                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();



    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Home.class);
        startActivity(b);
    }




}
