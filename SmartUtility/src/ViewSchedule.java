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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSchedule extends ActionBarActivity {
Bundle bundle;
String IPADD="10.39.197.133";//10.39.202.120";
String IPADD2="10.39.208.128";
TextView headMsg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_schedule);
		bundle=getIntent().getExtras();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		String user=bundle.getString("username");
		headMsg=(TextView)findViewById(R.id.textView1);
		headMsg.setTextColor(Color.MAGENTA);
		
		try
	    {
				String result=getConnection ("http://"+IPADD+"/getlist.php","view",user,"","","","","");
	            JSONArray jArray = new JSONArray(result);

	            TableLayout tv=(TableLayout) findViewById(R.id.tViewSched);
	            tv.removeAllViewsInLayout();
	            tv.setBackgroundColor(Color.BLACK);
	            int flag=1;

	            for(int i=-1;i<jArray.length();i++)
	            //for(int i=-1;i<3;i++)
	                  // when i=-1, loop will display heading of each column
	                  // then usually data will be display from i=0 to jArray.length()
	                    {

	                            TableRow tr=new TableRow(ViewSchedule.this);

	                            tr.setLayoutParams(new LayoutParams(
	                                       LayoutParams.MATCH_PARENT,
	                                       LayoutParams.WRAP_CONTENT));


	                            if(flag==1)
	                            // this will be executed once
	                            {

	                                 TextView b3=new TextView(ViewSchedule.this);
	                                 b3.setText("Appliance");
	                                 b3.setTextColor(Color.CYAN);
	                                 b3.setTextSize(15);
	                                 tr.addView(b3);


	                                 TextView b4=new TextView(ViewSchedule.this);
	                                 b4.setPadding(10, 0, 0, 0);
	                                 b4.setTextSize(15);
	                                 b4.setText("Start Time");
	                                 b4.setTextColor(Color.CYAN);
	                                 tr.addView(b4);

	                                 TextView b5=new TextView(ViewSchedule.this);
	                                 b5.setPadding(10, 0, 0, 0);
	                                 b5.setText("End Time");
	                                 b5.setTextColor(Color.CYAN);
	                                 b5.setTextSize(15);
	                                 tr.addView(b5);
	                                 
	                                 TextView b6=new TextView(ViewSchedule.this);
	                                 b6.setPadding(10, 0, 0, 0);
	                                 b6.setText("Cost");
	                                 b6.setTextColor(Color.CYAN);
	                                 b6.setTextSize(15);
	                                 tr.addView(b6);


	                                 tv.addView(tr);

	                                 final View vline = new View(ViewSchedule.this);
	                                 vline.setLayoutParams(new       
	                                 TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
	                                 vline.setBackgroundColor(Color.CYAN);
	                                 tv.addView(vline); // add line below heading
	                                 flag=0;


	                            }

	                            else
	                            {



	                                JSONObject json_data = jArray.getJSONObject(i);

	                                TextView b=new TextView(ViewSchedule.this);
	                                String str=json_data.getString("AppName");
	                                b.setText(str);
	                                b.setTextColor(Color.RED);
	                                b.setTextSize(15);
	                                tr.addView(b);


	                                TextView b1=new TextView(ViewSchedule.this);
	                                b1.setPadding(10, 0, 0, 0);
	                                b1.setTextSize(15);
	                                String str1=json_data.getString("ScheduledStartTime");
	                                b1.setText(str1);
	                                b1.setTextColor(Color.RED);
	                                tr.addView(b1);

	                                TextView b2=new TextView(ViewSchedule.this);
	                                b2.setPadding(10, 0, 0, 0);
	                                String str2=json_data.getString("ScheduledEndTime");
                            		//json_data.getString("UserEndTime");
	                                b2.setText(str2);
	                                b2.setTextColor(Color.RED);
	                                b2.setTextSize(15);
	                                tr.addView(b2);

	                                
	                                TextView b3=new TextView(ViewSchedule.this);
	                                b3.setPadding(10, 0, 0, 0);
	                                String str3=json_data.getString("Cost");
	                                str3="$"+str3;
                            		//json_data.getString("UserEndTime");
	                                b3.setText(str3);
	                                b3.setTextColor(Color.RED);
	                                b3.setTextSize(15);
	                                tr.addView(b3);

	                                tv.addView(tr);


	                                final View vline1 = new View(ViewSchedule.this);
	                                vline1.setLayoutParams(new                
	                                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
	                                vline1.setBackgroundColor(Color.WHITE);
	                                tv.addView(vline1);  // add line below each row   


	                            }

	                   }

	            


	            }
	            catch(JSONException e)
	            {
	                    Log.e("log_tag", "Error parsing data "+e.toString());
	                    Toast.makeText(getApplicationContext(), "JsonArray fail", 
	                                                          Toast.LENGTH_SHORT).show();
	            }
		
				
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_schedule, menu);
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
	
	
	
	
	
	
	
	/*=============================================================================*/
	
	public String getConnection(String url, String request, String usr, String app, 
			String fName, String lName,String mail, String phone){

	//	Bundle bundle = new Bundle();
	//	Message msg = new Message();
		InputStream inputStream = null;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		//nameValuePairs1.add(new BasicNameValuePair("request",request));
		nameValuePairs1.add(new BasicNameValuePair("username",usr));
		//nameValuePairs1.add(new BasicNameValuePair("appliance",app));
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
