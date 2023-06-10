package com.example.gym;

import org.json.JSONObject;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Payments extends Activity implements JsonResponse {
	EditText t1,e1,e2,e3,e4;
	Button b1;
	
	String amount,log_id,card_no,cvv,exp_date,card_h_name;
	
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments);
		t1=(EditText)findViewById(R.id.cash);

		e1=(EditText)findViewById(R.id.cn);
		e2=(EditText)findViewById(R.id.expd);
		e3=(EditText)findViewById(R.id.cvv);
		e4=(EditText)findViewById(R.id.chn);

		b1=(Button)findViewById(R.id.pay);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amount=t1.getText().toString();

				card_no = e1.getText().toString();
				cvv = e3.getText().toString();
				exp_date = e2.getText().toString();
				card_h_name = e4.getText().toString();

				sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				
				
				log_id = sh.getString("user_id","");

				if (card_no.length() != 16) {
					e1.setError("Valid card number");
					e2.setFocusable(true);
				} else if (cvv.length() != 3) {
					e3.setError("Valid cvv");
					e3.setFocusable(true);
				} else if (exp_date.equalsIgnoreCase("")) {
					e2.setError("Expiry date");
					e2.setFocusable(true);
				}
				else if (card_h_name.equalsIgnoreCase("")) {
					e4.setError("Card Holder Name");
					e4.setFocusable(true);
				}
				else {
					JsonReq jr = new JsonReq();
					jr.json_response = (JsonResponse) Payments.this;
					String q = "/payments/?log_id=" + log_id + "&amount=" + amount;

					q.replace("", "%20");
					jr.execute(q);
				}
				
			}
		});

		e2.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				exp_date = e2.getText().toString();
				if (exp_date.length() == 2 && !exp_date.contains("/")) {
					e2.setText(exp_date + "/");
					e2.setSelection(e2.getText().length());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payments, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			if(status.equalsIgnoreCase("Success"))
			{
				Toast.makeText(getApplicationContext(), "Payed Successfully", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
//			else if(status.equalsIgnoreCase("duplicate"))
//			{
//				Toast.makeText(getApplicationContext(), "Duplicate", Toast.LENGTH_LONG).show();
//			}
			else
			{
				Toast.makeText(getApplicationContext(), "Not Successfull", Toast.LENGTH_LONG).show();
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
		}
	}

}
