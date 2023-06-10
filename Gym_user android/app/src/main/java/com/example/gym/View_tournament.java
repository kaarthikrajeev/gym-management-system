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

public class View_tournament extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    public static String tournament_ids;
    public static String[] tournament_id,name,place,date,time,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tournament);
        l1=(ListView)findViewById(R.id.lvproducts);
        l1.setOnItemClickListener(this);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_tournament.this;
        String q = "/View_tournament";
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("View_tournament")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    tournament_id=new String[ja1.length()];
                    name=new String[ja1.length()];
                    place=new String[ja1.length()];
                    date=new String[ja1.length()];
                    time=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        tournament_id[i]=ja1.getJSONObject(i).getString("tournament_id");
                        name[i]=ja1.getJSONObject(i).getString("name");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        time[i]=ja1.getJSONObject(i).getString("time");
                        value[i]="Tournament Name  :  "+name[i]+"\nPlace : "+place[i]+"\nDate : "+date[i]+"\nTime : "+time[i];

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

            if(method.equalsIgnoreCase("User_send_tournament_request")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " Successfully Send", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_my_request.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Already Requested", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),View_tournament.class));
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
        tournament_ids = tournament_id[arg2];



        final CharSequence[] items = {"Send Request", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(View_tournament.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send Request")) {

                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) View_tournament.this;
                    String q = "/User_send_tournament_request?tournament_ids="+tournament_ids+"&login_id="+sh.getString("logid","");
                    q=q.replace(" ","%20");
                    JR.execute(q);

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
