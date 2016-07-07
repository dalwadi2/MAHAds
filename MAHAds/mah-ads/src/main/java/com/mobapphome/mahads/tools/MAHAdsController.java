package com.mobapphome.mahads.tools;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobapphome.mahads.ProgramItmAdptPrograms;
import com.mobapphome.mahads.types.Program;

public class MAHAdsController {
	public static final String MAH_ADS_INTERNAL_CALLED = "internal_called";
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static boolean internalCalled = false;
	private static boolean lightTheme = true;
	private static String fontName = null;


	private static List<Program> programsSelected = new LinkedList<>();


	public static void init(final Activity act, String urlRootOnServer) throws NullPointerException{
		MAHAdsController.urlRootOnServer = urlRootOnServer;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}

		sharedPref = act.getPreferences(Context.MODE_PRIVATE);

		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onSuccsess() {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(act);
			}

			@Override
			public void onError(final String errorStr) {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(act);
			}
		});
		updater.updateProgramList(act);
	}

	protected static SharedPreferences getSharedPref() {
		return sharedPref;
	}

	public static void setFontTextView(TextView tv) {
		if(fontName == null){
			return;
		}
		try{
			Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(),fontName);
			tv.setTypeface(font);
		}catch(RuntimeException r){
			Log.e("test", "Error " + r.getMessage());
		}
	}

	public static String getFontName() {
		return fontName;
	}

	public static void setFontName(String fontName) {
		MAHAdsController.fontName = fontName;
	}

	public static boolean isLightTheme() {
		return lightTheme;
	}

	public static void setLightTheme(boolean lightTheme) {
		MAHAdsController.lightTheme = lightTheme;
	}

	public static List<Program> getProgramsSelected() {
		synchronized (programsSelected) {
			return programsSelected;			
		}
	}

	public static void setProgramsSelected(List<Program> programsSelected) {
		synchronized (MAHAdsController.programsSelected) {
			MAHAdsController.programsSelected = programsSelected;			
		}
	}

	public static boolean isInternalCalled() {
		return internalCalled;
	}

	public static void setInternalCalled(boolean internalCalled) {
		MAHAdsController.internalCalled = internalCalled;
	}
	
	
}
