package com.proandroidgames;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SFGameRenderer implements Renderer{
	private SFBackground background = new SFBackground();
	private SFBackground background2 = new SFBackground();
	private SFGoodGuy player1 = new SFGoodGuy();
	private SFEnemy[] enemies = new SFEnemy[SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS - 1];
	private SFTextures textureLoader;
	private int[] spriteSheets = new int[1];
	
	
	private int goodGuyBankFrames = 0;
	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0 ;
	
	private float bgScroll1;
	private float bgScroll2;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		loopStart = System.currentTimeMillis();
		// TODO Auto-generated method stub
		try {
			if (loopRunTime < SFEngine.GAME_THREAD_FPS_SLEEP){
				Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP - loopRunTime);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		
		scrollBackground1(gl);
		scrollBackground2(gl);

		movePlayer1(gl);
		moveEnemy(gl);
		
		//All other game drawing will be called here
		
		gl.glEnable(GL10.GL_BLEND); 
	    gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
	    loopEnd = System.currentTimeMillis();
	    loopRunTime = ((loopEnd - loopStart));
    
	}
	private void initializeInterceptors(){
		for (int x = 0; x<SFEngine.TOTAL_INTERCEPTORS -1 ; x++){
			SFEnemy interceptor = new SFEnemy(SFEngine.TYPE_INTERCEPTOR, SFEngine.ATTACK_RANDOM);
			enemies[x] = interceptor;
		}
	}
	private void initializeScouts(){
		for (int x = SFEngine.TOTAL_INTERCEPTORS -1; x<SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS -1; x++){
			SFEnemy interceptor;
			if (x>=(SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS) / 2 ){
				interceptor = new SFEnemy(SFEngine.TYPE_SCOUT, SFEngine.ATTACK_RIGHT);
			}else{
				interceptor = new SFEnemy(SFEngine.TYPE_SCOUT, SFEngine.ATTACK_LEFT);
			}
			enemies[x] = interceptor;
		}
	}
	private void initializeWarships(){
		for (int x = SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS -1; x<SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS -1; x++){
			SFEnemy interceptor = new SFEnemy(SFEngine.TYPE_WARSHIP, SFEngine.ATTACK_RANDOM);
			enemies[x] = interceptor;
		}
	}
	private void moveEnemy(GL10 gl){
		for (int x = 0; x < SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS - 1; x++){
			if (!enemies[x].isDestroyed){
				Random randomPos = new Random();
				switch (enemies[x].enemyType){
				case SFEngine.TYPE_INTERCEPTOR:
					if (enemies[x].posY < 0){
						enemies[x].posY = (randomPos.nextFloat() * 4) + 4;
						enemies[x].posX = randomPos.nextFloat() * 3;
						enemies[x].isLockedOn = false;
						enemies[x].lockOnPosX = 0;
					}	
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(.25f, .25f, 1f);
		
					if (enemies[x].posY >= 3){
						enemies[x].posY -= SFEngine.INTERCEPTOR_SPEED;
					}else{
						if (!enemies[x].isLockedOn){
							enemies[x].lockOnPosX = SFEngine.playerBankPosX;
							enemies[x].isLockedOn = true;
							enemies[x].incrementXToTarget =  (float) ((enemies[x].lockOnPosX - enemies[x].posX )/ (enemies[x].posY / (SFEngine.INTERCEPTOR_SPEED  * 4)));
						}
						enemies[x].posY -= (SFEngine.INTERCEPTOR_SPEED  * 4);
						enemies[x].posX += enemies[x].incrementXToTarget; 
	
					}
						gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
						gl.glMatrixMode(GL10.GL_TEXTURE);
						gl.glLoadIdentity();
						gl.glTranslatef(0.25f, .25f , 0.0f);					
					enemies[x].draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();
			
					break;
				case SFEngine.TYPE_SCOUT:
					if (enemies[x].posY <= 0){
						enemies[x].posY = (randomPos.nextFloat() * 4) + 4;
						enemies[x].isLockedOn = false;
						enemies[x].posT = SFEngine.SCOUT_SPEED;
						enemies[x].lockOnPosX = enemies[x].getNextScoutX();
						enemies[x].lockOnPosY = enemies[x].getNextScoutY();
						if(enemies[x].attackDirection == SFEngine.ATTACK_LEFT){
							enemies[x].posX = 0;
						}else{
							enemies[x].posX = 3f;
						}
					}	
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(.25f, .25f, 1f);
		
					if (enemies[x].posY >= 2.75f){
						enemies[x].posY -= SFEngine.SCOUT_SPEED;
			
					}else{
						enemies[x].posX = enemies[x].getNextScoutX();
						enemies[x].posY = enemies[x].getNextScoutY();
						enemies[x].posT += SFEngine.SCOUT_SPEED;
										
	
					}
						gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
						gl.glMatrixMode(GL10.GL_TEXTURE);
						gl.glLoadIdentity();
						gl.glTranslatef(0.50f, .25f , 0.0f);					
					enemies[x].draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();
					
					break;
				case SFEngine.TYPE_WARSHIP:
					if (enemies[x].posY < 0){
						enemies[x].posY = (randomPos.nextFloat() * 4) + 4;
						enemies[x].posX = randomPos.nextFloat() * 3;
						enemies[x].isLockedOn = false;
						enemies[x].lockOnPosX = 0;
					}	
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(.25f, .25f, 1f);
		
					if (enemies[x].posY >= 3){
						enemies[x].posY -= SFEngine.WARSHIP_SPEED;
			
					}else{
						if (!enemies[x].isLockedOn){
							enemies[x].lockOnPosX = randomPos.nextFloat() * 3;
							enemies[x].isLockedOn = true;
							enemies[x].incrementXToTarget =  (float) ((enemies[x].lockOnPosX - enemies[x].posX )/ (enemies[x].posY / (SFEngine.WARSHIP_SPEED  * 4)));
						}
						enemies[x].posY -= (SFEngine.WARSHIP_SPEED  * 2);
						enemies[x].posX += enemies[x].incrementXToTarget; 
	
					}
						gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
						gl.glMatrixMode(GL10.GL_TEXTURE);
						gl.glLoadIdentity();
						gl.glTranslatef(0.75f, .25f , 0.0f);					
					enemies[x].draw(gl,spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();
					
					break;
			
				}
			
			}	
		}
			
	}
		
	
	private void movePlayer1(GL10 gl){

		switch (SFEngine.playerFlightAction){
		case SFEngine.PLAYER_BANK_LEFT_1:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.25f, .25f, 1f);			
			if (goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI && SFEngine.playerBankPosX > 0){
				SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.75f,0.0f, 0.0f); 
				goodGuyBankFrames += 1;
			}else if (goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI && SFEngine.playerBankPosX > 0){
				SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f,0.25f, 0.0f); 
			}else{
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f,0.0f, 0.0f); 	
			}
			player1.draw(gl,spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			
			break;
		case SFEngine.PLAYER_BANK_RIGHT_1:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.25f, .25f, 1f);			
			if (goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI && SFEngine.playerBankPosX < 3){
				SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.25f,0.0f, 0.0f);
				goodGuyBankFrames += 1;
			}else if (goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI && SFEngine.playerBankPosX < 3){
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
			    gl.glTranslatef(0.50f,0.0f, 0.0f);
				SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
			}else{
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
			    gl.glTranslatef(0.0f,0.0f, 0.0f); 
			}
			player1.draw(gl,spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			break;
		case SFEngine.PLAYER_RELEASE:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
		 	gl.glScalef(.25f, .25f, 1f);
		 	gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
		    gl.glTranslatef(0.0f,0.0f, 0.0f); 
		    goodGuyBankFrames = 0;
		    player1.draw(gl,spriteSheets);
		    gl.glPopMatrix();
		    gl.glLoadIdentity();
			break;
		default:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
		 	gl.glScalef(.25f, .25f, 1f);
		 	gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
		    gl.glTranslatef(0.0f,0.0f, 0.0f); 
		    player1.draw(gl,spriteSheets);
		    gl.glPopMatrix();
		    gl.glLoadIdentity();
			break;
		}

	}
	private void scrollBackground1(GL10 gl){
		if (bgScroll1 == Float.MAX_VALUE){
			bgScroll1 = 0f;
		}
		
		
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    gl.glPushMatrix();
	    gl.glScalef(1f, 1f, 1f);
	    gl.glTranslatef(0f, 0f, 0f);
    
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
	    gl.glTranslatef(0.0f,bgScroll1, 0.0f); 
	   
	    background.draw(gl);
	    gl.glPopMatrix();
	    bgScroll1 +=  SFEngine.SCROLL_BACKGROUND_1;
	    gl.glLoadIdentity();


	}
	private void scrollBackground2(GL10 gl){
		if (bgScroll2 == Float.MAX_VALUE){
			bgScroll2 = 0f;
		}
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    gl.glPushMatrix();
	    gl.glScalef(.5f, 1f, 1f);
	    gl.glTranslatef(1.5f, 0f, 0f);

	    gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
	    gl.glTranslatef( 0.0f,bgScroll2, 0.0f); 
	   
	    background2.draw(gl);
	    gl.glPopMatrix();
	    bgScroll2 +=  SFEngine.SCROLL_BACKGROUND_2;  
	    gl.glLoadIdentity();
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
		gl.glViewport(0, 0, width,height);
		 
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		initializeInterceptors();
		initializeScouts();
		initializeWarships();
		textureLoader = new SFTextures(gl);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.CHARACTER_SHEET, SFEngine.context, 1); 
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		background.loadTexture(gl,SFEngine.BACKGROUND_LAYER_ONE, SFEngine.context);
		background2.loadTexture(gl,SFEngine.BACKGROUND_LAYER_TWO, SFEngine.context);
	}
	
}
