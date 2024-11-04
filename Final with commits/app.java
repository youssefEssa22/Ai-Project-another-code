public class app {
    public static void main(String[] args) {
        puzzleSolver solver = new puzzleSolver();

        int[][] initialState = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };
        Node start = new Node(initialState);

        // Demonstrating how to use different search algorithms:
        long startTime, endTime, runtime;

        System.out.println("Solving using A* Search:");
        startTime = System.currentTimeMillis();
        solver.AStar(start);
        endTime = System.currentTimeMillis();
        runtime = endTime - startTime;
        System.out.println("Runtime: " + runtime + " milliseconds\n");


        System.out.println("Solving using BFS:");
        startTime = System.currentTimeMillis();
        solver.BFS(start);
        endTime = System.currentTimeMillis();
        runtime = endTime - startTime;
        System.out.println("Runtime: " + runtime + " milliseconds\n");

        System.out.println("Solving using DFS:");
        startTime = System.currentTimeMillis();
        solver.DFS(start);
        endTime = System.currentTimeMillis();
        runtime = endTime - startTime;
        System.out.println("Runtime: " + runtime + " milliseconds");



    }
}