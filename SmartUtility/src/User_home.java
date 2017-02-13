package uncc.Dhiren.smartutility;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class User_home extends ActionBarActivity {

	Bundle bundle;
	TextView tusrName;
	
	Button bManApp, bVieSch, bManSch, bManAcc, bLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		
		bundle = getIntent().getExtras();
		String user = bundle.getString("username");
		
		tusrName=(TextView)findViewById(R.id.tUserName);
		tusrName.setText("USER: "+user);
		
		bManApp=(Button)findViewById(R.id.bManageApp);
		bVieSch=(Button)findViewById(R.id.bViewSchedule);
		bManSch=(Button)findViewById(R.id.bManageSched);
		bManAcc=(Button)findViewById(R.id.bManageAccount);
		bLogout=(Button)findViewById(R.id.bLogout);
		
		
		//listener for logout
		bLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//listener for Manage Application Button
		bManApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ManageApp= new Intent(getBaseContext(),Manage_Appliance.class);
				ManageApp.putExtras(bundle);
				startActivity(ManageApp);
			}
		});
		
		
		bVieSch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ViewSchedule= new Intent(getBaseContext(),ViewSchedule.class);
				ViewSchedule.putExtras(bundle);
				startActivity(ViewSchedule);
			}
		});
		
		bManSch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ManageSchedule= new Intent(getBaseContext(),ManageSchedule.class);
				ManageSchedule.putExtras(bundle);
				startActivity(ManageSchedule);
				
			}
		});
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home, menu);
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
}
