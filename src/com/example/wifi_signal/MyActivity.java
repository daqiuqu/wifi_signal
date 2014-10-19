package com.example.wifi_signal;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends Activity {
	
	TextView tv;
	private Handler myHandler;
	private String serverIP = null;
	private client net = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       	myHandler = new Handler();
        myHandler.post(wifiTimer);
    }

	private Runnable wifiTimer = new Runnable() {
		public void run() {
			getWifiInfo();
			myHandler.postDelayed(this, 1000);
		}
	};

	private void exit_program() {
		myHandler.removeCallbacks(wifiTimer);
		System.out.println("have closed wifitimer and going to exit");
        	finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit_program();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void getWifiInfo() {
		String wserviceName = Context.WIFI_SERVICE;
		WifiManager wm = (WifiManager) getSystemService(wserviceName); 
		if (!wm.isWifiEnabled())
			wm.setWifiEnabled(true);
		wm.startScan();
		
		WifiInfo info = wm.getConnectionInfo();   
		int strength = info.getRssi();
		int speed = info.getLinkSpeed();  
		String units = WifiInfo.LINK_SPEED_UNITS;  
		String ssid = info.getSSID();  
		String mac_connect = info.getMacAddress();
		
		tv  = (TextView) findViewById(R.id.textView1);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		List<ScanResult> results = wm.getScanResults();
		String otherwifi = "The existing network is: \n\n";
		
		for (ScanResult result : results) {  
			otherwifi += result.SSID  + ":" + result.level + " mac:" + result.BSSID + "\n";
		}
		
		String text = "We are connecting to " + ssid + " " + mac_connect + " at " + String.valueOf(speed) + "   " + String.valueOf(units) + ". Strength : " + strength;
		otherwifi += "\n\n";
		otherwifi += text;
		System.out.println(otherwifi);
		
		tv.setText(otherwifi);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
	menu.add(Menu.NONE, Menu.FIRST + 1, 5, "send wifi info to server");
        //setContentView(R.layout.main);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case Menu.FIRST + 1:
			final EditText serverIPEditText = new EditText(this);
			new AlertDialog.Builder(this).setTitle("Please input server's IP:").setView(serverIPEditText)
				.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						serverIP = serverIPEditText.getText().toString();
						net = new client(serverIP);
						Toast.makeText(MyActivity.this, "Your input is:" + serverIP, Toast.LENGTH_SHORT).show();
						System.out.println("input server ip is: " + serverIP);
					}
				}).setNegativeButton("Cancel", null).show();
			System.out.println("click menu button");
		}
		return false;
	}
    
}
