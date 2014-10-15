/*
package com.example.wifi_signal;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
*/


//package com.example.wifistrength;
package com.example.wifi_signal;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;
//import android.widget.Toast;

public class MyActivity extends Activity {
	
	TextView tv;
	private Handler myHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       	myHandler = new Handler();
        myHandler.post(wifiTimer);
    }

	protected void onDestroy(Bundle savedInstanceState) {
		myHandler.removeCallbacks(wifiTimer);
	}

	private Runnable wifiTimer = new Runnable() {
		public void run() {
			getWifiInfo();
			myHandler.postDelayed(this, 1000);
		}
	};

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
        //setContentView(R.layout.main);
        return true;
    }
    
}
