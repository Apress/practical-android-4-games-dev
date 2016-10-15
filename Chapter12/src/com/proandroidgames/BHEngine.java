package com.proandroidgames;

import android.content.Context;
import android.view.Display;


public class BHEngine {
	/*Constants that will be used in the game*/
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int GAME_THREAD_FPS_SLEEP = (1000/60);	
	public static final int BACK_WALL = R.drawable.walltexture256;
	public static final int PLAYER_FORWARD = 1;
	public static final int PLAYER_RIGHT = 2;
	public static final int PLAYER_LEFT = 3;
	public static final float PLAYER_ROTATE_SPEED = 1f;
	public static final float PLAYER_WALK_SPEED = 0.1f;
	/*Game Variables*/
	public static int playerMovementAction = 0;
	public static Context context;
	public static Display display;
}
