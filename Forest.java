/**
Anthony Jansen ID 1251960
*/
package GA3;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL;

public class Forest {
	// variables declared
	public GL gl;
	private Random location = new Random();
	private ArrayList<Tree> forest;
	private static float y = 0;

	// Iterates the number of trees & created that number of x and z locations
	public Forest(GL gl, int numTrees) {
		this.gl = gl;
		forest = new ArrayList<Tree>();
		for (int i = 0; i < numTrees; i++) {
			double x = location.nextDouble() * 80 - 40;
			double z = location.nextDouble() * 80 - 40;
			// new tree and add trees
			Tree tree = new Tree(x, y, z);
			forest.add(tree);
		}
	}

	// draws many trees on the environment
	public void drawForest(GL gl) {
		for (Tree t : forest) {
			t.drawTree(gl);
		}
	}

}
