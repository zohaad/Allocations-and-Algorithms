import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		String filename = "instance" + args[0] + ".gr";
		File file = new File(filename);
		Reader reader = new Reader(file);

		try {
			Integer[][] cost = reader.read_cost();
			ArrayList<Integer> terminals = reader.read_terminals();
			cost = Leaves.remove(cost, terminals);

			System.gc();

			Mst mst = new Mst(cost);
			Integer[][] fw = mst.floyd_warshall();
			
			// to verify that costs are equal, change x to 58, y to 70
			// int x = 70;
			// int y = 58;
			// ArrayList<Integer> path = mst.path(x, y);
			// System.out.print("from " + x + " to " + y + ": ");
			// Mst.print_ArrayList(path);
			// int c = 0;
			// for (int i = 0; i < path.size() - 1; i++) {
			// 	c += mst.cost(path.get(i), path.get(i + 1));
			// }
			// System.out.println("cost: " + c);
			// end verification

			ArrayList<int[]> kruskal_edges = mst.kruskal();
			System.out.println("Kruskal done!");

			Reader kruskal_reader = new Reader(cost.length);
			Integer[][] kruskal_cost = kruskal_reader.read_cost(kruskal_edges);
			kruskal_cost = Leaves.remove(kruskal_cost, terminals);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}