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

public class User_view_category extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    public static String category_id;
    public static String[] category_ids,categorys,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_category);
        l1=(ListView)findViewById(R.id.lvproducts);
        l1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_category.this;
        String q = "/User_view_category";
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("User_view_category")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    category_ids=new String[ja1.length()];
                    categorys=new String[ja1.length()];

                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        category_ids[i]=ja1.getJSONObject(i).getString("category_id");
                        categorys[i]=ja1.getJSONObject(i).getString("category");

                        value[i]=categorys[i];

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


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        category_id = category_ids[arg2];



        final CharSequence[] items = {"View Products", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_category.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Products")) {

                    startActivity(new Intent(getApplicationContext(), View_products.class));
//                        Toast.makeText(getApplicationContext(), "Add To Cart", Toast.LENGTH_LONG).show();
                } else if (items[item].equals("Cancel")) {
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
