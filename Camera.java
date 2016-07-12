/**
Anthony Jansen ID 1251960
*/
package GA3;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 * Basic Camera class 
 * 
 * NOTE:
 * You will need to implement the stub methods 
 * and add any additional methods that you need
 * to implement the requiredcamera functionality.
 * 
 * @author Jacqueline Whalley
 *
 */
public class Camera {
	
	private static final double FOV = 45;
	
	
	 double windowWidth      = 1;
	 double windowHeight     = 1;
	 double distance = 5;
	 
	 float[] lookAt = {0,0,0}; //what the camera is looking at
	 private float[] eye = {0,0,0}; //where the camera is located need this to move the camera's position!!
	 
	
	 

	public void draw(GL gl){
		// set up projection first
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 100);
        // set up the camera position and orientation
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(eye[0], eye[1], eye[2], //eye
                	     lookAt[0], lookAt[1], lookAt[2], // looking at 
                         0.0, 1.0, 0.0); // up   
	}
	
	/**
     * Sets up the lookAt point - could be a specified object's location
     * @param x X coordinate of the lookAt point
     * @param y Y coordinate of the lookAt point
     * @param z Z coordinate of the lookAt point
     */
    public void setLookAt(double x, double y, double z) {
       lookAt[0] = (float) x;
       lookAt[1]= (float) y;
       lookAt[2] = (float) z;
    }
    public void setEye(float x, float y, float z){   	
    	eye[0] = x;
    	eye[1] = y;
    	eye[2] = z;	 	
  
    }
 
	
	 /**
     * Passes a new window size to the camera.
     * This method should be called from the <code>reshape()</code> method
     * of the main program.
     *
     * @param width the new window width in pixels
     * @param height the new window height in pixels
     */
    public void newWindowSize(int width, int height) {
    	this.windowHeight = height;
		this.windowWidth = width;
    }

}

