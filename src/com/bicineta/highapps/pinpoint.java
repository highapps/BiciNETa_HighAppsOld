package com.bicineta.highapps;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;


public class pinpoint extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> pinpoints = new ArrayList<OverlayItem>();
	private Context c;
	
	public pinpoint(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public pinpoint(Drawable m,Context context) {
		
		// TODO Auto-generated constructor stub
		this(m);
		c = context;
	}
	

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoints.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoints.size();
	}
	
	public void insertPinpoint(OverlayItem item){
		pinpoints.add(item);//esta wea se manda por json
		this.populate();
	}
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = pinpoints.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(c);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}

}
