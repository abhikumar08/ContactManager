package com.example.contactmanager;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewContact extends Activity implements OnClickListener {
	TextView tvname,tvno,tvhome,tvmail,tvaddr;
	ArrayList<String> namelist,numlist,homelist,maillist,addresslist;
	String cmob,chome;
	SQLiteDatabase db;
	Button edit;
	
	
	ImageButton ibcall1,ibmsg1,ibcall2,ibmsg2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);
		Bundle b= getIntent().getExtras();
		//int position=b.getInt("position");
		cmob=b.getString("cmobile");
		namelist= new ArrayList<String>();
		numlist= new ArrayList<String>();
		homelist=new ArrayList<String>();
		maillist=new ArrayList<String>();
		addresslist=new ArrayList<String>();
		
		db= openOrCreateDatabase("contactsdb", MODE_PRIVATE, null);
		Cursor c = db.rawQuery("Select * from contacts WHERE cmobile='"+cmob+"' ORDER BY cname ASC", null);
		while(c.moveToNext()){
			namelist.add(c.getString(0));
			numlist.add(c.getString(1));
			homelist.add(c.getString(2));
			maillist.add(c.getString(3));
			addresslist.add(c.getString(4));
		}
		tvname=(TextView) findViewById(R.id.Contact_name);
		tvno=(TextView)findViewById(R.id.Contact_primary);
		tvhome=(TextView)findViewById(R.id.tv_home);
		tvmail=(TextView)findViewById(R.id.tv_email);
		tvaddr=(TextView)findViewById(R.id.tv_addr);
		edit=(Button)findViewById(R.id.but_edit);
		tvname.setText(namelist.get(0));
		tvno.setText(numlist.get(0));
		tvhome.setText(homelist.get(0));
		
		chome=homelist.get(0);
		tvmail.setText(maillist.get(0));
		tvaddr.setText(addresslist.get(0));
		
		ibcall1=(ImageButton)findViewById(R.id.ib_call_1);
		ibmsg1=(ImageButton)findViewById(R.id.ib_msg_1);
		ibcall2=(ImageButton)findViewById(R.id.ib_call_2);
		ibmsg2=(ImageButton)findViewById(R.id.ib_msg_2);
		
		
		ibcall1.setOnClickListener(this);
		ibmsg1.setOnClickListener(this);
		ibcall2.setOnClickListener(this);
		ibmsg2.setOnClickListener(this);
		edit.setOnClickListener(this);
		
		ibcall1.setBackgroundColor(Color.TRANSPARENT);
		ibmsg1.setBackgroundColor(Color.TRANSPARENT);
		ibcall2.setBackgroundColor(Color.TRANSPARENT);
		ibmsg2.setBackgroundColor(Color.TRANSPARENT);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
			case R.id.ib_call_1:
				Intent i= new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+cmob));
				startActivity(i);
				break;
				
				
			case R.id.ib_msg_1:
				i=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + cmob));
				startActivity(i);
				break;
				
				
			case R.id.ib_call_2:
				i= new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+chome));
				startActivity(i);
				break;
				
			case R.id.ib_msg_2:
				
				i=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + chome));
				startActivity(i);
				break;
				
			case R.id.but_edit:
				
				Intent j= new Intent(ViewContact.this,EditContact.class);
				
				
				j.putExtra("cmobile", cmob);
				startActivity(j);
				break;
		}
		
	}

	

}
