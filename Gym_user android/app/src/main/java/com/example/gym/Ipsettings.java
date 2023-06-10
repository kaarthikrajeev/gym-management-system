package com.example.gym;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ipsettings extends Activity {
	EditText e1;
	Button b1;
	public static String ip;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipsettings);
		e1=(EditText)findViewById(R.id.ip);
		b1=(Button)findViewById(R.id.butt);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		e1.setText(sh.getString("ip",""));
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ip=e1.getText().toString();
				Editor ed=sh.edit();	
				ed.putString("ip", ip);
				
				ed.commit();
				startActivity(new Intent(getApplicationContext(),Login.class));
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ipsettings, menu);
		return true;
	}

	

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Are you sure you want to exit..?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }
}
