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
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

public class View_products extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    public static String category_id,product_id,quantity,rate;
    public static String[] category_ids,categorys,product_ids,products,quantitys,rates,value,image;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        l1=(ListView)findViewById(R.id.lvproducts);
        l1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_products.this;
        String q = "/View_products?category_id="+User_view_category.category_id;
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("View_products")){
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
                    image=new String[ja1.length()];
                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        category_ids[i]=ja1.getJSONObject(i).getString("category_id");
                        categorys[i]=ja1.getJSONObject(i).getString("category");
                        product_ids[i]=ja1.getJSONObject(i).getString("product_id");
                        products[i]=ja1.getJSONObject(i).getString("product");
                        quantitys[i]=ja1.getJSONObject(i).getString("quantity");
                        rates[i]=ja1.getJSONObject(i).getString("rate");
                        image[i]=ja1.getJSONObject(i).getString("image");
                        value[i]="Category Name  :  "+categorys[i]+"\nProduct : "+products[i]+"\nQuantity : "+quantitys[i]+"\nRate : "+rates[i];

                    }
//                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list,value);
//                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));

                    Cust_products clist=new Cust_products(this,image,products,quantitys,rates);
                    l1.setAdapter(clist);

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


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        category_id = category_ids[arg2];
        product_id=product_ids[arg2];
        quantity=quantitys[arg2];
        rate=rates[arg2];


        final CharSequence[] items = {"Add To Cart","Buy Now", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(View_products.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add To Cart")) {

                    startActivity(new Intent(getApplicationContext(), Add_to_cart.class));
//                        Toast.makeText(getApplicationContext(), "Add To Cart", Toast.LENGTH_LONG).show();
                }

                else if (items[item].equals("Buy Now")) {
//                    startActivity(new Intent(getApplicationContext(), Customer_make_payment.class));
                    startActivity(new Intent(getApplicationContext(), Direct_buy_quantity.class));

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });

        builder.show();

//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);

    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), Home.class);
        startActivity(b);
    }

}
