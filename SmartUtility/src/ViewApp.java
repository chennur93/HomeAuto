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
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewApp extends ActionBarActivity {
Bundle bundle;
String IPADD="10.39.197.133";//10.39.202.120";
TextView tvUserHeader, tvAppName, tvEnergy, tvOpTime, tvStarttime,
			tvDeadline, tvConstraints;
Button bEdit,bCancel,bBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_app);
		
		
		bEdit=(Button)findViewById(R.id.bEdit);
		
		bBack=(Button)findViewById(R.id.bBack);
		
		bundle=getIntent().getExtras();
		
		String user=bundle.getString("username");
		String appliance=bundle.getString("appliance");
		//String user="vdeshpa1";
		//String appliance="Washing";
		tvUserHeader=(TextView)findViewById(R.id.tvUserHeader);
		tvAppName=(TextView)findViewById(R.id.tvAppname);
		tvEnergy=(TextView)findViewById(R.id.tvEnergy);
		tvOpTime=(TextView)findViewById(R.id.tvOpTime);
		tvStarttime=(TextView)findViewById(R.id.tvStartTime);
		tvDeadline=(TextView)findViewById(R.id.tvDeadline);
		tvConstraints=(TextView)findViewById(R.id.tvConstraint);
		
		tvUserHeader.setText("USER ID: "+user);
		

		String conResult = getConnection ("http://"+IPADD+"/viewreq.php","retrieve",user,appliance,"","","","");
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
				
				

				
				tvAppName.setText("Appliance Name: "+appliance);
				tvEnergy.setText("Energy: "+energy+" Watts");
				tvOpTime.setText("Operation Time: "+ optime+" mins");
				tvStarttime.setText("Start Time: "+ sttime+" Hrs");
				tvDeadline.setText("Deadline: "+ deadline+" Hrs");
				tvConstraints.setText("Constraints: "+constraint);

			}
		}

		catch(JSONException e){
			//Log.e("log_tag", "Error parsing data "+e.toString());
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
	
		bEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent EditApp= new Intent(getBaseContext(),EditApp.class);
				EditApp.putExtras(bundle);
				startActivity(EditApp);
			}
		});
		
	
		
		
		bBack.setOnClickListener(new View.OnClickListener() {
			
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
		getMenuInflater().inflate(R.menu.view_app, menu);
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
			String fName, String lName,String mail, String phone){

	//	Bundle bundle = new Bundle();
	//	Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("appliance",app));
		/*
		if(request.equals("update")){

			nameValuePairs1.add(new BasicNameValuePair("password",pwd));
			nameValuePairs1.add(new BasicNameValuePair("firstname",fName));
			nameValuePairs1.add(new BasicNameValuePair("lastname",lName));
			nameValuePairs1.add(new BasicNameValuePair("email",mail));
			nameValuePairs1.add(new BasicNameValuePair("phone",phone));
		}
*/
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
