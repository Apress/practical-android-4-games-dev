package com.proandroidgames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class SFGoodGuy {
	public boolean isDestroyed = false;
	private int damage = 0;

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
	    	if (damage == SFEngine.PLAYER_SHIELDS){
	    		isDestroyed = true;
	    	}
	    	
	    }
	   public SFGoodGuy() {
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
