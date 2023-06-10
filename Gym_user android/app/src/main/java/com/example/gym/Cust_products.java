package com.example.gym;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.autofill.Transformation;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Cust_products extends ArrayAdapter<String> {

    String[] image,products,quantitys,rates;

    //for custom view photo items
    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    public Cust_products(Activity context, String[] image, String[] products, String[] quantitys,String[] rates) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.cust_products, image);
        this.context = context;
        this.image = image;
        this.products= products;
        this.quantitys=quantitys;
        this.rates=rates;

    }
    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.cust_products, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1= (TextView) listViewItem.findViewById(R.id.textView);
        TextView t2= (TextView) listViewItem.findViewById(R.id.textView1);
        TextView t3= (TextView) listViewItem.findViewById(R.id.textView2);



        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);


        t1.setText(products[position]);
        t2.setText(quantitys[position]);
        t3.setText(rates[position]);


//        Toast.makeText(getContext(), name[position], Toast.LENGTH_LONG).show();


        sh= PreferenceManager.getDefaultSharedPreferences(getContext());

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


