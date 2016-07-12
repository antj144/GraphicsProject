package GA3;

import java.util.Random;

import javax.media.opengl.GL;

public class Particle {

	// Variables declared
	public double[] rainLocation = { 0, 0, 0 };
	private Random location = new Random();
	private static double dy = 0.5;
	private static double height = 30;
	// water material
	private float[] blueAmbient = { 0.0f, 0.0f, 0.4f, 1f };
	private float[] blueDiffuse = { 0.0f, 0.0f, 0.4f, 1f };
	private float[] blueSpecular = { 0.0f, 0.0f, 0.4f, 1f };
	private float blueShine = 83.2f;

	// rain locations set and declared into particle system class
	public Particle(double x, double y, double z) { // double dx, double dy) {
		rainLocation[0] = x;
		rainLocation[1] = y;
		rainLocation[2] = z;
	}

	// draw a single rain particle
	public void drawRain(GL gl) {
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, blueSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, blueDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, blueAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, blueShine);
		gl.glPushMatrix();
		gl.glTranslated(rainLocation[0], rainLocation[1], rainLocation[2]);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(rainLocation[0], rainLocation[1]);
		gl.glVertex2d(rainLocation[0], rainLocation[1] + 0.2);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}

	// animates the rain particle and recycles them to random locations
	public void animate() {
		rainLocation[1] -= dy;
		if (rainLocation[1] <= -1) {
			rainLocation[0] = location.nextDouble() * 80 - 40;
			rainLocation[1] = height;
			rainLocation[2] = location.nextDouble() * 80 - 40;
		}

	}
}
