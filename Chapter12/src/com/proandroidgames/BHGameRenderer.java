package com.proandroidgames;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class BHGameRenderer implements Renderer{
	private BHCorridor corridor = new BHCorridor();
	private float corridorZPosition = -5f;
	private float playerRotate = 0f;

	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0 ;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		loopStart = System.currentTimeMillis();
		// TODO Auto-generated method stub
		try {
			if (loopRunTime < BHEngine.GAME_THREAD_FPS_SLEEP){
				Thread.sleep(BHEngine.GAME_THREAD_FPS_SLEEP - loopRunTime);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		drawCorridor(gl);
		
		loopEnd = System.currentTimeMillis();
	    loopRunTime = ((loopEnd - loopStart));
    
	}
	
	
	
	
	private void drawCorridor(GL10 gl){
		
		if (corridorZPosition <= -5f){
			corridorZPosition = -5f;
		}
		if (corridorZPosition >= 0f){
			corridorZPosition = 0f;
		}
		
		switch(BHEngine.playerMovementAction){
		case BHEngine.PLAYER_FORWARD:
			corridorZPosition += BHEngine.PLAYER_WALK_SPEED;
			break;
		case BHEngine.PLAYER_LEFT:
			playerRotate -= BHEngine.PLAYER_ROTATE_SPEED;
			break;
		case BHEngine.PLAYER_RIGHT:
			playerRotate += BHEngine.PLAYER_ROTATE_SPEED;
			break;
		default:
				break;
		}
		
		GLU.gluLookAt(gl, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1f, 0f);
		gl.glTranslatef(-0.5f, -0.5f, corridorZPosition);
		gl.glRotatef( playerRotate, 0.0f,1.0f, 0.0f);
		
		corridor.draw(gl);

	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

		gl.glViewport(0, 0, width,height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl, 45.0f, (float) width / height, .1f, 100.f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    
	     
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glDisable(GL10.GL_DITHER); 
		
		corridor.loadTexture(gl, BHEngine.BACK_WALL, BHEngine.context);
		
	}
	
}