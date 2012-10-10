package com.bicineta.highapps;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class LocalesMap extends MapActivity{
	
	MapView map;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplocales);
        map = (MapView)findViewById(R.id.mvLocales);
        map.setBuiltInZoomControls(true);
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
