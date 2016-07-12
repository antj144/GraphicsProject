/**
Anthony Jansen ID 1251960
*/
package GA3;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Texturing {
	// variables decleared
	public GL gl;
	public GLU glu;
	public GLUquadric quadric;
	public double scaleTexture = 1.0;
	private Texture[] textures;
	private double width = 5.0;

	public Texturing() {
		glu = new GLU();
		quadric = glu.gluNewQuadric();

	}

	// get pictures from file
	public void getTeture() {
		textures = new Texture[2];
		try {
			textures[0] = TextureIO.newTexture(new File("C:/Users/Anthony/Desktop/2016 Semester 1/Computer Graphics/Assignments/Project/textures/forest-floor-terrain.jpg"),
					true);
			textures[1] = TextureIO.newTexture(new File("C:/Users/Anthony/Desktop/2016 Semester 1/Computer Graphics/Assignments/Project/textures/Sky_dome1.jpg"), true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// enable texture on scene & mini mapping
	public void drawTexture(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_2D);
		// ground texture
		textures[0].isUsingAutoMipmapGeneration();
		// sky texture
		textures[1].isUsingAutoMipmapGeneration();
	}

	// draw texture onto ground plane
	public void drawTexturedPlane(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		textures[0].bind();
		gl.glPushMatrix();
		gl.glTranslated(-110, 0, -110);
		for (float x = 0; x < 220; x += width) {
			for (float z = 0; z < 220; z += width) {
				gl.glBegin(GL.GL_QUADS);
				gl.glNormal3d(x, 1, z);
				gl.glTexCoord2d(0, 0);
				gl.glVertex3d(x, 0, z);
				gl.glNormal3d(x + width, 1, z);
				gl.glTexCoord2d(1, 0);
				gl.glVertex3d(x + width, 0, z);
				gl.glNormal3d(x + width, 1, z + width);
				gl.glTexCoord2d(1, 1);
				gl.glVertex3d(x + width, 0, z + width);
				gl.glNormal3d(x, 1, z + width);
				gl.glTexCoord2d(0, 1);
				gl.glVertex3d(x, 0, z + width);
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}

	// draw texture onto sky dome
	public void drawTexturedSphere(GL gl) {
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		gl.glEnable(GL.GL_TEXTURE_2D);
		textures[1].bind();
		gl.glPushMatrix();
		gl.glColor3d(1, 1, 1);
		gl.glScaled(3.0, 3.0, 3.0);
		gl.glTranslated(0, 0, 0);
		gl.glRotated(180, 0, 1, 0);
		gl.glRotated(-90, 1, 0, 0);
		glu.gluQuadricTexture(quadric, true);
		glu.gluSphere(quadric, 22.5, 40, 20);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_COLOR_MATERIAL);

	}
}
