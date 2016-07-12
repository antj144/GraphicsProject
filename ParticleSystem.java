/**
Anthony Jansen ID 1251960
*/
package GA3;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class ParticleSystem {
	// variables declared
	public GL gl;
	private Random location = new Random();
	private ArrayList<Particle> particles;
	GLU glu = new GLU();
	GLUquadric quadric = glu.gluNewQuadric();

	// random rain coordinates & rain drops random x,y and z locations
	public ParticleSystem(GL gl, int rainFall) {
		this.gl = gl;
		particles = new ArrayList<Particle>();
		for (int i = 0; i < rainFall; i++) {
			double x = location.nextDouble() * 80 - 40;
			double y = location.nextDouble() * 50;
			double z = location.nextDouble() * 80 - 40;
			// new rain drops and add rain drops
			Particle rain = new Particle(x, y, z);
			particles.add(rain);
		}
	}

	// draws many line(rain drops) on the environment & animates them all
	public void drawRainFall(GL gl) {// draws the rain
		for (Particle p : particles) {
			p.animate();
			p.drawRain(gl);

		}
	}
}
