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
			
			// testing
			ArrayList<Integer> path = mst.path(58, 70);
			System.out.print("path from " + 58 + " to " + 70 + ": ");
			Mst.print_ArrayList(path);

			fw = Leaves.remove(fw, terminals);
			
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}