package com.bicineta.highapps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EstacionamientosMaps extends MapActivity implements LocationListener {
    /** Called when the activity is first created. */
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
        Button check= (Button) findViewById(R.id.button1);
        display.setText("hola1");
        new leer().execute("name");
        display.setText("hola2");
        //ejecuta el asunctask pa sacar la info del jason
        /*cuando haci click en el boton llena el mapa con los puntos a partir de la informacion que esta en info*/
        check.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
				
				if(info.length()>0)
				{
					StringTokenizer tokens = new StringTokenizer(info, "+");
					while(tokens.hasMoreElements())
					{
						nombre=tokens.nextToken();
						direccion=tokens.nextToken();
						lati=tokens.nextToken();
						latitude=Double.parseDouble(lati.trim());	
						longit=tokens.nextToken();
						longitude=Double.parseDouble(longit.trim());
						GeoPoint punto=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
						controller.animateTo(punto);
						d=getResources().getDrawable(R.drawable.ic_launcher);
						OverlayItem overlayItem = new OverlayItem(punto,nombre,direccion);
			    		pinpoint p = new pinpoint (d,EstacionamientosMaps.this);	
			    		p.insertPinpoint(overlayItem);
			    		overlayList.add(p);
			    		
					}
				}
			}
		});  
        
        Touchy t = new Touchy();
        overlayList = map.getOverlays();
        overlayList.add(t);
        compass = new MyLocationOverlay(EstacionamientosMaps.this, map);
        overlayList.add(compass);
        controller = map.getController();
        GeoPoint point = new GeoPoint(-33491419,-70617896); 
        controller.animateTo(point);
        controller.setZoom(15);        
        d=getResources().getDrawable(R.drawable.ic_launcher);
        
        //coloca punto en posicion actual
        Criteria crit = new Criteria();
        lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        towers = lm.getBestProvider(crit, false);
        Location location = lm.getLastKnownLocation(towers);
        if(location != null){
        	lat=(int)(location.getLatitude() *1E6);
    		longi=(int)(location.getLongitude() *1E6);
        	GeoPoint ourLocation = new GeoPoint(lat,longi);
            OverlayItem overlayItem = new OverlayItem(ourLocation, "das","asdasd");
    		pinpoint p = new pinpoint (d,EstacionamientosMaps.this);	
    		p.insertPinpoint(overlayItem);
    		overlayList.add(p);
        }else{
        	Toast.makeText(EstacionamientosMaps.this, "no encontro proveedor", Toast.LENGTH_SHORT).show();
        }        
    }
    
    /*asynctask pa hacer el httpclient y wea*/
    public class leer extends AsyncTask<String, Integer, String>{  //listo pasada

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
	protected void onPause() {
		// TODO Auto-generated method stub
		compass.disableCompass();
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		compass.enableCompass();
		super.onResume();
		lm.requestLocationUpdates(towers, 500, 1, this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class Touchy extends Overlay{
		@SuppressWarnings("deprecation")
		public boolean onTouchEvent(MotionEvent e, MapView m ){
			if(e.getAction()==MotionEvent.ACTION_DOWN){
				start = e.getEventTime();
				x=(int)e.getX();
				y=(int)e.getY();
				touchedPoint = map.getProjection().fromPixels(x, y);
			}
			if(e.getAction()==MotionEvent.ACTION_UP){
				stop = e.getEventTime();
			}
			if(stop - start > 1500){
				AlertDialog alert = new AlertDialog.Builder(EstacionamientosMaps.this).create();
				alert.setTitle("Opciones");
				alert.setMessage("Elige");
				alert.setButton("pon punto", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "das","asdasd");
						pinpoint p = new pinpoint (d,EstacionamientosMaps.this);	
						p.insertPinpoint(overlayItem);
						overlayList.add(p);
		
					}
				});
				alert.setButton2("obtener direccion", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
						try{
							List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitudeE6()/1E6, touchedPoint.getLongitudeE6()/1E6, 1);
							String display ="";
							if(address.size()>0){
								for(int i = 0;i<address.get(0).getMaxAddressLineIndex();i++){
									display += address.get(0).getAddressLine(i)+"\n";
								}
								Toast t = Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
								t.show();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							
						}
					}
				});
				alert.setButton3("Toggle View", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(map.isSatellite()){
							map.setSatellite(false);
							map.setStreetView(true);
						}else{
							map.setStreetView(false);
							map.setSatellite(true);
							
						}
						
					}
				});
				alert.show();
				return true;
			}
			return false;
		}
		
		
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lat=(int)(location.getLatitude() *1E6);
		longi=(int)(location.getLongitude() *1E6);
		GeoPoint ourLocation = new GeoPoint(lat,longi);
        OverlayItem overlayItem = new OverlayItem(ourLocation, "das","asdasd");
		pinpoint p = new pinpoint (d,EstacionamientosMaps.this);	
		p.insertPinpoint(overlayItem);
		overlayList.add(p);
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}