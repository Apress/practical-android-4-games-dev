package com.proandroidgames;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;

public class SFEngine {
	/*Constants that will be used in the game*/
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
	public static final int SPLASH_SCREEN_MUSIC = R.raw.warfieldedit;
	public static final int R_VOLUME = 100;
	public static final int L_VOLUME = 100;
	public static final boolean LOOP_BACKGROUND_MUSIC = true;
	public static final int GAME_THREAD_FPS_SLEEP = (1000/60);	
	public static Context context;
	public static Thread musicThread;
	public static Display display;
	public static float SCROLL_BACKGROUND_1  = .002f;
	public static float SCROLL_BACKGROUND_2  = .007f;
	public static final int BACKGROUND_LAYER_ONE = R.drawable.backgroundstars;
	public static final int BACKGROUND_LAYER_TWO = R.drawable.debris;
	
	/*Kill game and exit*/
	public boolean onExit(View v) {
        try
        {
        	Intent bgmusic = new Intent(context, SFMusic.class);
        	context.stopService(bgmusic);
        	musicThread.stop();

        	return true;
        }catch(Exception e){
        	return false;
        }
     		
	}
	
		 
}
