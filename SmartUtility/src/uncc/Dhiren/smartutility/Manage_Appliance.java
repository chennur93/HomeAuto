package uncc.Dhiren.smartutility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Manage_Appliance extends ActionBarActivity {
	
	Bundle bundle;
	Button bAddApp, bViewApp, bEditApp, bDeleteApp;
	Spinner sApp;
	String IPADD="10.39.197.133";//10.39.202.120";
	String IPADD2="10.39.208.128";//10.39.202.120";
	String user;
	int appPosition;
	String appliance;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage__appliance);
		bAddApp=(Button)findViewById(R.id.bAddApp);
		bEditApp=(Button)findViewById(R.id.bEditApp);
		bViewApp=(Button)findViewById(R.id.bViewApp);
		bDeleteApp=(Button)findViewById(R.id.bDelApp);
		sApp=(Spinner)findViewById(R.id.AppSpinner);
		ArrayList<String> appList = new ArrayList<String>();
		ArrayAdapter<String> appAdapter; 
		
		
		bundle = getIntent().getExtras();
		String user=bundle.getString("username");
		appAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,appList);
		
		String conResult = getConnection ("http://"+IPADD+"/getlist.php","getlisting",user,"appliance","","","","");
		try{
			JSONArray jArray = new JSONArray(conResult);
			appList.clear();
			for(int i=0;i<jArray.length();i++){	
				JSONObject json_data = jArray.getJSONObject(i);
				appList.add(json_data.getString("AppName"));
			}
		}

		catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		
		
		bAddApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent AddViewApp= new Intent(getBaseContext(),AddViewApp.class);
				AddViewApp.putExtras(bundle);
				startActivity(AddViewApp);
				
			}
		});
		
		bViewApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent ViewApp= new Intent(getBaseContext(),ViewApp.class);
				bundle.putString("appliance",appliance);
				ViewApp.putExtras(bundle);
				startActivity(ViewApp);
				
			}
		});
		
		bEditApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent EditApp= new Intent(getBaseContext(),EditApp.class);
				bundle.putString("appliance",appliance);
				EditApp.putExtras(bundle);
				startActivity(EditApp);
				
			}
		});
		
		
		/*===============Delete Button Stub=============================*/
		bDeleteApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder deleteApp = new AlertDialog.Builder(Manage_Appliance.this);
				deleteApp.setMessage("Do you really want to delete appliance: "+ appliance+"?");
				deleteApp.setCancelable(true);
				deleteApp.setPositiveButton("DELETE",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                    String user=bundle.getString("username");
	                    getConnection ("http://"+IPADD+"/viewreq.php","delete",user,appliance,"","","","");
	                    getConnection ("http://"+IPADD2+"/viewreq.php","delete",user,appliance,"","","","");
	                    finish();
	                    onResume();
	                    
	            		
	                }
	            });
				
				deleteApp.setNegativeButton("No",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });

	            AlertDialog alert11 = deleteApp.create();
	            alert11.show();
				
			}
		});
		/*===============END OF DELETE BUTTON===========================*/
		//appAdapter.notifyDataSetChanged();
		//sApp.setAdapter(appAdapter);
		
		appAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		sApp.setAdapter(appAdapter);
		sApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				appliance=sApp.getItemAtPosition(position).toString();
				appPosition=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		appAdapter.setNotifyOnChange(true);
		
		
	}

	
	
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		String conResult = getConnection ("http://"+IPADD+"/getlist.php","getlisting",user,"","","","","");
		try{
			ArrayList<String> appList1 = new ArrayList<String>();
			ArrayAdapter<String> appAdapter1;
			appAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,appList1);
			appList1.clear();
			
			
			
			JSONArray jArray = new JSONArray(conResult);
			appList1.clear();
			
			for(int i=0;i<jArray.length();i++){	
				JSONObject json_data = jArray.getJSONObject(i);
					appList1.add(json_data.getString("AppName"));
					
			}
			
			appAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
			sApp.setAdapter(appAdapter1);
			
			
		}

		catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());

		}
		
		
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String conResult = getConnection ("http://"+IPADD+"/getlist.php","getlisting",user,"","","","","");
		try{
			ArrayList<String> appList1 = new ArrayList<String>();
			ArrayAdapter<String> appAdapter1;
			appAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,appList1);
			appList1.clear();
			
			
			
			JSONArray jArray = new JSONArray(conResult);
			appList1.clear();
			
			for(int i=0;i<jArray.length();i++){	
				JSONObject json_data = jArray.getJSONObject(i);
					appList1.add(json_data.getString("AppName"));
					
			}
			
			appAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
			sApp.setAdapter(appAdapter1);
			
			
		}

		catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());

		}
		
		
		
		
	}








	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage__appliance, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
/*
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		appPosition=position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	*/
	
	
	
	
	
	
	
	
	
/*------------------------------------Method to get list of Appliance for user--------------------------------------------------------*/

	public String getConnection(String url, String request, String usr, String app, 
			String fName, String lName,String mail, String phone){

	//	Bundle bundle = new Bundle();
	//	Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("appliance",app));
		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to your ip
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		}
		catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			inputStream.close();
			result=sb.toString();
		}
		catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}
		return result;

	}
	
	
	
	
}
