import java.util.*;

public class Leaves {
	public static Integer[][] remove(Integer[][] matrix, ArrayList<Integer> terminals) {

		ArrayList<Integer> deleted = new ArrayList<>();
		int old_deleted_size;
		do {
			old_deleted_size = deleted.size();
			for (int v = 0; v < matrix.length; v++) { // for every vertex...
				if (!terminal_check(v, terminals) && matrix[v] != null) { // must not be a terminal or already deleted
					
					int adjacency_number = 0;
					for (int j = 0; j < matrix[v].length; j++) { // check row
						if (matrix[v][j] != null) {
							adjacency_number++;
						}
					}
					for (int i = v; i < matrix.length; i++) { // check column
						if (matrix[i] != null && matrix[i][v] != null) { // if the ith row is not already null
							adjacency_number++;
						}
					}
					System.out.println("v: " + v);
					if (adjacency_number == 1) {
						
						for (int i = v; i < matrix.length; i++) { // delete column
							if (matrix[i] != null) { // if the ith row is not already null
								matrix[i][v] = null;
							}
						}
						matrix[v] = null; // delete row
						deleted.add(v);
					}
				}
			}
		} while (old_deleted_size != deleted.size());
		System.out.println("Deleted: " + Arrays.deepToString(deleted.toArray()));

		return matrix;
	}

	public static boolean terminal_check (int v, ArrayList<Integer> terminals) {
		for (int i : terminals) {
			if (i == v) {
				return true;
			}
		}
		return false;
	}
}