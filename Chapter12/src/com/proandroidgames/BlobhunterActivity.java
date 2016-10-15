package com.proandroidgames;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;


public class BlobhunterActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	private BHGameView gameView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	BHEngine.display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

    	super.onCreate(savedInstanceState);
        gameView = new BHGameView(this);
        setContentView(gameView);
        BHEngine.context = this;
    }
    @Override
    protected void onResume() {
       super.onResume();
       gameView.onResume();
    }

    @Override
    protected void onPause() {
       super.onPause();
       gameView.onPause();
    }
    
	@Override
   	public boolean onTouchEvent(MotionEvent event) {
   		//
   		float x = event.getX();
        float y = event.getY();
        int height = BHEngine.display.getHeight() / 4;
        int playableArea = BHEngine.display.getHeight() - height;
        if (y > playableArea){
        	switch (event.getAction()){
        	case MotionEvent.ACTION_DOWN:
        		if(x < BHEngine.display.getWidth() / 2){
        			BHEngine.playerMovementAction = BHEngine.PLAYER_LEFT;
        		}else{
        			BHEngine.playerMovementAction = BHEngine.PLAYER_RIGHT;
        		}
        		break;
        	case MotionEvent.ACTION_UP:
        		BHEngine.playerMovementAction = 0;
        		break;
        	}
        	
        }else{
        	switch (event.getAction()){
        	case MotionEvent.ACTION_DOWN:
        		BHEngine.playerMovementAction = BHEngine.PLAYER_FORWARD;
        		break;
        	case MotionEvent.ACTION_UP:
        		BHEngine.playerMovementAction = 0;
        		break;
        	}
        }

		return false;
    }
}