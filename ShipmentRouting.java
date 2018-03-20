import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShipmentRouting {

    public static void main(String[] args) {

        Orders[] order = null;

        try {
            order = readexcelOrders();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Roadlegs[] roadleg = null;
        
        try {
            roadleg = readexcelRoadlegs();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 1; i< order.length; i++){
            order[i].changeReached(Dijkstra(order, roadleg, i));
        }

        int count = 0;
        System.out.println("Orders(The output columns for the orders are: the ID, the origin nr, the destination nr," +
                " the available departure time, the necessary arrival time, the actual arrival time, the weight, " +
                "the roadlegs used (named by their id’s), if they are reached on time and the size of the route used.): ");
        for (int o = 1; o < order.length; o++) {
            if (order[o].getReached()){
                count++;}
            System.out.println(order[o].getId() + " " + order[o].getOrigin() + " " + order[o].getDestination()
                    + " " + order[o].getDeparture()+ " " + order[o].getArrival()  + " " + order[o].getTime()
                    + " " + order[o].getWeight() + " " + order[o].getTotalRoute() + " " + order[o].getReached()
                    + "  " + order[o].getSizeRoute());
        }

        System.out.println("Roadlegs(The output columns for the orders are: the ID, the origin nr, the destination nr," +
                " the departure time, the arrival time, the (updated) capacity, the cost and if the cost is fixed.): ");
        for (int r = 1; r < roadleg.length; r++) {
            System.out.println(roadleg[r].getID() + " " + roadleg[r].getOrigin() + " " + roadleg[r].getDestination()
                    + " " + roadleg[r].getDepartureTime() + " " + roadleg[r].getArrivalTime() + " " +
                    roadleg[r].getCapacity() + " " + roadleg[r].getCostperunit() + " " + roadleg[r].getFixed());
        }
        System.out.println("Orders on time: " + count);
        
    }

    //creates the Orders objects
    public static Orders[] readexcelOrders()
            throws java.io.FileNotFoundException {
        File file = new File("Orders.txt");
        Scanner in = new Scanner(file);

        Orders[] order = new Orders[4952];
        for (int o = 1; o < order.length; o++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
            double f = in.nextDouble();

            order[o] = new Orders(a, b, c, d, e, Integer.MAX_VALUE, f, null, false);
        }

        in.close();
        return order;

    }

    //creates the Roadlegs objects
    public static Roadlegs[] readexcelRoadlegs()
            throws java.io.FileNotFoundException {
        File file = new File("Roadlegs.txt");
        Scanner in = new Scanner(file);

        Roadlegs[] roadleg = new Roadlegs[1217];
        for (int r = 1; r < roadleg.length; r++) {

            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
            double f = in.nextDouble();
            double g = in.nextDouble();
            Boolean h = in.nextBoolean();

            roadleg[r] = new Roadlegs(a, b, c, d, e, f, g, h);
        }

        in.close();
        return roadleg;

    }

    //the main algorithm of the code, uses dijkstra as its basis.
    public static Boolean Dijkstra(Orders[] order,Roadlegs[] roadleg, int o) {
        int origin = order[o].getOrigin();                                  // origin of package o
        int destination = order[o].getDestination();                        // destination of package o
        int[] best = new int[46];                                           //used to compare costs(times)
        boolean[] visited = new boolean[46];                                //checks if city already visited
        int[] array = new int [46];                                         //stores routes used to get to city i
        for (int i = 1; i < best.length; i++) {
            visited[i] = false;
            best[i] = Integer.MAX_VALUE;
        }
        best[origin] = order[o].getDeparture();
        array[origin]= -1;

        for (int count = 1; count < best.length; count++) {
            int node = minimum(best, visited);                              //checks for unvisited "best" node

            if (best[node] == Integer.MAX_VALUE) {                          //no more feasible times
                order[o].changeTime(Integer.MAX_VALUE);                     //order cannot be delivered
                return false;}

            if (node == destination) {                                      //checks if destination reached
                order[o].changeTime(best[destination]);                     //time of order registered
                printRoute(order, roadleg, array, array[destination], o);   //prints route in object order
                reduceCapacity(order, roadleg, o);                          //uses route to reduce capacity roadleg
                if (order[o].getTime() <= order[o].getArrival()){           //order is on time
                    return true;
                }
                else return false;                                          //order is too late
            }
            int arrival;
            visited[node] = true;
            for (int r = 1; r < roadleg.length; r++) {
                if (roadleg[r].getOrigin() == node){                        //checks if roadleg can be used
                    if (roadleg[r].getDestination() != destination){
                        arrival = roadleg[r].getArrivalTime() +200;         //adds transition time
                    }
                    else {arrival = roadleg[r].getArrivalTime();}           //no transition time needed
                    if(best[node] <= roadleg[r].getDepartureTime()          //checks time contraint
                        && best[roadleg[r].getDestination()] > arrival      //checks if new arrivaltime is better than old
                        && order[o].getWeight() <= roadleg[r].getCapacity() //checks capacity constraint
                    ){
                        best[roadleg[r].getDestination()] = arrival;        //new arrivaltime is best time
                        array[roadleg[r].getDestination()] = roadleg[r].getID(); //stores road used in array
                    }
                }
            }
        }
        return false;
    }


    //checks for the minimum not yet used node (vertice/city)
    public static int minimum(int[] best, boolean[] visited) {
        int least = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 1; i < best.length; i++) {
            if (!visited[i] && best[i] <= least) {
                least = best[i];
                index = i;
            }
        }
        return index;
    }

    //printRoute uses recursion to get through all the used routes,which are stored in array, for an order.
    public static void printRoute(Orders[] order, Roadlegs[] roadleg,int[] array, int pre, int o){
        if (pre!= -1){
            order[o].addRoute(pre);
            printRoute(order, roadleg, array, array[roadleg[pre].getOrigin()], o);
        }

    }

    //for all the roadlegs used, reduceCapacity reduces the capacity with the weight of the given order.
    public static void reduceCapacity(Orders[] order, Roadlegs[] roadleg, int o){
        for (int i =0; i< order[o].getSizeRoute(); i++) {
            roadleg[order[o].getSingleRoute(i)].changeCapacity(order[o].getWeight());
        }

    }
}
