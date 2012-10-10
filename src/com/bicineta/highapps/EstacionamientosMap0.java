package com.bicineta.highapps;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class EstacionamientosMap0 extends MapActivity implements LocationListener{
	
	MapView map;
	long start,stop;
	MyLocationOverlay compass;
	MapController controller;
	int x,y;
	GeoPoint touchedPoint;
	Drawable d;
	List<Overlay> overlayList;
	LocationManager lm;
	String towers;
	int lat=0,longi=0;
	HttpClient client = new DefaultHttpClient();
 	final static String URL = "http://bicineta.herokuapp.com/points.json";
 	StringBuilder informacion;
 	String info,lati,longit,nombre,direccion;
 	TextView display;
 	Button check;
 	double latitude,longitude;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapestacionamientos);
        map = (MapView)findViewById(R.id.mvEstacionamientos);
        map.setBuiltInZoomControls(true);
        display = (TextView) findViewById(R.id.textView1);
        new leer().execute("name"); //ejecuta el asunctask pa sacar la info del jason
        display.setText(info);       
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public class leer extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				getjason prueba = new getjason();
				informacion=prueba.info(); //guarda los datos del punto en el String info

				return informacion.toString();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			info=result;
		}

	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}