/**
Anthony Jansen ID 1251960
*/
package GA3;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Tree {
	// variable declared
	private GLU glu;
	private GLUquadric quadric;
	public double[] treeLocation = { 0, 0, 0 };
	private static double trunkBase = 0.5;
	private static double trunkHeight = 10;
	private static double leavesBase = 2;
	private static double leavesTop = 0;
	private static double leavesHeight = 6;

	// Emerald Material
	private float emeraldSpecular[] = { 0.633f, 0.727811f, 0.633f, 1f };
	private float emeraldAmbient[] = { 0.0215f, 0.1745f, 0.0215f, 1f };
	private float emeraldDiffuse[] = { 0.07568f, 0.61424f, 0.07568f, 1f };
	// brown Material
	private float brownSpecular[] = { 0.8f, 0.35f, 0.02f, 1f };
	private float brownAmbient[] = { 0.8f, 0.35f, 0.02f, 1f };
	private float brownDiffuse[] = { 0.8f, 0.35f, 0.02f, 1f };

	// tree method takes tree location into forest class
	public Tree(double positionx, double positiony, double positionz) {
		glu = new GLU();
		quadric = glu.gluNewQuadric();

		treeLocation[0] = positionx;
		treeLocation[1] = positiony;
		treeLocation[2] = positionz;
	}

	// draws a single tree with materials
	public void drawTree(GL gl) {
		gl.glDisable(GL.GL_COLOR_MATERIAL);
		// tree trunk
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, brownSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, brownDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, brownAmbient, 0);
		
		gl.glPushMatrix();
		gl.glTranslated(treeLocation[0], treeLocation[1], treeLocation[2]);
		gl.glPushMatrix();
		gl.glTranslated(1, 0, 1);
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, trunkBase, trunkBase - (0.2 * trunkBase), trunkHeight, 15, 10);
		gl.glPopMatrix();
		//tree leaves
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, emeraldSpecular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, emeraldDiffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, emeraldAmbient, 0);
	
		gl.glPushMatrix();
		gl.glPushMatrix();
		gl.glTranslated(1, 10, 1);
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, leavesBase, leavesTop, leavesHeight, 15, 10);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glPushMatrix();
		gl.glTranslated(1, 13, 1);
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, leavesBase, leavesTop, leavesHeight, 15, 10);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glPushMatrix();
		gl.glTranslated(1, 15, 1);
		gl.glRotated(-90, 1, 0, 0);
		glu.gluCylinder(quadric, leavesBase, leavesTop, leavesHeight, 15, 10);
		gl.glPopMatrix();

		gl.glPopMatrix();
		gl.glEnable(GL.GL_COLOR_MATERIAL);

	}

}
