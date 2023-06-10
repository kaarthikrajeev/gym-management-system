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

public class User_view_employee extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    public static String instructor_ids,login_ids;
    public static String[] instructor_id,login_id,gfirst_name,glast_name,age,gender,phone,email,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_employee);
        l1=(ListView)findViewById(R.id.lvproducts);
        l1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_employee.this;
        String q = "/User_view_employee";
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("User_view_employee")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    instructor_id=new String[ja1.length()];
                    login_id=new String[ja1.length()];
                    gfirst_name=new String[ja1.length()];
                    glast_name=new String[ja1.length()];
                    age=new String[ja1.length()];
                    gender=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        instructor_id[i]=ja1.getJSONObject(i).getString("instructor_id");
                        login_id[i]=ja1.getJSONObject(i).getString("login_id");
                        gfirst_name[i]=ja1.getJSONObject(i).getString("gfirst_name");
                        glast_name[i]=ja1.getJSONObject(i).getString("glast_name");
                        age[i]=ja1.getJSONObject(i).getString("age");
                        gender[i]=ja1.getJSONObject(i).getString("gender");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        value[i]="Instructor Name  :  "+gfirst_name[i]+" "+glast_name[i]+"\nGender : "+gender[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];

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


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        instructor_ids = instructor_id[arg2];
        login_ids=login_id[arg2];



        final CharSequence[] items = {"Add To Rating", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_employee.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add To Rating")) {

                    startActivity(new Intent(getApplicationContext(), User_rate_employee.class));
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
