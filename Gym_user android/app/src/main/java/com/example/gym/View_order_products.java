package com.example.gym;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_order_products extends Activity implements JsonResponse {

    ListView l1;
    public static String category_id,product_id,quantity,rate;
    public static String[] category_ids,categorys,product_ids,products,quantitys,rates,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_products);
        l1=(ListView)findViewById(R.id.lvmyproducts);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_order_products.this;
        String q = "/View_order_products?bmaster_id="+ View_my_orders.bmaster_id;
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("View_order_products")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    category_ids=new String[ja1.length()];
                    categorys=new String[ja1.length()];
                    product_ids=new String[ja1.length()];
                    products=new String[ja1.length()];
                    quantitys=new String[ja1.length()];
                    rates=new String[ja1.length()];
                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        category_ids[i]=ja1.getJSONObject(i).getString("category_id");
                        categorys[i]=ja1.getJSONObject(i).getString("category");
                        product_ids[i]=ja1.getJSONObject(i).getString("product_id");
                        products[i]=ja1.getJSONObject(i).getString("product");
                        quantitys[i]=ja1.getJSONObject(i).getString("bquantity");
                        rates[i]=ja1.getJSONObject(i).getString("amount");
                        value[i]="Category Name  :  "+categorys[i]+"\nProduct : "+products[i]+"\nQuantity : "+quantitys[i]+"\nRate : "+rates[i];

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

//            if(method.equalsIgnoreCase("Return_medicine")){
//                String status=jo.getString("status");
//                Log.d("pearl",status);
//                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
//                if(status.equalsIgnoreCase("success")){
//
//                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),View_my_medicine_request.class));
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),View_my_medicine_request.class));
//                }
//            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }



    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), View_my_orders.class);
        startActivity(b);
    }

}
