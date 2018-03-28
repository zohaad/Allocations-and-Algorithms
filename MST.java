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
		// this.pre = new ArrayList[cost.length]; //dit werkt niet denk ik, hoe lossen we dit op?
	}

	// to deal with upper triangle
	public int cost(int a, int b) {
		return a > b ? this.cost[a][b] : this.cost[b][a];//a>b return [a][b] else return [b][a]
	}

	public boolean terminal_check(int v) {
		for (int i = 0; i < this.terminals.length; i++) {
			if (this.terminals[i] == v)
				return true;

		return false;
	}
	
	 //creates a matrix with the shortest path from any a to any b (using the Floyd-Warschall algorithm)
    public void Floyd_Warschall_Matrix() {
        for (int k = 0; k < this.cost.length; k++) {
            for (int i = 0; i < this.cost.length; i++) {
                for (int j = 0; j < this.cost.length; j++) {
                    if (cost(i,k) + cost(k,j) < cost(i,j)) {
                        if(i > j) {this.cost[i][j] = cost(i,k) + cost(k,j);}
                        else{this.cost[j][i] = cost(i,k) + cost(k,j);}
                            //this.pre[i].add[j]
                    }
                }

            }
            System.out.println(k);
        }
        System.out.println("FW done");
    }

    public void floyd_warshall() {
    	
    }
}
