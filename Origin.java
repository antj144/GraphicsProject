package GA3;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Origin  {
	
	private GLU glu;
	private GLUquadric quadric;
	public GL gl;
	private double lineWidth;
	private double size;
	
	
	
	public Origin(GL gl){
		glu = new GLU();
		this.gl = gl;
	    lineWidth = 5;
	    size = 10;

	}  
	public void ball(GL gl){
		quadric = glu.gluNewQuadric();
		gl.glColor3d(1, 0.3, 0.7);
		glu.gluQuadricDrawStyle(quadric,GLU.GLU_FILL);
		glu.gluSphere(quadric, 0.3,8,10);
		
	}
	public void centerOrigin(GL gl) {
		gl.glLineWidth((float) lineWidth);
        gl.glBegin(GL.GL_LINES);
            gl.glColor3d(1, 0, 0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(size, 0, 0);
            gl.glColor3d(0, 1, 0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, size, 0);
            gl.glColor3d(0, 0, 1);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, 0, size);
        gl.glEnd();
	}
}

