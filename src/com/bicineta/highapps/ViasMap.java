package com.bicineta.highapps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class ViasMap extends MapActivity {
	
	MapView map;
	
	long start, stop;
	
	MyLocationOverlay compass;
	MapController controller;
	int xCord, yCord;
	GeoPoint touchedPoint;
	//Drawable d; //Recordar descomentar el import del drawable pa usarlo
	List<Overlay> overlayList;
	
	//Vibrator vibrar = (Vibrator) getSystemService(ViasMap.VIBRATOR_SERVICE);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapvias);
        
        map = (MapView)findViewById(R.id.mvVias);
        map.setBuiltInZoomControls(true);
        
        Presionar p = new Presionar();
        overlayList = map.getOverlays();
        overlayList.add(p);
        
        compass = new MyLocationOverlay(ViasMap.this, map);
        overlayList.add(compass);
        controller = map.getController();//-335198249,-706597648
        GeoPoint point = new GeoPoint(-33519824,-70659764);
        controller.animateTo(point);
        controller.setZoom(12);
        /* Esta wea es del drawable de arriba
         * d = getResources().getDrawable(R.drawable.bicipin);
         */
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		compass.disableCompass();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		compass.enableCompass();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class Presionar extends Overlay{
		public boolean onTouchEvent(MotionEvent e, MapView m){
			if(e.getAction() == MotionEvent.ACTION_DOWN){
				start = e.getEventTime();
				xCord = (int)e.getX();
				yCord = (int)e.getY();
				touchedPoint = map.getProjection().fromPixels(xCord, yCord);
				
			}
			if(e.getAction() == MotionEvent.ACTION_UP){
				stop = e.getEventTime();
			}
			if(stop - start > 1500){
				AlertDialog alert = new AlertDialog.Builder(ViasMap.this).create();
				alert.setTitle("Menú");
				alert.setMessage("Elige una opción");
				alert.setButton("Nuevo Punto", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						/* 
						 * Esta wea hay que arreglarla... se pifea la wea...  por ahora no la pesquí
						
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "qe pasa", "segundo string");
						NuevoPunto nuevo = new NuevoPunto(d, ViasMap.this);
						nuevo.insertarPunto(overlayItem);
						overlayList.add(nuevo);
						
						*/
						
						
						
						
					}
				});
				alert.setButton2("Obtener Dirección", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
						try{
							List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitudeE6() / 1E6, touchedPoint.getLongitudeE6() / 1E6, 1);
							if (address.size() > 0){
								String display = "";
								for (int i=0; i<address.get(0).getMaxAddressLineIndex(); i++){
									display += address.get(0).getAddressLine(i) + "\n";
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
				alert.setButton3("Cambiar Vista", new DialogInterface.OnClickListener() {
					
					@Override
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

}
