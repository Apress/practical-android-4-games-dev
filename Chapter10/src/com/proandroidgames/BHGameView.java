package com.proandroidgames;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BHGameView extends GLSurfaceView {
	private BHGameRenderer renderer;
	
	public BHGameView(Context context) {
		super(context);
		
		renderer = new BHGameRenderer();
		
		this.setRenderer(renderer);
		

	}
	

}
