package com.example.gym;





import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class customequipments extends ArrayAdapter<String> {
	
	String[] image,name,description;

	    //for custom view photo items
	    private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	    public customequipments(Activity context, String[] image, String[] name, String[] description) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.custom_equipments, image);
	        this.context = context;
	        this.image = image;
	        this.name= name;
	        this.description=description;
	       
	    }
	    public View getView(int position, View convertView, ViewGroup parent) {
            //override getView() method

   LayoutInflater inflater = context.getLayoutInflater();
   View listViewItem = inflater.inflate(R.layout.custom_equipments, null, true);
	//cust_list_view is xml file of layout created in step no.2

   TextView t1= (TextView) listViewItem.findViewById(R.id.validity);
   TextView t2= (TextView) listViewItem.findViewById(R.id.amnt);
  


   ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
   

   t2.setText(description[position]);
   t1.setText(name[position]);
 
   Toast.makeText(getContext(), name[position], Toast.LENGTH_LONG).show();
   
   
   sh=PreferenceManager.getDefaultSharedPreferences(getContext());
   
  String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
  pth = pth.replace("~", "");
   
   Log.d("-------------", pth);
   Picasso.with(context)
           .load(pth)
           .placeholder(R.drawable.ic_launcher)
           .error(R.drawable.ic_launcher).into(im);
   
   return  listViewItem;
}
}
