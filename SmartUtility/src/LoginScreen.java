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



import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginScreen extends ActionBarActivity {
	final static String IPADD="10.39.197.133";//"10.39.202.120";
	final static String IPADD2="10.39.208.128";
	String usrnme;
	String passwd;
	
	EditText userName, passWord;
	
	Button bReset, bLogin, bRegister, bForgPwd, bExit;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		
		//thread policy
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		
		//mapping EditText
		userName=(EditText)findViewById(R.id.eUsrName);
		passWord=(EditText)findViewById(R.id.ePassword);
		
		//mapping layout buttons
		bReset=(Button)findViewById(R.id.bReset);
		bLogin=(Button)findViewById(R.id.bLogin);
		bRegister=(Button)findViewById(R.id.bRegister);
		bForgPwd=(Button)findViewById(R.id.bForgot);
		bExit=(Button)findViewById(R.id.bExit);
		
		//Listener for Reset Button
		bReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName.setText(null);
				passWord.setText(null);
			}
		});
		
		//Listener for register button
		bRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable()){
					Log.d("test","Network available");
					Intent userRegister = new Intent(getBaseContext(),UserRegister.class);
					startActivity(userRegister);
				}

				else{	
					Toast.makeText(getBaseContext(), "No connection", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//Listener for Login Button
		bLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				usrnme = userName.getText().toString();
				passwd = passWord.getText().toString();
				
				/*development purpose*/
				/*
				usrnme="vdeshpa1";
				passwd="hadoop";
				*/
				
				if(usrnme.equals("") ||passwd.equals("")){
					Toast.makeText(getBaseContext(),"Please enter Username and Password!", Toast.LENGTH_SHORT).show();
				}
				else if(isNetworkAvailable()){
					Log.d("test","Network available");

					String authPasswd = getConnection(usrnme, passwd);
					String utilPass = getConnectionUtil(usrnme, passwd);

					if(!((authPasswd.equals("null\n")) || (authPasswd.equals("")))){

						if(authPasswd.equals("true\n") && utilPass.equals("true\n")){

							Toast.makeText(getBaseContext(),"Login Successfull!: Home+Utility", Toast.LENGTH_SHORT).show();
							Bundle bundle = new Bundle();
							bundle.putString("username", usrnme);
							Intent userHomeActivity = new Intent(getBaseContext(),User_home.class);
							userHomeActivity.putExtras(bundle);
							startActivity(userHomeActivity);
						}
						else
							Toast.makeText(getBaseContext(),"Invalid Username/Password!", Toast.LENGTH_SHORT).show();
					}
					else
						Toast.makeText(getBaseContext(),"Login Failed!", Toast.LENGTH_SHORT).show();
				}

				else{	
					Toast.makeText(getBaseContext(),"No connectivity", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//Listener for Exit Button
		bExit.setOnClickListener(new View.OnClickListener() {
			
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
		getMenuInflater().inflate(R.menu.login_screen, menu);
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




/*--------------------------------------------------------------------------------------*/
/*nw availablity method and http post---------------------------------------------------*/
/*--------------------------------------------------------------------------------------*/

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null, otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
		}
	
	public String getConnection(String usr, String pwd){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("password",pwd));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+IPADD+"/login.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		}
		catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
			Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
			return "";
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

	
	
	/*------util----*/
	
	public String getConnectionUtil(String usr, String pwd){

		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		nameValuePairs1.add(new BasicNameValuePair("password",pwd));

		//http postappSpinners
		try{
			HttpClient httpclient = new DefaultHttpClient();

			// have to change the ip here to correct ip
			HttpPost httppost = new HttpPost("http://"+IPADD2+"/login.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		}
		catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
			Toast.makeText(getBaseContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
			return "";
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
