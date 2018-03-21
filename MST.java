import java.util.*;

public class MST {

	private int[][] cost;
	private int[]	terminals;
	private ArrayList<Integer> leaves;

	public MST(int[][] cost, int[] terminals) {
		this.cost      = cost;
		this.terminals = terminals;
		this.leaves = new ArrayList<>();
	}

	public void delete_leaves() {
		int max = (int) (Integer.MAX_VALUE / 2 - 1);
		
		// implement while loop that runs until total number of leaves is zero

		// for every vertex v, that is not a terminal
		System.out.println("Beginning leaf deletion");
		
		int old_leave_size = -1; // arbitrary value 
		while (this.leaves.size() != old_leave_size) {
			for (int v = 0; v < this.cost.length; v++) {
				// if not a terminal
				if (!terminal_check(v)) {
					// arraylist with adjacent nodes
					ArrayList<Integer> adjacent_to_v = new ArrayList<>();

					for (int i = 0; i < this.cost.length; i++) {
						if (i == v) continue; // don't check if v is adjacent to itself
						if (cost(v, i) < max) { // if edge exists 
							adjacent_to_v.add(i); // add to adjacent arraylist
						}
					}

					if (adjacent_to_v.size() == 1) { // if it's a leaf
						this.leaves.add(v);
						System.out.println(v);
					}
				}
			}
			System.out.println("# leaves deleted: " + this.leaves.size());	
			old_leave_size = this.leaves.size();
		}
	}

	// to deal with upper triangle
	public int cost(int a, int b) {
		return a > b ? this.cost[a][b] : this.cost[b][a];
	}

	public boolean terminal_check(int v) {
		for (int i = 0; i < this.terminals.length; i++) {
			if (this.terminals[i] == v)
				return true;
		}
		return false;
	}
}