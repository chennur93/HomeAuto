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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditApp extends ActionBarActivity {
	
	Bundle bundle;
	TextView teuserHeader;
	EditText eeAppName, eeEnergy, eeOpTime,eeStartTime, eeDeadline, eeConstraints;
	Button beDone, beBack;
	String IPADD="10.39.197.133"; //10.39.202.120";
	String IPADD2="10.39.208.128";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_app);
		
		//mapping text view
				teuserHeader=(TextView)findViewById(R.id.teUserHeader);
				/*-----get bundle info--------*/
				bundle=getIntent().getExtras();
				final String userid=bundle.getString("username");
				final String appliance=bundle.getString("appliance");
				teuserHeader.setText("USER: "+ userid);
				
				//mapping Edittext value
				eeAppName=(EditText)findViewById(R.id.eeAppname);
				eeEnergy=(EditText)findViewById(R.id.eeEnergy);
				eeOpTime=(EditText)findViewById(R.id.eeOpTime);
				eeStartTime=(EditText)findViewById(R.id.eeStartTime);
				eeDeadline=(EditText)findViewById(R.id.eeDeadline);
				eeConstraints=(EditText)findViewById(R.id.eeConstraints);
				
				
				//mapping buttons
				beDone=(Button)findViewById(R.id.beDone);
				beBack=(Button)findViewById(R.id.beBack);
		
				eeAppName.setText(appliance);
				eeAppName.setFocusableInTouchMode(false);
				
				String conResult = getConnection ("http://"+IPADD+"/viewreq.php","retrieve",userid,appliance,"","","","","");
				try{
					JSONArray jArray = new JSONArray(conResult);

					//for(int i=0;i<jArray.length();i++){
					for(int i=0;i<jArray.length();i++){	
						JSONObject json_data = jArray.getJSONObject(i);

						String energy =json_data.getString("Energy");
						String optime = json_data.getString("OperationTime");
						String sttime =json_data.getString("UserStartTime");
						String deadline =json_data.getString("UserEndTime");
						String constraint =json_data.getString("ConstraintType");
						//System.out.println(energy);
						

						eeEnergy.setText(energy);
						eeOpTime.setText(optime);
						eeStartTime.setText(sttime);
						eeDeadline.setText(deadline);
						eeConstraints.setText(constraint);

					}
				}

				catch(JSONException e){
					//Log.e("log_tag", "Error parsing data "+e.toString());
					Log.e("log_tag", "Error parsing data "+e.toString());
				}
		
		//edit done=update
				beDone.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String energy=eeEnergy.getText().toString();
						String optime=eeOpTime.getText().toString();
						String starttime=eeStartTime.getText().toString();
						String deadline=eeDeadline.getText().toString();
						String constraints=eeConstraints.getText().toString();
						
						if(starttime.contains(":")){
							String[] hhmm=starttime.split(":");
							if(Integer.parseInt(hhmm[0])<= 9){
								hhmm[0]="0"+hhmm[0];
							}
							starttime=hhmm[0]+hhmm[1];
							}
							
							if(deadline.contains(":")){
								String[] hhmm=deadline.split(":");
								if(Integer.parseInt(hhmm[0])<= 9){
									hhmm[0]="0"+hhmm[0];
								}
								deadline=hhmm[0]+hhmm[1];
							}
						
					
						if (energy.length() == 0){
							eeEnergy.setError("Please enter Energy");
						}
						else if (optime.length() == 0){
							eeOpTime.setError("Please enter operation time");
						}
						else if (starttime.length() == 0){
							eeStartTime.setError("Please enter Start time");
						}
						else if(Integer.parseInt(deadline)>2400){
							eeDeadline.setError("Enter deadline less than 2400Hrs");
						}
						else if(Integer.parseInt(starttime)>2400){
							eeStartTime.setError("Enter start time less than 2400Hrs");
						}
						
						else if(Integer.parseInt(optime)>(Integer.parseInt(deadline)-Integer.parseInt(starttime)))
						{
							eeOpTime.setError("Operation Time cannot be greater than interval");
						}
						else if (!(constraints.equalsIgnoreCase("HARD")||constraints.equalsIgnoreCase("SOFT"))){
							eeConstraints.setError("Enter either Hard or Soft");
						}
						//finally on successful verification the appropriate message is displayed
						else {
							
							if(!starttime.contains(":")){
								String hh=starttime.substring(0,2);
								String mm=starttime.substring(2,4);
								starttime=hh+":"+mm;
								}
								
								if(!deadline.contains(":")){
								String hh1=deadline.substring(0,2);
								String mm1=deadline.substring(2,4);
								deadline=hh1+":"+mm1;
								}
								
							try{
							String x1=getConnection("http://"+IPADD+"/viewreq.php","update",userid,appliance,energy,optime,starttime,deadline,constraints);
							
							String x2=getConnection("http://"+IPADD2+"/viewreq.php","update",userid,appliance,energy, optime, starttime, deadline,constraints);
							}catch(Exception e){
								Log.e("Q_Tag","Querry fail");
							}
							Toast.makeText(getBaseContext(),"Appliance Details Updated!",Toast.LENGTH_SHORT).show();
							finish();
						}
					}	
				});
				
		/*==========Back Button Listener==========*/
		beBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_app, menu);
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
	
	/*-------------------------------------------HTTP POST and get connection----------------------------------------------------
	 * --------------------------------------------------------------------------------------------------------------------------*/
	
	
	public String getConnection(String url, String request, String usr, String app, 
			String ene, String opt,String stm, String ddl,String cnst){

	//	Bundle bundle = new Bundle();
	//	Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("appliance",app));
		
		//if(request.equals("update")){

			nameValuePairs1.add(new BasicNameValuePair("energy",ene));
			nameValuePairs1.add(new BasicNameValuePair("optime",opt));
			nameValuePairs1.add(new BasicNameValuePair("stime",stm));
			nameValuePairs1.add(new BasicNameValuePair("deadline",ddl));
			nameValuePairs1.add(new BasicNameValuePair("constraints",cnst));
		//}

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
