import java.io.*;
import java.util.*;

public class Reader {

	private Scanner scan;
	private int nodes;
	private int edges;
	private Integer[][] cost;
	private ArrayList<Integer> terminals;

	public Reader(File file){
		try {

			this.scan = new Scanner(file);
			amount_nodes_edges();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 2nd constructor for kruskal
	public Reader(int nodes) {
		this.nodes = nodes;
	}

	public void amount_nodes_edges() {
		// skip first line
		this.scan.nextLine();

		String[] line_array = this.scan.nextLine().split(" ");
		this.nodes = Integer.parseInt(line_array[1]);

		line_array = this.scan.nextLine().split(" ");
		this.edges = Integer.parseInt(line_array[1]);
	}

	public Integer[][] read_cost()
	throws java.io.FileNotFoundException {
		// create Integer[][] triangular matrix
		this.cost = new Integer[this.nodes][];

		for (int i = 0; i < this.cost.length; i++) {
			this.cost[i] = new Integer[i + 1];
			this.cost[i][i] = 0;
		}

		int from;
		int to;
		int c;
		String[] line_array;

		for (int i = 0; i < this.edges; i++) {
			line_array = this.scan.nextLine().split(" ");
			from = Integer.parseInt(line_array[1]) - 1; // arrays start at 0
			to   = Integer.parseInt(line_array[2]) - 1; // arrays start at 0
			// to retain triangular form
			if (to > from) {
				int save = from;
				from = to;
				to = save;
			}
			c    = Integer.parseInt(line_array[3]);
			this.cost[from][to] = c;
		}

		return this.cost;
	}


	// method for kruskal cost
	public Integer[][] read_cost(ArrayList<int[]> mst) { // given a MST, make a new matrix
		this.cost = new Integer[this.nodes][];

		for (int i = 0; i < this.cost.length; i++) {
			this.cost[i] = new Integer[i + 1];
			this.cost[i][i] = 0;
		}

		int from;
		int to;

		for (int[] e : mst) {
			if (e[0] > e[1]) {
				from = e[0];
				to   = e[1];
			} else {
				from = e[1];
				to   = e[0];
			}

			this.cost[from][to] = e[2];
		}
		return this.cost;
	}

	public ArrayList<Integer> read_terminals() 
	throws java.io.FileNotFoundException {
		this.terminals = new ArrayList<Integer>();

		// skip 3 lines 
		this.scan.nextLine();
		this.scan.nextLine();
		this.scan.nextLine();
		int amount_terminals = Integer.parseInt(this.scan.nextLine().split(" ")[1]);
		
		String[] line_array;
		for (int i = 0; i < amount_terminals; i++) {
			line_array = this.scan.nextLine().split(" ");
			this.terminals.add(Integer.parseInt(line_array[1]) - 1); // decrement with 1, arrays start at 0
		}


		return this.terminals;
	}
}