import java.util.*;

public class MST {

    private int[][] cost;
    private int[][] next;
    private int[]	terminals;
    private int     max;
    private ArrayList<Integer> leaves;
    private ArrayList<Integer> pre; //dit werkt niet denk ik, hoe lossen we dit op?

    public MST(int[][] cost, int[] terminals) {
        this.cost      = cost;
        this.next      = new int[cost.length][];
        for (int i=0; i<cost.length; i++) {
            this.next[i] = new int[i+1];
            for (int j=0;j<this.next[i].length;j++){
                if(i!=j) {
                    next[i][j] = j + 1;
                }
            }
        }
        this.terminals = terminals;
        this.max       = (int) (Integer.MAX_VALUE / 2 - 1);
        this.leaves    = new ArrayList<>();
        this.pre       = new ArrayList<>(); //dit werkt niet denk ik, hoe lossen we dit op?
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
        for (int i = 0; i < this.terminals.length; i++) {
            if (this.terminals[i] == v)
                return true;
        }
        return false;
    }

    //creates a matrix with the shortest path from any a to any b (using the Floyd-Warschall algorithm)
    public void Floyd_Warschall_Matrix() {
        //int[][] next = new int [cost.length][cost.length];
        /*for (int i=0; i<this.cost.length; i++){
            for (int j=0; j<this.cost[i].length;j++){
                if(cost(i,j)< this.max){
                    this.next[i][j] = j+1;
                }
            }
        }*/


        for (int k = 0; k < this.cost.length; k++) {
            for (int i = 0; i < this.cost.length; i++) {
                for (int j = 0; j < this.cost[i].length; j++) {
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
        //System.out.println(Arrays.toString(this.pre.toArray()));
        System.out.println(this.pre.size());
        System.out.println(this.pre);
    }

    public void getPath(int i, int j){ //always i > j
        if (this.next[i][j] == j) {
            this.pre.add(j);
            System.out.println("= j:" +j);
        }
        else if (this.next[i][j] < j) {
            this.pre.add(j);
            System.out.println("< j :" +j);
            getPath(j-1, this.next[i][j]);
        }
        else {
            this.pre.add(j);
            System.out.println("> j:" +j);
            getPath(this.next[i][j]-1, j);
        }

        /*else{
            if (this.next[j][i] == j) {
                this.pre.add(j);
            }
            else{
                getPath(this.next[j][i], j);
                this.pre.add(j);
            }
        }*/
    }

    public void print_Matrix(){
        System.out.println("Cost: ");
        for(int i =0; i< this.cost.length; i++) {
            System.out.print(i+1 +": ");
            for (int j=0; j<this.cost[i].length; j++){
                System.out.print(this.cost[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("j: 1 2 3 4 5 6");
        System.out.println("next: ");
        for(int i =0; i< this.cost.length; i++) {
            System.out.print(i+1 +": ");
            for (int j=0; j<this.cost[i].length; j++){
                if (i==j) {
                    System.out.println("-");
                }
                else System.out.print(this.next[i][j] + " ");
            }
            //System.out.println();
        }
        System.out.println("j: 1 2 3 4 5 6");
    }

}