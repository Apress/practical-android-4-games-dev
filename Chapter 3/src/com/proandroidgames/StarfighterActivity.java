package com.proandroidgames;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StarfighterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*display the splash screen image*/
        setContentView(R.layout.splashscreen);
        /*start up the splash screen and main menu in a time delayed thread*/
        
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                   Intent mainMenu = new Intent(StarfighterActivity.this, SFMainMenu.class);
                   StarfighterActivity.this.startActivity(mainMenu);
                   StarfighterActivity.this.finish();
                   overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            }
    }, SFEngine.GAME_THREAD_DELAY);

    }
}