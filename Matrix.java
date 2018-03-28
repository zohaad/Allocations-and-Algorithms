// update: obsolete

/* A class to do trianguar matrix manipulation.

   The delete() method works on the fact that deletion
   removes one row and one column. It then shifts
   the index of the original matrix to fill the new one.

   It might help to have System.gc() after the delete() method.
*/

import java.util.*;
public class Matrix {

	/* if we decide to use int arrays
	   advantage: faster
	   disadvantage: lookup becomes hard
	*/
	public static int[][] delete(int[][] matrix, int k, ArrayList<Integer> deleted) {
		int[][] updated_matrix = new int[matrix.length - 1][];

		// variables to represent entries of this.matrix, we don't want to touch i and j
		int x; // row of this.matrix
		int y; // col of this.matrix

		for (int i = 0; i < updated_matrix.length; i++) {
			updated_matrix[i] = new int[i + 1];

			x = i;
			if (i >= k) {
				x++;
			}
			for (int j = 0; j < updated_matrix[i].length; j++) {
				y = j;
				if (i >= k) {
					y++;
				}
				updated_matrix[i][j] = matrix[x][y];
			}
		}

		deleted.add(k);

		return updated_matrix;
	}

	/* if we decide to use Integer arrays
	   advantage: order is preserved, lookup is easy
	   disadvantage: Integer is Object, not primitive, so it's slower
	*/
	public static Integer[][] delete(Integer[][] matrix, int k) {
		// delete column
		for (int i = k; i < matrix.length; i++) {
			matrix[i][k] = null;
		}
		// delete row
		for (int j = 0; j < k; j++) { // not <= k because it is deleted above
			matrix[k][j] = null;
		}
		return matrix;

	}
}