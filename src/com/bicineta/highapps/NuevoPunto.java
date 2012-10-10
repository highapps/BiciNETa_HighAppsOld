package com.bicineta.highapps;

/*
 * 				ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION ATENCION 
 * 							Esta es la clase qe corresponde al boton de acceso directo de Nuevo punto... 
 * 														Hay qe arreglarla... 
 * 														Asiqe no la pesqui
 *  
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class NuevoPunto extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
	private Context c;
	
	public NuevoPunto(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	public NuevoPunto(Drawable m, Context context) {
		// TODO Auto-generated constructor stub
		this(m);
		c = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return puntos.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return puntos.size();
	}
	
	public void insertarPunto(OverlayItem item){
		puntos.add(item);
		this.populate();
	}

}
