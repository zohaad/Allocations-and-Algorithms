import java.util.*;

public class DisjointSets {
	private ArrayList<ArrayList<Integer>> S;

	public DisjointSets() {
		this.S = new ArrayList<ArrayList<Integer>>();
	}

	public void add(int v) {
		this.S.add(new ArrayList<Integer>());
		this.S.get(this.S.size() - 1).add(v);
	}

	public ArrayList<Integer> find(int v) {
		for (ArrayList<Integer> i : this.S) {
			if (i.contains(v)) {
				return i;
			}
		}
		return null; // should never happen
	}

	public void union(int v1, int v2) {
		ArrayList<Integer> set_v2 = find(v2);
		find(v1).addAll(find(v2));
		this.S.remove(set_v2);
	}
}