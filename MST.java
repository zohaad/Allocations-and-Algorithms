import java.util.*;

public class MST {

	private int[][] cost;
	private int[]	terminals;
	private ArrayList<Integer> leaves;
	private ArrayList<Integer>[] pre; //dit werkt niet denk ik, hoe lossen we dit op?

	public MST(int[][] cost, int[] terminals) {
		this.cost      = cost;
		this.terminals = terminals;
		this.leaves = new ArrayList<>();
		this.pre = new ArrayList[cost.length]; //dit werkt niet denk ik, hoe lossen we dit op?
	}

	public void delete_leaves() {
		int max = (int) (Integer.MAX_VALUE / 2 - 1);
		
		// implement while loop that runs until total number of leaves is zero

		System.out.println("Beginning leaf deletion");
		
		int old_leave_size; 
		do {
			// for every vertex v, that is not a terminal
			for (int v = 0; v < this.cost.length; v++) {
				// if not a terminal and not a leaf already
				if (!terminal_check(v) && !this.leaves.contains(v)) {
					// arraylist with adjacent nodes
					ArrayList<Integer> adjacent_to_v = new ArrayList<>();

					for (int i = 0; i < this.cost.length; i++) {
						if (i == v) continue; // don't check if v is adjacent to itself
						if (cost_index(v, i) < max) { // if edge exists 
							adjacent_to_v.add(i); // add to adjacent arraylist
						}
					}

					if (adjacent_to_v.size() == 1) { // a leaf only has one adjacent vertex
						this.leaves.add(v);
						System.out.println(v);
					}
				}
			}
			System.out.println("# leaves deleted: " + this.leaves.size());	
			old_leave_size = this.leaves.size();

		} while (this.leaves.size() != old_leave_size);
	}

	// to deal with upper triangle
	public int cost_index(int a, int b) {
		return a > b ? this.cost[a][b] : this.cost[b][a];//a>b return [a][b] else return [b][a]
	}

	public boolean terminal_check(int v) {
		for (int i = 0; i < this.terminals.length; i++) {
			if (this.terminals[i] == v)
				return true;
		}
		return false;
	}
	
	 //creates a matrix with the shortest path from any a to any b (using the Floyd-Warschall algorithm)
    public void Floyd_Warschall_Matrix() {
        for (int k = 0; k < this.cost.length; k++) {
            for (int i = 0; i < this.cost.length; i++) {
                for (int j = 0; j < this.cost.length; j++) {
                    if (cost_index(i,k) + cost_index(k,j) < cost_index(i,j)) {
                        if(i > j) {this.cost[i][j] = cost_index(i,k) + cost_index(k,j);}
                        else{this.cost[j][i] = cost_index(i,k) + cost_index(k,j);}
                            //this.pre[i].add[j]
                    }
                }

            }
            System.out.println(k);
        }
        System.out.println("FW done");
    }
}
