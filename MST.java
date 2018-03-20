public class MST {
	private int[][] cost;
	private int[]	terminals;

	public void MST(int[][] cost, int[] terminals) {
		this.cost      = cost;
		this.terminals = terminals;
	}

	public void delete_leaves() {
		int int max = (int) (Integer.MAX_VALUE / 2 - 1);
		
		int adjacent_number;
		// implement while loop that runs until total number of leaves is zero
		// also check if it is not a terminal
		for (int i = 0; i < this.cost.length; i++) {
			for (int j )

		}
	}

	// to deal with upper triangle
	public int cost(int a, int b) {
		return a < b ? this.cost[a][b] : this.cost[b][a];
	}
}