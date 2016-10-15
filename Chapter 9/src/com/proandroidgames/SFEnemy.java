package com.proandroidgames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class SFEnemy {

		public float posY = 0f;
		public float posX = 0f;
		public float posT = 0f;
		public float incrementXToTarget = 0f;
		public float incrementYToTarget = 0f;
		public int attackDirection = 0;
		public boolean isDestroyed = false;
		private int damage = 0;
		
		public int enemyType = 0;
		
		public boolean isLockedOn = false;
		public float lockOnPosX = 0f;
		public float lockOnPosY = 0f;

		private Random randomPos = new Random();
		
	   private FloatBuffer vertexBuffer;
	   private FloatBuffer textureBuffer;
	   private ByteBuffer indexBuffer;
	   
	   private float vertices[] = {
	                   0.0f, 0.0f, 0.0f, 
	                   1.0f, 0.0f, 0.0f,  
	                   1.0f, 1.0f, 0.0f,  
	                   0.0f, 1.0f, 0.0f,
	                                 };
	   
	    private float texture[] = {          
	                   0.0f, 0.0f,
	                   0.25f, 0.0f,
	                   0.25f, 0.25f,
	                   0.0f, 0.25f, 
	                                  };
	        
	    private byte indices[] = {
	                   0,1,2, 
	                   0,2,3, 
	                                  };
	    public void applyDamage(){
	    	damage++;
	    	switch(enemyType){
	    	case SFEngine.TYPE_INTERCEPTOR:
	    		if (damage == SFEngine.INTERCEPTOR_SHIELDS){
	    			isDestroyed = true;
	    		}	    		
	    		break;
	    	case SFEngine.TYPE_SCOUT:
	    		if (damage == SFEngine.SCOUT_SHIELDS){
	    			isDestroyed = true;
	    		}
	    		break;
	    	case SFEngine.TYPE_WARSHIP:
	    		if (damage == SFEngine.WARSHIP_SHIELDS){
	    			isDestroyed = true;
	    		}
	    		break;
	    	}
	    }

	   public SFEnemy(int type, int direction) {
		   enemyType = type;
		   attackDirection = direction;
		   posY = (randomPos.nextFloat() * 4) + 4;
		   switch(attackDirection){
		   case SFEngine.ATTACK_LEFT:
			   posX = 0;
			   break;
		   case SFEngine.ATTACK_RANDOM:
			   posX = randomPos.nextFloat() * 3;
			   break;
		   case SFEngine.ATTACK_RIGHT:
			   posX = 3;
			   break;
		   }
		   posT = SFEngine.SCOUT_SPEED;
		   
	      ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	      byteBuf.order(ByteOrder.nativeOrder());
	      vertexBuffer = byteBuf.asFloatBuffer();
	      vertexBuffer.put(vertices);
	      vertexBuffer.position(0);

	      byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
	      byteBuf.order(ByteOrder.nativeOrder());
	      textureBuffer = byteBuf.asFloatBuffer();
	      textureBuffer.put(texture);
	      textureBuffer.position(0);

	      indexBuffer = ByteBuffer.allocateDirect(indices.length);
	      indexBuffer.put(indices);
	      indexBuffer.position(0);
	   }
	   public float getNextScoutX(){
		   if (attackDirection == SFEngine.ATTACK_LEFT){
			   return (float)((SFEngine.BEZIER_X_4*(posT*posT*posT)) + (SFEngine.BEZIER_X_3 * 3 * (posT * posT) * (1-posT)) + (SFEngine.BEZIER_X_2 * 3 * posT * ((1-posT) * (1-posT))) + (SFEngine.BEZIER_X_1 * ((1-posT) * (1-posT) * (1-posT))));
		   }else{
			   return (float)((SFEngine.BEZIER_X_1*(posT*posT*posT)) + (SFEngine.BEZIER_X_2 * 3 * (posT * posT) * (1-posT)) + (SFEngine.BEZIER_X_3 * 3 * posT * ((1-posT) * (1-posT))) + (SFEngine.BEZIER_X_4 * ((1-posT) * (1-posT) * (1-posT))));
		   }
		   
	   }
	   public float getNextScoutY(){
		   return (float)((SFEngine.BEZIER_Y_1*(posT*posT*posT)) + (SFEngine.BEZIER_Y_2 * 3 * (posT * posT) * (1-posT)) + (SFEngine.BEZIER_Y_3 * 3 * posT * ((1-posT) * (1-posT))) + (SFEngine.BEZIER_Y_4 * ((1-posT) * (1-posT) * (1-posT))));
	   }

	   public void draw(GL10 gl, int[] spriteSheet) {
		   gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[0]);
	      
		   gl.glFrontFace(GL10.GL_CCW);
		   gl.glEnable(GL10.GL_CULL_FACE);
		   gl.glCullFace(GL10.GL_BACK);
	           
		   gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		   gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		   gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		   gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
	      
		   gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);      
	      
		   gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		   gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		   gl.glDisable(GL10.GL_CULL_FACE);
	   }

}

