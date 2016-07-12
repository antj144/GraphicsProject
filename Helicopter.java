/**
Anthony Jansen ID 1251960
*/
package GA3;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Helicopter {
	// Variables declared
	private GL gl;
	private GLU glu;
	private GLUquadric quadric;
	public float[] location = { 0, 2, 0 };
	private double rotate;
	public double heading = 0;
	private double distance = 0.2;
	private double speed = 4;
	double start = System.currentTimeMillis();
	private boolean takeOff = false;
	private boolean startEngine = false;
	private double tick;
	private double pitch = 0;
	private double roll = 0;
	public boolean pitchReturn = false;
	// Polished Copper
	private float copperSpecular[] = { 0.580594f, 0.223257f, 0.0695701f, 1.0f };
	private float copperAmbient[] = { 0.2295f, 0.08825f, 0.0275f, 1.0f };
	private float copperDiffuse[] = { 0.7038f, 0.27048f, 0.0828f, 1.0f };
	private float copperShine = 12.8f;
	// Black Plastic Material
	private float blackSpecular[] = { 0.50f, 0.50f, 0.50f, 1.0f };
	private float blackAmbient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	private float blackDiffuse[] = { 0.01f, 0.01f, 0.01f, 1.0f };
	private float blackShine = 32.0f;
	// Polished Gold
	private float goldSpecular[] = { 0.797357f, 0.723991f, 0.208006f, 1.0f };
	private float goldAmbient[] = { 0.24725f, 0.2245f, 0.0645f, 1.0f };
	private float goldDiffuse[] = { 0.34615f, 0.3143f, 0.0903f, 1.0f };
	private float goldShine = 83.2f;

	public Helicopter() {
		glu = new GLU();
		quadric = glu.gluNewQuadric();
	}

	// Draws the helicopter
	public void draw(GL gl) {
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, copperSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, copperDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, copperAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, copperShine);

		gl.glPushMatrix();
		gl.glTranslated(location[0], location[1], location[2]);
		gl.glRotated(heading, 0, 1, 0);
		gl.glRotated(pitch, 1, 0, 0);
		gl.glRotated(roll, 0, 0, 1);

		// Body
		gl.glPushMatrix();

		glu.gluSphere(quadric, 1.5, 16, 20);
		gl.glPopMatrix();
		// boom
		gl.glPushMatrix();
		glu.gluCylinder(quadric, 0.4, 0.2, 4, (int) 6.2, 7);
		gl.glPopMatrix();
		// tail
		gl.glPushMatrix();
		gl.glTranslated(0, 0.1, 4.4);
		gl.glRotated(90, 0, 90, 0);

		glu.gluDisk(quadric, 0.4, 0.7, 14, 20);
		gl.glPopMatrix();
		// top cylinder
		gl.glPushMatrix();
		gl.glTranslated(0, 1, 0);
		gl.glRotated(270, 1, 0, 0);

		glu.gluCylinder(quadric, 0.5, 0.2, 1, 15, 15);
		gl.glPopMatrix();
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, goldSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, goldDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, goldAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, goldShine);

		// Skids
		gl.glPushMatrix();
		gl.glTranslated(0.58, -1.2, -0.5);
		gl.glRotated(150, 1, -0.5, 1);
		glu.gluCylinder(quadric, 0.1, 0.1, 0.7, 20, 15);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(0.58, -1.2, 0.5);
		gl.glRotated(150, 1, -0.5, 1);
		glu.gluCylinder(quadric, 0.1, 0.1, 0.7, 20, 15);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(-0.58, -1.2, -0.5);
		gl.glRotated(150, 1, 0.5, -1);
		glu.gluCylinder(quadric, 0.1, 0.1, 0.7, 20, 15);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(-0.58, -1.2, 0.5);
		gl.glRotated(150, 1, 0.5, -1);
		glu.gluCylinder(quadric, 0.1, 0.1, 0.7, 20, 15);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(1.1, -1.8, -1);
		gl.glRotated(90, 0, 0, 1);
		glu.gluCylinder(quadric, 0.15, 0.15, 2, 20, 15);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(-1.1, -1.8, -1);
		gl.glRotated(90, 0, 0, 1);
		glu.gluCylinder(quadric, 0.15, 0.15, 2, 20, 15);
		gl.glPopMatrix();
		// top disk/rotors
		gl.glPushMatrix();
		gl.glTranslated(0, 0, 0);
		gl.glRotated(rotate, 0, 1, 0);
		drawMainRotors(gl);
		gl.glPopMatrix();

		// tail rotors
		gl.glPushMatrix();
		gl.glTranslated(0, 0.1, 4.45);
		gl.glRotated(rotate, 1, 0, 0);
		drawTailRotors(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
		rotateRotors();
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	// Draws the top and the rotors of the helicopter
	public void drawMainRotors(GL gl) {
		gl.glDisable(GL.GL_COLOR_MATERIAL);

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, copperSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, copperDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, copperAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, copperShine);

		gl.glPushMatrix();
		gl.glTranslated(0, 2, 0);
		gl.glRotated(90, 1, 0, 0);
		glu.gluDisk(quadric, 0, 0.4, 20, 30);
		gl.glPopMatrix();

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, blackSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, blackDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, blackAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, blackShine);

		// Rotors
		gl.glPushMatrix();
		gl.glTranslated(1.35, 2, 0);
		gl.glRotated(180, 0, 1, 1);

		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(1f, 0.2f, 0f);
		gl.glVertex3f(1f, -0.2f, 0f);
		gl.glVertex3f(-1f, -0.2f, 0f);
		gl.glVertex3f(-1f, 0.2f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(-1.35, 2, 0);
		gl.glRotated(180, 0, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(1f, 0.2f, 0f);
		gl.glVertex3f(1f, -0.2f, 0f);
		gl.glVertex3f(-1f, -0.2f, 0f);
		gl.glVertex3f(-1f, 0.2f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(0, 2, 1.3);
		gl.glRotated(240, 1, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(1f, 0.2f, 0f);
		gl.glVertex3f(1f, -0.2f, 0f);
		gl.glVertex3f(-1f, -0.2f, 0f);
		gl.glVertex3f(-1f, 0.2f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(0, 2, -1.3);
		gl.glRotated(240, 1, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(1f, 0.2f, 0f);
		gl.glVertex3f(1f, -0.2f, 0f);
		gl.glVertex3f(-1f, -0.2f, 0f);
		gl.glVertex3f(-1f, 0.2f, 0f);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}

	// Draws the tail and the rotors of the helicopter
	public void drawTailRotors(GL gl) {
		gl.glDisable(GL.GL_COLOR_MATERIAL);

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, copperSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, copperDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, copperAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, copperShine);

		gl.glPushMatrix();
		glu.gluSphere(quadric, 0.07, 4, 10);
		gl.glPopMatrix();

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, blackSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, blackDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, blackAmbient, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, blackShine);

		gl.glPushMatrix();
		gl.glColor3d(0, 0, 0.);
		gl.glRotated(180, 1, 0, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(0.45f, 0.05f, 0f);
		gl.glVertex3f(0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, 0.05f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor3d(0, 0, 0);

		gl.glRotated(180, 1, 0, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(0.045f, 0.05f, 0f);
		gl.glVertex3f(0.045f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, 0.05f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor3d(0, 0, 0);

		gl.glRotated(120, 1, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(0.45f, 0.05f, 0f);
		gl.glVertex3f(0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, 0.05f, 0f);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor3d(0, 0, 0);
		gl.glRotated(120, 1, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(0.45f, 0.05f, 0f);
		gl.glVertex3f(0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, -0.05f, 0f);
		gl.glVertex3f(-0.45f, 0.05f, 0f);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}

	// calculate distance variable in increment
	public void getDistance(double time) {
		distance = speed * time;
		distance++;

	}

	// Speed up and slow Down helicopter rotators
	public void rotateRotors() {
		// System.out.println("rotate:" + rotate);
		// System.out.println(tick);

		if (location[1] > 1.9 && rotate < 200 && !takeOff && startEngine) {
			// for (speed = 0; speed <= 40; speed++) {
			tick = (System.currentTimeMillis() - start) / 10000;
			speed = 20;
			rotate += (speed * tick) % 360;
		} else if (rotate >= 200) {
			this.takeOff = true;
			// rotate += speed * tick;
		} else if (location[1] <= 1.9) {
			while (speed >= 0) {
				rotate -= speed * tick;
				speed--;
			}
			if (rotate <= 0) {
				this.takeOff = false;
				this.startEngine = false;
			}
		}
	}

	// move helicopter up
	public void moveUp() {
		this.startEngine = true;
		if (this.takeOff) {
			location[1] += distance;
		}
	}

	// move helicopter down and not below ground
	public void moveDown() {
		this.takeOff = false;
		location[1] -= distance;
		if (location[1] <= 1.8) {
			location[1] = (float) 1.8;
		}
	}

	// move helicopter left on axis
	public void turnLeft() {
		heading += 2;
	}

	// move helicopter right on axis
	public void turnRight() {
		heading -= 2;
	}

	public void stafeLeft() {
		if (roll >= -10) {
			roll = 10;
			pitch = 0;
		}
		location[0] -= (float) (Math.sin(Math.toRadians(heading + 90)) * distance);
		location[2] -= (float) (Math.cos(Math.toRadians(heading + 90)) * distance);
	}

	public void stafeRight() {
		if (roll >= 0) {
			roll = -10;
			pitch = 0;
		}
		location[0] += (float) (Math.sin(Math.toRadians(heading + 90)) * distance);
		location[2] += (float) (Math.cos(Math.toRadians(heading + 90)) * distance);
	}

	// move helicopter forward
	public void moveForward() {
		if (pitch >= 0 || pitch <= 10) {
			pitch = -10;
			roll = 0;
			location[0] -= (float) (Math.sin(Math.toRadians(heading)) * distance);
			location[2] -= (float) (Math.cos(Math.toRadians(heading)) * distance);
		}
	}

	// move helicopter back
	public void moveBack() {
		if (pitch >= 0 || pitch <= -10) {
			pitch = 10;
			roll = 0;
		}
		location[0] += (float) (Math.sin(Math.toRadians(heading)) * distance);
		location[2] += (float) (Math.cos(Math.toRadians(heading)) * distance);
	}
}
