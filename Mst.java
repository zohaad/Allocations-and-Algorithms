import java.util.*;

public class Mst {

	private Integer[][] cost;
	private Integer[][] next;
	private ArrayList<int[]> A; // tree containing edges
	private DisjointSets S; // disjoint sets

	public Mst(Integer[][] cost) {
		//intialization
		this.cost = cost;

		// Floyd-Warshall's algorithm

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

		// Kruskal's algorithm
		this.S = new DisjointSets();
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
    		// Status updates:
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

	public ArrayList<Integer> path(int u, int v) { // Wikipedia

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

	public ArrayList<int[]> kruskal() {
		for (int v = 0; v < this.cost.length; v++) { // for every vertex ..
			if (this.cost[v] != null) { // .. that exists ..
				this.S.add(v); // make a disjoint set
			}
		}

		ArrayList<int[]> ordered_edges = ordered_edges();

		this.A = new ArrayList<int[]>();

		for (int[] e : ordered_edges) { // e for edge
			if (this.S.find(e[0]) != this.S.find(e[1])) {
				this.A.add(e);
				this.S.union(e[0], e[1]); // adds the set of e[1] to the set of e[0] and deletes the set of e[1]
			}
		}
		return this.A;
	}

	public ArrayList<int[]> ordered_edges() { // orderes edges increasing
		ArrayList<int[]> edges = new ArrayList<>();

		
		for (int i = 0; i < this.cost.length; i++) { // go over cost array
			if (this.cost[i] == null) { // skip if vertex i doesn't exist
				continue;
			}
			for (int j = 0; j < i; j++) { // don't consider this.cost[i][i]
				if (this.cost[i][j] != null) { // and cost i-j is defined
					int[] edge = new int[3];
					edge[0] = i;
					edge[1] = j;
					edge[2] = this.cost[i][j];
					edges.add(edge);
				}
			}
		}

		// sorting by cost, uses Java 8
		edges.sort(Comparator.comparingInt(a -> a[2])); 
		
		// TODO ?: remove cost
		return edges;
	}
}
