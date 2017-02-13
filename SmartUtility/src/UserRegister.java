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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegister extends ActionBarActivity {
	final static String IPADD="10.39.197.133";//10.39.202.120";
	final static String IPADD2="10.39.208.128";
	
	EditText eUserName,ePassword,eVerifyPassword, eFirstName,
			eLastName, eEmail, ePhoneNumber;
	
	CheckBox cBox;
	
	Button bReset, bRegister, bBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_register);
		//mapping all edit text fields
		eUserName=(EditText)findViewById(R.id.eUsername);
		ePassword=(EditText)findViewById(R.id.ePassword);
		eVerifyPassword=(EditText)findViewById(R.id.eVerifyPassword);
		eFirstName=(EditText)findViewById(R.id.eFirstname);
		eLastName=(EditText)findViewById(R.id.eLastname);
		eEmail=(EditText)findViewById(R.id.eEmail);
		ePhoneNumber=(EditText)findViewById(R.id.ePhoneno);
		//check box
		cBox=(CheckBox)findViewById(R.id.cAgreeMent);
		//mapping all the buttons
		bReset=(Button)findViewById(R.id.bReset);
		bRegister=(Button)findViewById(R.id.bRegister);
		bBack=(Button)findViewById(R.id.bBack);
		
		cBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url="http://www.privacychoice.org/policy/mobile?policy=78ac71afd91243b2a86aa7d62a65ba20";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		
		//listener for reset
		bReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eUserName.setText(null);
				ePassword.setText(null);
				eVerifyPassword.setText(null);
				eFirstName.setText(null);
				eLastName.setText(null);
				eEmail.setText(null);
				ePhoneNumber.setText(null);
			}
		});
		
		//listener for back
		bBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//listener for register
		bRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username=eUserName.getText().toString();
				String password=ePassword.getText().toString();
				String verifypassword=eVerifyPassword.getText().toString();
				String firstname=eFirstName.getText().toString();
				String lastname=eLastName.getText().toString();
				String email=eEmail.getText().toString();
				String phone_No=ePhoneNumber.getText().toString();
				
				//boolean value to check the email id format
				boolean emailCheck = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
				
				// all the validations are done in sequence
				if (username.length() == 0){
					eUserName.setError("Please Enter Username");
				}
				else if (username.length() < 6){
					eUserName.setError("Please enter username greater than 6 characters");
				}
				else if (password.length() == 0){
					ePassword.setError("Please enter password");
				}
				else if ((password.length() < 6)){
					ePassword.setError("Password too short, please enter more than 6 chars");
				}
				else if (verifypassword.length() == 0){
					eVerifyPassword.setError("Please re-enter password!!");
				}
				else if (!password.equals(verifypassword)){
					eVerifyPassword.setError("Passwords do not match!");
				}
				else if (firstname.length() == 0){
					eFirstName.setError("Please enter first name");
				}
				else if (lastname.length() == 0){
					eLastName.setError("Please enter your last name");
				}
				else if (email.length() == 0){
					eEmail.setError("Enter email-id");
				}
				else if (!emailCheck){
					eEmail.setError("Please enter valid mail id");
				}
				else if (phone_No.length() == 0){
					ePhoneNumber.setError("Enter valid phone number");
				}
				else if(!cBox.isChecked()){
					Toast.makeText(getBaseContext(),"Please read terms and conditions",Toast.LENGTH_SHORT).show();
				}
				//finally on successful verification the appropriate message is displayed
				else {

					String status = getConnection(username,password,firstname, lastname, email, phone_No);
					String statusUtil = getConnectionUtil(username,password,firstname, lastname, email, phone_No);
					if(status.equals("true\n") && statusUtil.equals("true\n")){
						Toast.makeText(getBaseContext(),"Registration Successfull!",Toast.LENGTH_SHORT).show();
						finish();
					}
					else if(status.equals("false_user\n")){
						Toast.makeText(getBaseContext(),"Username already exists. Please enter a different username!",Toast.LENGTH_SHORT).show();
					}
					else if(status.equals("false_email\n")){
						Toast.makeText(getBaseContext(),"Email already registered. Please enter different email!",Toast.LENGTH_SHORT).show();
					}
					else

						Toast.makeText(getBaseContext(),"Problem! Server returned invalid value",Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_register, menu);
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




/*--------------------------------------------------------------------------------------
 * ---------------------------HTTP STUB------------------------------------------------
 * -------------------------------------------------------------------------------------*/


public String getConnection(String usr, String pwd, String fName, String lName,String mail, String phone){

	InputStream inputStream = null;
	String result = "";
	ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
	nameValuePairs1.add(new BasicNameValuePair("username",usr));
	nameValuePairs1.add(new BasicNameValuePair("password",pwd));
	nameValuePairs1.add(new BasicNameValuePair("firstName",fName));
	nameValuePairs1.add(new BasicNameValuePair("lastName",lName));
	nameValuePairs1.add(new BasicNameValuePair("email",mail));
	nameValuePairs1.add(new BasicNameValuePair("phone",phone));

	//http postappSpinners
	try{
		HttpClient httpclient = new DefaultHttpClient();

		// have to change the ip here to correct ip
		HttpPost httppost = new HttpPost("http://"+IPADD+"/register.php");
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




/*------------util stub-------------------*/
public String getConnectionUtil(String usr, String pwd, String fName, String lName,String mail, String phone){

	InputStream inputStream = null;
	String result = "";
	ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
	nameValuePairs1.add(new BasicNameValuePair("username",usr));
	nameValuePairs1.add(new BasicNameValuePair("password",pwd));
	nameValuePairs1.add(new BasicNameValuePair("firstName",fName));
	nameValuePairs1.add(new BasicNameValuePair("lastName",lName));
	nameValuePairs1.add(new BasicNameValuePair("email",mail));
	nameValuePairs1.add(new BasicNameValuePair("phone",phone));

	//http postappSpinners
	try{
		HttpClient httpclient = new DefaultHttpClient();

		// have to change the ip here to correct ip
		HttpPost httppost = new HttpPost("http://"+IPADD2+"/register.php");
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



