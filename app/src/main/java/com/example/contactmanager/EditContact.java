package com.example.contactmanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditContact extends Activity {

	EditText name,mobile,home,email,address;
	Button save;
	Bundle b;
	SQLiteDatabase db;
	String n,mob,hom,mail,addr,cmob;
	
	ArrayList<String> namelist,numlist,homelist,maillist,addresslist;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
			
		name=(EditText)findViewById(R.id.edname);
		mobile=(EditText)findViewById(R.id.edmobile);
		home=(EditText)findViewById(R.id.edhome);
		email=(EditText)findViewById(R.id.edmail);
		address=(EditText)findViewById(R.id.edaddress);
		save=(Button)findViewById(R.id.edit_contact);
		namelist= new ArrayList<String>();
		numlist= new ArrayList<String>();
		homelist=new ArrayList<String>();
		maillist=new ArrayList<String>();
		addresslist=new ArrayList<String>();
		
		b= getIntent().getExtras();
		db= openOrCreateDatabase("contactsdb", MODE_PRIVATE, null);
		cmob=b.getString("cmobile");
		Cursor c = db.rawQuery("Select * from contacts WHERE cmobile='"+cmob+"' ORDER BY cname ASC", null);
		while(c.moveToNext()){
			namelist.add(c.getString(0));
			numlist.add(c.getString(1));
			homelist.add(c.getString(2));
			maillist.add(c.getString(3));
			addresslist.add(c.getString(4));
		}
		
		name.setText(namelist.get(0));
		mobile.setText(numlist.get(0));
		home.setText(homelist.get(0));
		email.setText(maillist.get(0));
		address.setText(addresslist.get(0));
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				n=name.getText().toString();
				mob=mobile.getText().toString();
				hom=home.getText().toString();
				mail=email.getText().toString();
				addr=address.getText().toString();
				db.execSQL("update contacts set cname='"+n+"' , cmobile='"+mob+"' , chome='"+hom+"' , cemail='"+mail+"' , caddress='"+addr+"' WHERE cmobile='"+b.getString("cmobile")+"'");
			
				startActivity(new Intent(EditContact.this,ContactActivity.class));
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
