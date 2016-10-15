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
	public static float SCROLL_BACKGROUND_1  = .002f;
	public static float SCROLL_BACKGROUND_2  = .007f;
	public static final int BACKGROUND_LAYER_ONE = R.drawable.backgroundstars;
	public static final int BACKGROUND_LAYER_TWO = R.drawable.debris;
	public static final int PLAYER_BANK_LEFT_1 = 1;
	public static final int PLAYER_RELEASE = 3;
	public static final int PLAYER_BANK_RIGHT_1 = 4;
	public static final int PLAYER_FRAMES_BETWEEN_ANI = 9;
	public static final float PLAYER_BANK_SPEED = .1f;
	public static int CHARACTER_SHEET = R.drawable.character_sprite;
	public static int TOTAL_INTERCEPTORS = 10;
	public static int TOTAL_SCOUTS = 15;
	public static int TOTAL_WARSHIPS = 5;
	public static float INTERCEPTOR_SPEED = SCROLL_BACKGROUND_1 * 4f;
	public static float SCOUT_SPEED = SCROLL_BACKGROUND_1 * 6f;
	public static float WARSHIP_SPEED = SCROLL_BACKGROUND_2 * 4f;
	public static final int TYPE_INTERCEPTOR = 1;
	public static final int TYPE_SCOUT = 2;
	public static final int TYPE_WARSHIP = 3;
	public static final int ATTACK_RANDOM = 0;	
	public static final int ATTACK_RIGHT = 1;
	public static final int ATTACK_LEFT = 2;
	public static final float BEZIER_X_1 = 0f;
	public static final float BEZIER_X_2 = 1f;
	public static final float BEZIER_X_3 = 2.5f;
	public static final float BEZIER_X_4 = 3f;
	public static final float BEZIER_Y_1 = 0f;
	public static final float BEZIER_Y_2 = 2.4f;
	public static final float BEZIER_Y_3 = 1.5f;
	public static final float BEZIER_Y_4 = 2.6f;
	public static final int WEAPONS_SHEET = R.drawable.destruction;
	public static final int PLAYER_SHIELDS = 5;
	public static final int SCOUT_SHIELDS = 3;
	public static final int INTERCEPTOR_SHIELDS = 1;
	public static final int WARSHIP_SHIELDS = 5;
	public static final float PLAYER_BULLET_SPEED = .125f;
	/*Game Variables*/
	
	public static Context context;
	public static Thread musicThread;
	public static Display display;
	public static int playerFlightAction = 0;
	public static float playerBankPosX = 1.75f;
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
