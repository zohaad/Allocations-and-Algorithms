import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		String filename = "instance" + args[0] + ".gr";
		File file = new File(filename);
		Reader reader = new Reader(file);

		try {

			// plan
			// leaves deletion -> floyd-warshall -> kruskal -> leaves deletion -> backtracking (make set) -> new graph -> kruskal (no cycles) -> leaves deletion

			Integer[][] cost = reader.read_cost();
			ArrayList<Integer> terminals = reader.read_terminals();

			// leaves deletion
			cost = Leaves.remove(cost, terminals);
			System.gc();

			// floyd-warshall
			Mst mst = new Mst(cost);
			Mst original_mst = new Mst(cost);
			mst.floyd_warshall();
			// kruskal
			ArrayList<int[]> kruskal_edges = mst.kruskal();
			Reader kruskal_reader = new Reader(cost.length);
			Integer[][] kruskal_cost = kruskal_reader.read_cost(kruskal_edges);

			// leaves deletion
			kruskal_cost = Leaves.remove(kruskal_cost, terminals);

			// backtracking
			System.out.println("Backtracking");
			ArrayList<Integer> non_metric_nodes = new ArrayList<>();
			for (int[] e : kruskal_edges) {

				ArrayList<Integer> path = mst.path(e[0], e[1]);
				for (Integer n : path) {
					if (!non_metric_nodes.contains(n)) { // ensuring edges will be unique
						non_metric_nodes.add(n);
					}
				}
			}
			System.out.println(non_metric_nodes.size());
			// Mst.print_ArrayList(non_metric_nodes);4

			System.gc();

			// make "residual" graph
			original_mst.remove_except(non_metric_nodes);

			// kruskal to make it a tree, remove cycles
			kruskal_edges = original_mst.kruskal();
			kruskal_reader = new Reader(cost.length);
			kruskal_cost = kruskal_reader.read_cost(kruskal_edges);

			// leaves deletion
			Leaves.remove(kruskal_cost, terminals);

			// calculating 2OPT-cost
			int two_opt_cost = 0;

			for (int i = 0; i < kruskal_cost.length; i++) {
				for (int j = 0; j < i; j++) { // <= case is always 0
					if (kruskal_cost[i] != null && kruskal_cost[i][j] != null) {
						two_opt_cost += kruskal_cost[i][j];
					}
				}
			}

			System.out.println("2OPT cost: " + two_opt_cost);

			System.gc();

			// writing file with +1 correction
			Writer writer = new Writer(args[0], kruskal_cost, two_opt_cost);
			try {
				writer.write();
			} catch (IOException io) {
				io.printStackTrace();
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}