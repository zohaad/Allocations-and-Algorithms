import java.util.*;

public class Mst {

	private Integer[][] cost;
	private Integer[][] next;

	public Mst(Integer[][] cost) {
		//intialization
		this.cost = cost;
		this.next = new Integer[this.cost.length][];
		for (int i = 0; i < this.next.length; i++) {
			this.next[i] = this.cost[i] == null ? null : new Integer[this.cost.length];
			// initialization
			if (this.next[i] != null) {
				for (int j = 0; j < this.cost.length; j++) {
					this.next[i][j] = j;
				}
			}
		}
	}


    public Integer[][] floyd_warshall() {
    	outerloop:
    	for (int k = 0; k < this.cost.length; k++) {
    		for (int i = 0; i < this.cost.length; i++) {
    			for (int j = 0; j < i + 1; j++) { // we can skip half the matrix because it is symmetrical
    				if (cost(i, j) > cost(i, k) + cost(k, j)) {

    					// don't want to mess with loop variables, introduce x and y
    					int x = i;
    					int y = j;

    					if (y > x) { // determine if we need to swap
    						int save = x;
    						x = y;
    						y = save;
    					}
    					this.cost[x][y] = cost(x, k) + cost(k, y); // we don't mess with k here, look at cost() method below
    					this.next[x][y] = this.next[x][k]; // let's hope this works
    				}
    			}
    		}
    		// System.out.println(k + "/" + (this.cost.length - 1));
    	}
    	System.out.println("Floyd-Warshall done!");
    	// maybe make it void?
    	return this.cost;
    }

    public Integer[][] return_next() {
    	return this.next;
    }

	
	public Integer cost(int a, int b) {
		int x = a;
		int y = b;
		int max = Integer.MAX_VALUE / 2 - 1;
		if (y > x) { // to deal with lower triangle
			int save = x;
			x = y;
			y = save;
		}
		if (this.cost[x] == null || this.cost[x][y] == null) { // first check for null, else NullPointerException
			return max;
		}

		return this.cost[x][y];
	}

	public ArrayList<Integer> path(int i, int j) { // Wikipedia
		int u = i;
		int v = j;
		// if (v > u) {
		// 	int save = u;
		// 	u = v;
		// 	v = save;
		// }
		// algorithm
		ArrayList<Integer> path = new ArrayList<>();

		if (this.next[u] == null || this.next[u][v] == null) {
			return path; // empty arraylist
		}
		path.add(u);
		while (u != v) {

			u = v > u ? this.next[v][u] : this.next[u][v];
			path.add(u);
		}
		return path;
	}

	public static void print_ArrayList(ArrayList<Integer> x) {
		System.out.println(Arrays.deepToString(x.toArray()));
	}
}
