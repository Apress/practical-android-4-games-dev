package com.proandroidgames;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SFGameView extends GLSurfaceView {
	private SFGameRenderer renderer;
	
	public SFGameView(Context context) {
		super(context);
		
		renderer = new SFGameRenderer();
		
		this.setRenderer(renderer);
		

	}
	

}
