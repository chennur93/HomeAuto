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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManageSchedule extends ActionBarActivity {

	Button bHome,bUtil,bCommit;
	Bundle bundle;
	int cFlag=0;
	TextView userHeader;
	String IPADD="10.39.197.133";//10.39.202.120";
	String IPADD2="10.39.208.128";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_schedule);
		userHeader=(TextView)findViewById(R.id.tuserHeaderMan);
		bHome=(Button)findViewById(R.id.bHome);
		bUtil=(Button)findViewById(R.id.bUtility);
		bCommit=(Button)findViewById(R.id.bCommit);
		bCommit.setClickable(false);
		bCommit.setFocusableInTouchMode(false);
		bundle=getIntent().getExtras();
		final String usrid=bundle.getString("username");
		userHeader.setText("USER: "+usrid);
		
		bUtil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String result=getConnection("http://"+IPADD2+"/homerun.php","",usrid,"","","","","","","");
				cFlag=1;
				Toast.makeText(getBaseContext(),"Schedule Available",Toast.LENGTH_SHORT).show();
				Toast.makeText(getBaseContext(),"Schedule Available",Toast.LENGTH_SHORT).show();
				Toast.makeText(getBaseContext(),"Please re-view before commiting",Toast.LENGTH_LONG).show();
				bCommit.setClickable(true);
				bCommit.setFocusableInTouchMode(true);
				bundle.putString("server", "util");
				Intent PreView= new Intent(getBaseContext(),PreView.class);
				PreView.putExtras(bundle);
				startActivity(PreView);
				bCommit.setClickable(true);
			}
		});
		
		
		bHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					
				String result=getConnection("http://"+IPADD+"/homerun.php","",usrid,"","","","","","","");
				cFlag=2;	
				Toast.makeText(getBaseContext(),"Schedule Available",Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(),"Please re-view before commiting",Toast.LENGTH_LONG).show();
					bCommit.setClickable(true);
					bCommit.setFocusableInTouchMode(true);
					bundle.putString("server", "home");
					Intent PreView= new Intent(getBaseContext(),PreView.class);
					PreView.putExtras(bundle);
					startActivity(PreView);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		bCommit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(cFlag==2){
					String result=getConnection("http://"+IPADD+"/commit.php","",usrid,"","","","","","","");
					String comToUtil=getConnection("http://"+IPADD+"/getlist.php","",usrid,"","","","","","","");
					try{
						JSONArray jArray = new JSONArray(comToUtil);
						for(int i=0;i<jArray.length();i++){	
							JSONObject json_data = jArray.getJSONObject(i);
							String app=json_data.getString("AppName");
							String stt=json_data.getString("ScheduledStartTime");
							String set=json_data.getString("ScheduledEndTime");
							String cst=json_data.getString("Cost");
							String pvcst=json_data.getString("PreViewCost");
							String pstt=json_data.getString("PreViewStartTime");
							String pett=json_data.getString("PreViewEndTime");
							
							String updateUtil=getConnection("http://"+IPADD2+"/updutill.php","",usrid,app,stt,set,cst,pvcst,pstt,pett);
							
							
						}
					}

					catch(JSONException e){
						Log.e("log_tag", "Error parsing data "+e.toString());
					}
					
					
					Toast.makeText(getBaseContext(),"Commit done!! on home",Toast.LENGTH_SHORT).show();
					cFlag=0;
				}
				
				else if(cFlag==1){
					//-------------commit on utility server synced with home---------------------------
					String result2=getConnection("http://"+IPADD2+"/commit.php","",usrid,"","","","","","","");
					String result3=getConnection("http://"+IPADD2+"/fetchcommit.php","",usrid,"","","","","","","");
					//util to home commit
					String comToUtil=getConnection("http://"+IPADD2+"/getlist.php","",usrid,"","","","","","","");
					try{
						JSONArray jArray = new JSONArray(comToUtil);
						for(int i=0;i<jArray.length();i++){	
							JSONObject json_data = jArray.getJSONObject(i);
							String app=json_data.getString("AppName");
							String stt=json_data.getString("ScheduledStartTime");
							String set=json_data.getString("ScheduledEndTime");
							String cst=json_data.getString("Cost");
							String pvcst=json_data.getString("PreViewCost");
							String pstt=json_data.getString("PreViewStartTime");
							String pett=json_data.getString("PreViewEndTime");
							
							String updateUtil=getConnection("http://"+IPADD+"/updhome.php","",usrid,app,stt,set,cst,pvcst,pstt,pett);
							
							
						}
					}

					catch(JSONException e){
						Log.e("log_tag", "Error parsing data "+e.toString());
					}
					
					
					Toast.makeText(getBaseContext(),"Commit done!! on utility",Toast.LENGTH_SHORT).show();
					cFlag=0;
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_schedule, menu);
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
	
	
	
	/*-------------------------------HTTP STUB------------------------------------------------
	 * ---------------------------------------------------------------------------------------
	 */
	public String getConnection(String url, String request, String usr, String app, 
			String stt, String set,String cst, String pvcst, String pstt, String pett){

	//	Bundle bundle = new Bundle();
	//	Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		//nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("appliance",app));
			nameValuePairs1.add(new BasicNameValuePair("start",stt));
			nameValuePairs1.add(new BasicNameValuePair("end",set));
			nameValuePairs1.add(new BasicNameValuePair("cost",cst));
			nameValuePairs1.add(new BasicNameValuePair("pcost",pvcst));
			nameValuePairs1.add(new BasicNameValuePair("pstart",pstt));
			nameValuePairs1.add(new BasicNameValuePair("pend",pett));
			
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
