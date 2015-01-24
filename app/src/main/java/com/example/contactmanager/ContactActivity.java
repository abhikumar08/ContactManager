package com.example.contactmanager;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ContactActivity extends Activity {
	ImageButton ivsrch,ivadd;
	ListView contactList;
	EditText etsrch;
	String temp="";
	String[] Search;
	int ss=4;
	SQLiteDatabase db;
	ArrayList<String> snameList,snumList,shomelist,semaillist,saddrlist;;
	
	ArrayList<String> namelist,numlist,homelist,emaillist,addrlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_contact);
		
		
		ivsrch=(ImageButton)findViewById(R.id.ib_search);
		ivadd=(ImageButton)findViewById(R.id.ib_add);
		etsrch=(EditText)findViewById(R.id.et_search);
		contactList=(ListView)findViewById(R.id.listView1);
		
		
		namelist= new ArrayList<String>();
		numlist=new ArrayList<String>();
		homelist=new ArrayList<String>();
		emaillist=new ArrayList<String>();
		addrlist=new ArrayList<String>();
		
		
		snameList=new ArrayList<String>();
		snumList=new ArrayList<String>();
		shomelist=new ArrayList<String>();
		semaillist=new ArrayList<String>();
		saddrlist=new ArrayList<String>();
		
		db= openOrCreateDatabase("contactsdb", MODE_PRIVATE, null);
		db.execSQL("create table if not exists contacts (cname varchar(20) NOT NULL,cmobile varchar(10) primary key,chome varchar(10),cemail varchar(30),caddress varchar(50));");
		
		Cursor c = db.rawQuery("Select * from contacts ORDER BY cname ASC", null);
		while(c.moveToNext()){
			namelist.add(c.getString(0));
			numlist.add(c.getString(1));
			homelist.add(c.getString(2));
			emaillist.add(c.getString(3));
			addrlist.add(c.getString(3));
			
		}
		
		
		contactList.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, namelist));
	
		ivadd.setBackgroundColor(Color.TRANSPARENT);
		ivsrch.setBackgroundColor(Color.TRANSPARENT);
		ivadd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ContactActivity.this,CreateContact.class);
				startActivity(i);
				finish();
				
			}
		});
		
		
		etsrch.addTextChangedListener(new TextWatcher() {
			
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
								
				//
				return;
			}
			
			@Override
			public void beforeTextChanged
			(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				snameList.clear();
				snumList.clear();
				try{
					Cursor c1=db.rawQuery("select * from contacts WHERE cname LIKE '"+s+"%' ORDER BY cname ",null);
					while(c1.moveToNext()){
						snameList.add(c1.getString(0));
						snumList.add(c1.getString(1));
						shomelist.add(c1.getString(2));
						semaillist.add(c1.getString(3));
						saddrlist.add(c1.getString(4));
					}
				}catch (Exception e) {
					// TODO: handle exception
					
				}
			
				contactList.setAdapter(new CustomAdapter(ContactActivity.this, android.R.layout.simple_list_item_1, snameList));
				
				contactList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent i=new Intent(ContactActivity.this,ViewContact.class);
						String cmobile=snumList.get(arg2);
						i.putExtra("cmobile", cmobile);
						startActivity(i);
					}
				
				
				});
			}
		});		
				
	
		
		//---------------------contactlistitem click listener---------------------------			
				
		contactList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				//  Auto-generated method stub
				Intent i=new Intent(ContactActivity.this,ViewContact.class);
				String cmobile=numlist.get(pos);
				i.putExtra("cmobile", cmobile);
				startActivity(i);
				
			}
		});
	
		
		
	
		//-------listitem long click listener-----------------------------	
		contactList.setOnItemLongClickListener(new OnItemLongClickListener() {
			String[] options={"Edit","Delete"};
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final int p=arg2;
				AlertDialog.Builder dialog = new  AlertDialog.Builder(ContactActivity.this);
				dialog.setTitle("Options");
				dialog.setItems(options, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						String cmobile=numlist.get(p);
						
						
						////------------------Edit the contact-------------
						if(which==0){
							Intent j= new Intent(ContactActivity.this,EditContact.class);
							
							
							j.putExtra("cmobile", cmobile);
							startActivity(j);
						}else{
						////--------------------Deletion of a no.-------------------	
							namelist.clear();
							numlist.clear();
							db.execSQL("delete from contacts WHERE cmobile='"+cmobile+"' ;");
							Cursor d = db.rawQuery("Select * from contacts ORDER BY cname ASC", null);
							while(d.moveToNext()){
								namelist.add(d.getString(0));
								numlist.add(d.getString(1));
							}
							
							
							contactList.setAdapter(new MyAdapter(ContactActivity.this, android.R.layout.simple_list_item_1, namelist));
						}
					}
				});
				dialog.show();
				
				
				return false;
			}
		});
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
	
	
	private class MyAdapter extends ArrayAdapter<String>{

		public MyAdapter(Context context, int Resource, ArrayList<String> list) {
			super(context, Resource, list);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = li.inflate(R.layout.row,parent,false);
			
			TextView tv  = (TextView)row.findViewById(R.id.c_name);
			TextView tvd = (TextView)row.findViewById(R.id.c_no);
			
			tv.setText(namelist.get(position));
			tvd.setText(numlist.get(position));
							
			return row;
		}
	
	}
	
	
	private class CustomAdapter extends ArrayAdapter<String>{

		public CustomAdapter(Context context, int Resource, ArrayList<String> list) {
			super(context, Resource, list);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = li.inflate(R.layout.row,parent,false);
			
			TextView tv  = (TextView)row.findViewById(R.id.c_name);
			TextView tvd = (TextView)row.findViewById(R.id.c_no);
			
			tv.setText(snameList.get(position));
			tvd.setText(snumList.get(position));
							
			return row;
		}
	
	}

}
