/**
Anthony Jansen ID 1251960
*/

package GA3;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Chopper - yeah.
 * 
 * @author Jacqueline Whalley
 */

import com.sun.opengl.util.FPSAnimator;

public class Heli16Flight implements GLEventListener, KeyListener {

	// scene objects
	private Camera camera;
	private Lighting lights;
	private Origin origin;
	private Helicopter Heli;
	private Texturing tex;
	private Forest forest;
	private ParticleSystem rain;
	// toggle variables
	private boolean top = true;
	private boolean light = true;
	private boolean shower = true;
	// Fog settings
	private boolean fog = true;
	private float fogEnd;
	private float fogDensity;
	// number of trees & rain
	private int numTree = 150;
	private int rainFall = 10000;

	public Heli16Flight() {
		new GLU();

	}

	public static void main(String[] args) {

		Frame frame = new Frame("A2 Starting Code");
		GLCanvas canvas = new GLCanvas();
		// print out the instruction to control the helicopter
		System.out.println("Key mapping:");
		System.out.println("UP/DOWN: Increase or Decrease altitude");
		System.out.println("LEFT/RIGHT: Turn left or right");
		System.out.println("W/S: Move forward or backwards");
		System.out.println("A/D : Stafe left or right");
		System.out.println("F : Fog");
		System.out.println("L : Camera");
		System.out.println("Q : Direct and night/spotlight lights");
		System.out.println("R : Make it Rain");

		Heli16Flight app = new Heli16Flight();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);

		frame.add(canvas);
		frame.setSize(1000, 500);
		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		animator.start();
	}

	// set the classes to be used
	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		// Enable VSync
		gl.setSwapInterval(1);
		// Setup the drawing area and shading mode
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0.1f, 0.1f, 0.6f, 0.8f);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);

		camera = new Camera();
		lights = new Lighting(gl);
		origin = new Origin(gl);
		Heli = new Helicopter();
		tex = new Texturing();
		tex.getTeture();
		forest = new Forest(gl, numTree);
		rain = new ParticleSystem(gl, rainFall);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, (double) width / height, 0.1, 20.0);
	}

	// show what is to be displayed on screen
	@Override
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL.GL_COLOR_MATERIAL);

		camera(top);
		fog(gl, fog);
		lights.enable();

		camera.draw(gl);
		lights.draw(gl);
		lighting(gl, light);
		tex.drawTexturedPlane(gl);
		tex.drawTexture(gl);
		tex.drawTexturedSphere(gl);
		origin.ball(gl);
		origin.centerOrigin(gl);
		gl.glPushMatrix();
		rain(gl, shower);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glPushMatrix();
		Heli.draw(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		forest.drawForest(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		Heli.draw(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}

	// turn rain on and off
	private void rain(GL gl, boolean shower) {
		if (!shower) {
			rain.drawRainFall(gl);
		}
	}

	// switch between lighting options
	private void lighting(GL gl, boolean light) {
		if (!light) {
			lights.nightLight();
			lights.spotLight(Heli);
			gl.glDisable(GL.GL_LIGHT1);
		} else {
			lights.directLight();
			gl.glDisable(GL.GL_LIGHT2);
			gl.glDisable(GL.GL_LIGHT3);
		}
	}

	// switch between camera views
	private void camera(boolean top) {
		if (top) {
			// calculate the camera location based on the helicopter location
			float camX = (float) (Heli.location[0] + Math.sin(Math.toRadians(Heli.heading)) * 10);
			float camZ = (float) (Heli.location[2] + Math.cos(Math.toRadians(Heli.heading)) * 10);
			float camY = Heli.location[1] + 5;
			// set camera
			camera.setEye(camX, camY, camZ);
			camera.setLookAt(Heli.location[0], Heli.location[1], Heli.location[2]);

		} else {
			//Top camera view
			float camX = (float) (Heli.location[0] + Math.sin(Math.toRadians(Heli.heading)));
			float camZ = (float) (Heli.location[2] + Math.cos(Math.toRadians(Heli.heading)));
			float camY = Heli.location[1] + 15;
			// set camera
			camera.setEye(camX, camY, camZ);
			camera.setLookAt(Heli.location[0], Heli.location[1], Heli.location[2]);
		}
	}

	// turn fog on and off
	private void fog(GL gl, boolean fog) {
		// setup fog parameters
		if (!fog) {
			gl.glEnable(GL.GL_FOG);
			float[] fogColour = { 0.3f, 0.3f, 0.3f, 1.0f };
			fogEnd = 3.0f;
			fogDensity = 0.03f;
			gl.glFogf(GL.GL_FOG_DENSITY, fogDensity);
			gl.glFogf(GL.GL_FOG_START, 5.0f);
			gl.glFogf(GL.GL_FOG_END, fogEnd);
			gl.glFogfv(GL.GL_FOG_COLOR, fogColour, 0);
			gl.glFogf(GL.GL_FOG_MODE, GL.GL_EXP2);
		} else {
			gl.glDisable(GL.GL_FOG);
		}
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// methods not used
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// method not used

	}

	// Set the key functions to move Helicopter and toggle features
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_DOWN: {
			Heli.moveDown();
			break;
		}
		case KeyEvent.VK_LEFT: {
			Heli.turnLeft();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			Heli.turnRight();
			break;
		}
		case KeyEvent.VK_W: {
			Heli.moveForward();
			break;
		}
		case KeyEvent.VK_S: {
			Heli.moveBack();
			break;
		}
		case KeyEvent.VK_A: {
			Heli.stafeLeft();
			break;
		}
		case KeyEvent.VK_D: {
			Heli.stafeRight();
			break;
		}
		case KeyEvent.VK_UP: {
			Heli.moveUp();
			break;
		}

		}
		if (key == KeyEvent.VK_L) {
			top = !top;
		}
		if (key == KeyEvent.VK_F) {
			fog = !fog;

		}
		if (key == KeyEvent.VK_Q) {
			light = !light;
		}
		if (key == KeyEvent.VK_R) {
			shower = !shower;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
