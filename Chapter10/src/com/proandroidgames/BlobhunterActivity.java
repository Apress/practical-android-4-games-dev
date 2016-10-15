package com.proandroidgames;

import android.app.Activity;
import android.os.Bundle;


public class BlobhunterActivity extends Activity {
    /** Called when the activity is first created. */
	
	private BHGameView gameView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
}