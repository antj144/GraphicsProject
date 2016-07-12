/**
Anthony Jansen ID 1251960
*/
package GA3;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;


public class Lighting {
	//variables declared 
	public GLU glu;
	public GL gl;
	public GLUquadric quadric;
	float globalAmbient[] = { 0.4f, 0.4f, 0.4f, 1 }; // global light properties

	public float[] lightPosition = { 5.0f, 5.0f, 5.0f, 1.0f }; // point light
	public float[] ambientLight = { 0, 0, 0, 1 };
	public float[] diffuseLight = { 1, 1, 1, 1 };
	public float[] specularLight = { 1, 1, 1, 1 };

	// directional light
	public float[] directPosition = { 5.0f, 0.0f, -10.0f, 0.0f };
	public float[] directAmbient = { 0.8f, 0.8f, 0.8f, 0 };
	public float[] directDiffuse = { 0.8f, 0.8f, 0.8f, 0 };
	public float[] directSpecular = { 0.8f, 0.8f, 0.8f, 0 };

	// night light
	public float[] nightPosition = { 0.0f, 0.0f, 0.0f, 0.0f };
	public float[] nightAmbient = { 0.05f, 0.05f, 0.05f, 1 };
	public float[] nightDiffuse = { 0.05f, 0.05f, 0.05f, 1 };
	public float[] nightSpecular = { 0.05f, 0.05f, 0.05f, 1 };

	// light and spot light, light settings
	// spot light setiing
	float spot_ambient[] = { 1.0f, 1f, 1.0f, 1.0f };
	float spot_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float spot_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float spot_position[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	float spot_direction[] = { 0.0f, -1.0f, 0.0f };
	float spot_angle = 30.0f;

	public Lighting(GL gl) {
		glu = new GLU();
		this.gl = gl;
	}

	// Direct Light Method
	public void directLight() {
		gl.glEnable(GL.GL_LIGHT1);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, directAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, directDiffuse, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, directSpecular, 0);
		// position the light
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, directPosition, 0);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glDisable(GL.GL_LIGHT2);
		gl.glDisable(GL.GL_LIGHT3);
	}

	// Night Light Method
	public void nightLight() {
		gl.glEnable(GL.GL_LIGHT2);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, nightAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, nightDiffuse, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, nightSpecular, 0);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, nightPosition, 0);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glDisable(GL.GL_LIGHT1);
	}

	// Spot Light Method
	public void spotLight(Helicopter heli) {
		gl.glEnable(GL.GL_LIGHT3);
		// Helicopters location to follow helicopter
		spot_position[0] = heli.location[0];
		spot_position[1] = heli.location[1];
		spot_position[2] = heli.location[2];
		enable();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_SMOOTH);
		// set spot light colors
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_AMBIENT, spot_ambient, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_DIFFUSE, spot_diffuse, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPECULAR, spot_specular, 0);
		// spot light position
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, spot_position, 0);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPOT_DIRECTION, spot_direction, 0);
		gl.glLightf(GL.GL_LIGHT3, GL.GL_SPOT_CUTOFF, (float) spot_angle);
		// "smoothing" the border of the lightcone
		// change this for effect
		gl.glLighti(GL.GL_LIGHT3, GL.GL_SPOT_EXPONENT, 10);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glDisable(GL.GL_SMOOTH);
		gl.glDisable(GL.GL_LIGHT1);
	}

	// enable lighting
	public void enable() {
		gl.glEnable(GL.GL_LIGHTING);
	}

	// original light draw showing light
	public void draw(GL gl) {
		quadric = glu.gluNewQuadric();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_LIGHTING);
		gl.glColor4d(1, 1, 1, 1);
		gl.glTranslated(lightPosition[0], lightPosition[1], lightPosition[2]);
		glu.gluSphere(quadric, 0.1, 16, 8); // draw sphere translated to light
											// position
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, -100, 0);
		gl.glEnd();
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPopMatrix();
	}
}
