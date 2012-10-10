package com.bicineta.highapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BiciNETa_HighAppsActivity extends Activity {
	
	Button bVerVias, bVerEstacionamientos, bVerLocales;
	TextView tvAppName;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        bVerVias = (Button)findViewById(R.id.bVerVias);
        bVerEstacionamientos = (Button)findViewById(R.id.bVerEstacionamientos);
        bVerLocales = (Button)findViewById(R.id.bVerLocales);
        tvAppName = (TextView)findViewById(R.id.tvAppName);
        
        bVerVias.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openViasMap = new Intent("com.bicineta.highapps.VIASMAP");
				startActivity(openViasMap);
			}
		});
        
        bVerEstacionamientos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openEstacionamientosMap = new Intent("com.bicineta.highapps.ESTACIONAMIENTOSMAP");
				startActivity(openEstacionamientosMap);
			}
		});
        
        bVerLocales.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openLocalesMap = new Intent("com.bicineta.highapps.LOCALESMAP");
				startActivity(openLocalesMap);
			}
		});
    }
}