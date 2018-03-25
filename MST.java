import java.util.*;

public class MST {

    private int[][] cost;
    private Integer[][] next;
    private int[]	terminals;
    private int     max;
    private ArrayList<Integer> leaves;
    private ArrayList<Integer> pre;
    private int[][] vSet;

    public MST(int[][] cost, int[] terminals) {
        this.cost      = cost;
        this.next = new Integer[this.cost.length][];
        for(int i = 0; i < this.cost.length; i++) {
            this.next[i] = new Integer[i+1];
            for (int j = 0; j < this.next[i].length; j++) {
                if (i != j) {
                    this.next[i][j] = j;
                }
            }
        }
        this.terminals = terminals;
        this.max       = (int) (Integer.MAX_VALUE / 2 - 1);
        this.leaves    = new ArrayList<>();
        this.pre       = new ArrayList<>();
        this.vSet = new int[this.cost.length][2];
        for (int i=1; i<this.cost.length; i++){
            vSet[i][0]=vSet[i][1]=i;
        }
    }

    public void delete_leaves() {
        //int max = (int) (Integer.MAX_VALUE / 2 - 1);

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
                        if (cost(v, i) < this.max) { // if edge exists
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
    public int cost(int a, int b) {
        return a > b ? this.cost[a][b] : this.cost[b][a]; //a>b return [a][b] else return [b][a]
    }

    public boolean terminal_check(int v) {

        for (int i : this.terminals) {
            if (i == v)
                return true;
        }
        return false;
    }

    //creates a matrix with the shortest path from any a to any b (using the Floyd-Warschall algorithm)
    public void Floyd_Warschall_Matrix() {


        for (int k = 1; k < this.cost.length; k++) {
            for (int i = 1; i < this.cost.length; i++) {
                for (int j = 1; j < this.cost[i].length; j++) {
                    if (cost(i,k) + cost(k,j) < this.cost(i,j)) {
                        /*if(i > j) {this.cost[i][j] = cost(i,k) + cost(k,j);}
                        else{this.cost[j][i] = cost(i,k) + cost(k,j);}*/
                        this.cost[i][j] = cost(i,k) + cost(k,j); //it is always the case that i>j
                        this.next[i][j] = this.next[i][k]; //waarom is this.next niet nodig??
                    }
                }
            }
            //System.out.println(k);
        }
        System.out.println("FW done");


    }

    public void print_Pre(){
        System.out.println(this.pre);
        this.pre.clear();
    }

    public void pathRecursion(int i, int j, int k){
        int a= this.next[i][j];
        if (a == min(i,j)) {
            this.pre.add(k);
        }
        else if (a >k){
            this.pre.add(this.next[i][j]);
            pathRecursion(a,k, k);
        }
        else{
            this.pre.add(a);
            pathRecursion(k, a, k);
        }
    }

    public void getPath(int i, int j) {
        System.out.println("path " + i + "-" + j+ ":");
        //System.out.print(i+" ");
        this.pre.add(i);
        pathRecursion(i,j,j);
    }

    public void print_Matrix(){
        System.out.println("Cost: ");
        for(int i =1; i< this.cost.length; i++) {
            System.out.print(i +": ");
            for (int j=1; j<this.cost[i].length; j++){
                System.out.print(this.cost[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("j: 1 2 3 4 5 6 7");
        System.out.println("next: ");
        for(int i =1; i< this.cost.length; i++) {
            System.out.print(i +": ");
            for (int j=1; j<this.cost[i].length; j++){
                if (i==j) {
                    System.out.println("-");
                }
                else System.out.print(this.next[i][j] + " ");
            }
            //System.out.println();
        }
        System.out.println("j: 1 2 3 4 5 6 7");
    }

    public int min(int a, int b){
        return a < b ? a : b;
    }

    public int max(int a, int b){ return a > b ? a : b;
    }

    public void Kruskal(int nodes) {
        int amount = (((nodes-1)*nodes)/2);
        System.out.println(amount);
        Integer[][] ordered_edges = new Integer[amount][];
        int count = 0;
        //outerloop:
        for (int i = 1; i < this.cost.length; i++) {
            for (int j = 1; j < i; j++) {
                //if (count == amount) break outerloop;
                if (this.cost[i][j] < max) {
                    ordered_edges[count]    = new Integer[4];
                    ordered_edges[count][0] = i;
                    ordered_edges[count][1] = j;
                    ordered_edges[count][2] = this.cost[i][j];
                    count++;
                }
            }
        }
        //System.out.println(count);

        // int[][] out = Arrays.stream(ordered_edges)
        // .sorted(Comparator.comparing(x -> ordered_edges[][]))
        // .toArray(int[][]::new);


        // sorting on ascending order
        Arrays.sort(ordered_edges, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                Integer c1 = o1[2];
                Integer c2 = o2[2];
                return c1.compareTo(c2);
            }
        });

        for(int i=0; i<count; i++) {
            System.out.println(ordered_edges[i][0]+ " " + ordered_edges[i][1] + " " + ordered_edges[i][2]+ " " + ordered_edges[i][3]);
        }

        for(int i=0; i<amount; i++){
            int a= ordered_edges[i][0];
            int b= ordered_edges[i][1];
            int c;
            int d;
            if(this.vSet[a][1]!=this.vSet[b][1]){
                ordered_edges[i][3]=1;
                c = max(this.vSet[a][1],this.vSet[b][1]);
                d = min(this.vSet[a][1],this.vSet[b][1]);
                this.vSet[a][1]=this.vSet[b][1]=d;
                change_set(c,d);
            }
            System.out.println(ordered_edges[i][0]+ " " + ordered_edges[i][1] + " " + ordered_edges[i][2]+ " " + ordered_edges[i][3]);
            System.out.println(vSet[a][1] +" "+vSet[b][1]);
        }

        for(int i=0; i<amount; i++){
            if(ordered_edges[i][3]!=null)
                System.out.println(ordered_edges[i][0] +"-" +ordered_edges[i][1]);
        }

        /*for (int i=1;i<this.cost.length;i++){
            System.out.println(i+": "+vSet[i][0] + " " + vSet[i][1]);
        }*/

        /*ArrayList<Integer> A = new ArrayList<>();

        List<List<Integer>> set_v = new ArrayList<List<Integer>>();
        System.out.println("this.cost.length = " + this.cost.length);
        for (int v = 1; v < this.cost.length; v++) { // for each vertex
            set_v.add(new ArrayList<Integer>());
            set_v.get(v).add(v);
            System.out.println(set_v.get(v).get(0));
        }*/
    }

    public void change_set(int c, int d){
        for (int i=0; i<this.vSet.length; i++){
            if (this.vSet[i][1]==c){
                this.vSet[i][1]=d;
            }
        }
    }

}