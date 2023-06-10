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

public class User_view_plan extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    public static String plan_ids,amounts,plan_types;
    public static String[] plan_id,duration,plan_type,amount,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_plan);
        l1=(ListView)findViewById(R.id.lvproducts);
        l1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_plan.this;
        String q = "/User_view_plan";
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("User_view_plan")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    plan_id=new String[ja1.length()];
                    duration=new String[ja1.length()];
                    plan_type=new String[ja1.length()];
                    amount=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {
                        plan_id[i]=ja1.getJSONObject(i).getString("plan_id");
                        duration[i]=ja1.getJSONObject(i).getString("duration");
                        plan_type[i]=ja1.getJSONObject(i).getString("plan_type");
                        amount[i]=ja1.getJSONObject(i).getString("amount");

                        value[i]="Plan Type : "+plan_type[i]+"\n Duration : "+duration[i]+"\nAmount : "+amount[i];

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
        plan_ids = plan_id[arg2];
        plan_types=plan_type[arg2];
        amounts=amount[arg2];




        final CharSequence[] items = {"Subscribe", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_plan.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Subscribe")) {

                    startActivity(new Intent(getApplicationContext(), User_plan_payment.class));
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
