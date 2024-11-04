import java.util.*;

public class Main {

    public static void main(String[] args) {
               
               
        int[] p1d = {1,2,3,4,0,5,6,7,8};
        int hueristic = 2;
        EightPuzzle start = new EightPuzzle(p1d, hueristic, 0);
        int[] win = { 0, 1, 2,
                      3, 4, 5,
                      6, 7, 8};
        EightPuzzle goal = new EightPuzzle(win, hueristic, 0);
               
        astar(start, goal);       

        }
       
        public static void astar(EightPuzzle start, EightPuzzle goal)
        {
                if(start.inversions() % 2 == 1)
                {
                        System.out.println("Unsolvable");
                        return;
                }
//            function A*(start,goal)
//            closedset := the empty set                 
//            The set of nodes already evaluated.
             LinkedList<EightPuzzle> closedset = new LinkedList<EightPuzzle>();
//           openset := set containing the initial node // The set of tentative nodes to be evaluated. priority queue
                PriorityQueue<EightPuzzle> openset = new PriorityQueue<EightPuzzle>();

                openset.add(start);
               

                while(openset.size() > 0){
//               x := the node in openset having the lowest f_score[] value
                        EightPuzzle x = openset.peek();

//               if x = goal
                        if(x.mapEquals(goal))
                        {
//                   return reconstruct_path(came_from, came_from[goal])
                                 Stack<EightPuzzle> toDisplay = reconstruct(x);
                                 System.out.println("Printing solution... ");
                                 System.out.println(start.toString());
                                 print(toDisplay);
                                 return;
                                 
                        }
//               remove x from openset
//               add x to closedset
                        closedset.add(openset.poll());
                        LinkedList <EightPuzzle> neighbor = x.getChildren();
//               foreach y in neighbor_nodes(x)                
                        while(neighbor.size() > 0)
                        {
                                EightPuzzle y = neighbor.removeFirst();
//                   if y in closedset
                            if(closedset.contains(y)){
//                       continue
                                  continue;
                                }
//                   tentative_g_score := g_score[x] + dist_between(x,y)
//                   if y not in openset
                            if(!closedset.contains(y)){
//                       add y to openset
                                  openset.add(y); 
                                }
                        }
                }
        }

        public static void print(Stack<EightPuzzle> x)
        {
                while(!x.isEmpty())
                {
                        EightPuzzle temp = x.pop();
                        System.out.println(temp.toString());
                }
        }

        public static Stack<EightPuzzle> reconstruct(EightPuzzle winner)
        {
                Stack<EightPuzzle> correctOutput = new Stack<EightPuzzle>();
               
                while(winner.getParent() != null)
                {
                correctOutput.add(winner);
                winner = winner.getParent();
                }

                return correctOutput;
        }
       
    }
   