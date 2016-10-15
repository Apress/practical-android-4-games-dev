package com.proandroidgames;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class BHCorridor {

	   private FloatBuffer vertexBuffer;
	   private FloatBuffer textureBuffer;
	   
	   private int[] textures = new int[1];

	   private float vertices[] = {
	                   -2.0f, 0.0f, 0.0f, 
	                   1.0f, 0.0f, 0.0f,
	                   -2.0f, 1.0f, 0.0f,  
	                   1.0f, 1.0f, 0.0f,
	                   
	                   1.0f, 0.0f, 0.0f,
	                   1.0f, 0.0f, 5.0f,
	                   1.0f, 1.0f, 0.0f,
	                   1.0f, 1.0f, 5.0f, 
	                   
	                   0.0f, 0.0f, 1.0f,
	                   0.0f, 0.0f, 5.0f,
	                   0.0f, 1.0f, 1.0f,
	                   0.0f, 1.0f, 5.0f,
	                   
	                   -2.0f, 0.0f, 1.0f, 
	                   0.0f, 0.0f, 1.0f,
	                   -2.0f, 1.0f, 1.0f,  
	                   0.0f, 1.0f, 1.0f,
	                   };
	   
	    private float texture[] = {          
                -1.0f, 0.0f,
                1.0f, 0f, 
                -1f, 1f,
                1f, 1.0f,
                
                -1.0f, 0.0f,
                1.0f, 0f, 
                -1f, 1f,
                1f, 1.0f,
                
                -1.0f, 0.0f,
                1.0f, 0f, 
                -1f, 1f,
                1f, 1.0f,
                
                -1.0f, 0.0f,
                1.0f, 0f, 
                -1f, 1f,
                1f, 1.0f,
                
               
                };

	   public BHCorridor() {
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
	   }

	    public void draw(GL10 gl) {
	    	
	     gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]); 
	     gl.glFrontFace(GL10.GL_CCW);
	    	
	     gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	     gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

	      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	      
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0,4);      
	      
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4,4);  
	      
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8,4); 
	      
	      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12,4); 
	      
	      gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	      gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	      gl.glDisable(GL10.GL_CULL_FACE);
	   }
	    
	    public void loadTexture(GL10 gl,int texture, Context context) {
		      InputStream imagestream = context.getResources().openRawResource(texture);
		      Bitmap bitmap = null;
		      try {
		        
		    	  bitmap = BitmapFactory.decodeStream(imagestream);

		      }catch(Exception e){
		    	  
		      }finally {
		        try {
		        	 imagestream.close();
		        	 imagestream = null;
		         } catch (IOException e) {
		         }
		      }

		      gl.glGenTextures(1, textures, 0);
		      gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		      
		      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		      
		      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		      
		      bitmap.recycle();
		   }
		}

	   
