package com.example.contactmanager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateContact extends Activity {
	EditText name,mobile,home,email,address;
	Button save;
	
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_contact);
		name=(EditText)findViewById(R.id.etname);
		mobile=(EditText)findViewById(R.id.etmobile);
		home=(EditText)findViewById(R.id.ethome);
		email=(EditText)findViewById(R.id.etmail);
		address=(EditText)findViewById(R.id.etaddress);
		save=(Button)findViewById(R.id.add_contact);

		db= openOrCreateDatabase("contactsdb", MODE_PRIVATE, null);
		db.execSQL("create table if not exists contacts (cname varchar(20),cmobile varchar(10) primary key," +
				"chome varchar(10),cemail varchar(30),caddress varchar(50));");
		
		
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String n,mob,hom,mail,addr;
				n=name.getText().toString();
				mob=mobile.getText().toString();
				hom=home.getText().toString();
				mail=email.getText().toString();
				addr=address.getText().toString();
				try{
					
					
						db.execSQL("insert into contacts values('"+n+"','"+mob+"','"+hom+"','"+mail+"','"+addr+"')");
						Toast.makeText(CreateContact.this, "Contact added succesfully", Toast.LENGTH_SHORT).show();
				
				}catch(Exception e){
					Toast.makeText(CreateContact.this, "Contact already exists", Toast.LENGTH_SHORT).show();
				}
				
				startActivity(new Intent(CreateContact.this,ContactActivity.class));
				finish();
				
			}
		});
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}

