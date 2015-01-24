package com.example.contactmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText etuser,etpass,etgetpass;
	Button bsetpass,blogin;
	String uName,uPass;
	
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("user detail", MODE_PRIVATE);
		
		if(!sp.getBoolean("created", false)){
			setContentView(R.layout.activity_main);	
			etuser=(EditText)findViewById(R.id.etuname);
			etpass=(EditText)findViewById(R.id.etupass);
			bsetpass=(Button)findViewById(R.id.set_pass);
			
			bsetpass.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					SharedPreferences.Editor ed= sp.edit();
					uName=etuser.getText().toString();
					uPass=etpass.getText().toString();
					
					ed.putString("name", uName);
					ed.putString("password", uPass);
					ed.putBoolean("created", true);
					
					ed.commit();
					
					Intent i1=new Intent(MainActivity.this,ContactActivity.class);
					startActivity(i1);
					finish();
				}
			});
		}
		else{
				setContentView(R.layout.log_in);
				etgetpass=(EditText)findViewById(R.id.et_getpass);
				blogin=(Button)findViewById(R.id.login);
				
			
				
				
				blogin.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String passcheck=etgetpass.getText().toString();
						
						if(passcheck.equals(sp.getString("password", ""))){
							Intent i2=new Intent(MainActivity.this,ContactActivity.class);
							startActivity(i2);
							finish();
						}else{
							Toast.makeText(MainActivity.this, "chawanprash khaya karo", Toast.LENGTH_SHORT).show();
						}
					}
				});
		}
	
	}
	
}
