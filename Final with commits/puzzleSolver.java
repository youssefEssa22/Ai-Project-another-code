import java.util.*;

public class puzzleSolver {

    // The goal state of the puzzle, represented as a 2D array of integers.
    private final int[][] goalState = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8}
    };

    // The possible directions for moving the empty tile, represented as 2D arrays.
    private final int[][] directions = {
        {-1, 0}, // Up
        {1, 0},  // Down
        {0, -1}, // Left
        {0, 1}   // Right
    };

    // Depth-First Search (DFS) algorithm to solve the puzzle.
    public Node DFS(Node start) {
        // HashSet to store visited states (nodes) to avoid infinite loops.
        Set<String> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        
        // Add the start node to the stack.
        stack.add(start);

        while (!stack.isEmpty()) {
            // Pop the first element from the stack (LIFO).
            Node currentNode = stack.pop();
            // Check if the current state has been visited before.
            if (!visited.contains(Arrays.deepToString(currentNode.state))) {
                visited.add(Arrays.deepToString(currentNode.state));

                // Check if the current state matches the goal state.
                if (Arrays.deepEquals(goalState, currentNode.state)) {
                    // Print the number of nodes expanded and the path cost.
                    System.out.println("Nodes expanded: " + visited.size());
                    System.out.println("Path cost: " + currentNode.depth);
                    System.out.println("path:");
                    System.out.println(toStringPath(currentNode));
                    return currentNode; // Return the solution node.
                }
            }

            // Generate legal moves (child nodes) for the current node.
            for (Node nextNode : getLegalMoves(currentNode)) {
                // Check if the next node's state has not been visited.
                if (!visited.contains(Arrays.deepToString(nextNode.state))) {
                    // Add the next node to the stack for further exploration.
                    stack.add(nextNode);
                }
            }
        }
        System.out.println("error");
        return null; // Return null if no solution is found.
    }

    // Breadth-First Search (BFS) algorithm to solve the puzzle.
    public Node BFS(Node start) {
        // HashSet to store visited states (nodes) to avoid infinite loops.
        Set<String> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start); // Add the start node to the queue.

        while (!queue.isEmpty()) {
            // Dequeue the first element from the queue (FIFO).
            Node currentNode = queue.poll();
            if(!visited.contains(Arrays.deepToString(currentNode.state))){
                visited.add(Arrays.deepToString(currentNode.state));
                if (Arrays.deepEquals(goalState, currentNode.state)) {
                    // Print the number of nodes expanded and the path cost.
                    System.out.println("Nodes expanded: " + visited.size());
                    System.out.println("Path cost: " + currentNode.depth);
                    System.out.println("path:");
                    System.out.println(toStringPath(currentNode));
                    return currentNode; // Return the solution node.
                }
            }

            // Generate legal moves (child nodes) for the current node.
            for (Node nextNode : getLegalMoves(currentNode)) {
                if (!visited.contains(Arrays.deepToString(nextNode.state))) {
                    queue.add(nextNode); // Add the next node to the queue.
                }
            }
        }
        System.out.println("error");
        return null; // Return null if no solution is found.
    }

    // A* (A-Star) search algorithm to solve the puzzle.
    public Node AStar(Node start) {
        // Priority queue to store nodes based on their total cost (f(n) = g(n) + h(n)).
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparing(n -> n.totalCost));
        Set<String> visited = new HashSet<>();
        start.heuristic = heuristic(start); // Calculate the heuristic value for the start node.
        start.totalCost = start.depth + start.heuristic; // Calculate the total cost for the start node.
        minHeap.add(start); // Add the start node to the priority queue.

        while (!minHeap.isEmpty()) {
            // Dequeue the node with the lowest total cost from the priority queue.
            Node currentNode = minHeap.poll();
            if(!visited.contains(Arrays.deepToString(currentNode.state))){
                visited.add(Arrays.deepToString(currentNode.state));
                if (Arrays.deepEquals(goalState, currentNode.state)) {
                    // Print the number of nodes expanded and the path cost.
                    System.out.println("Nodes expanded: " + visited.size());
                    System.out.println("Path cost: " + currentNode.depth);
                    System.out.println("path:");
                    System.out.println(toStringPath(currentNode));
                    return currentNode; // Return the solution node.
                }
            }

            // Generate legal moves (child nodes) for the current node.
            for (Node nextNode : getLegalMoves(currentNode)) {
                if (!visited.contains(Arrays.deepToString(nextNode.state))) {
                    // Calculate the heuristic value and total cost for the next node.
                    nextNode.heuristic = heuristic(nextNode);
                    nextNode.totalCost = nextNode.depth + nextNode.heuristic;
                    minHeap.add(nextNode); // Add the next node to the priority queue.
                }
            }
        }
        System.out.println("error");
        return null; // Return null if no solution is found.
    }

    // Heuristic function using Manhattan Distance to estimate the cost to reach the goal state.
    private int heuristic(Node node) {
        int[][] currentState = node.state;
        int distance = 0;
        int size = currentState.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = currentState[i][j];
                if (value != 0) {
                    int goalX = value / size;
                    int goalY = value % size;
                    distance += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return distance;
    }

    // Find the position of the empty tile in the current state.
    private int[] findEmptyTile(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Generate legal moves (child nodes) for the given node by moving the empty tile in all possible directions.
    private List<Node> getLegalMoves(Node node) {
        List<Node> moves = new ArrayList<>();
        int[][] currentState = node.state;
        int[] emptyTilePos = findEmptyTile(currentState);
        int row = emptyTilePos[0];
        int col = emptyTilePos[1];

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Check if the new position is within the grid bounds.
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                // Create a deep copy of the current state to avoid modifying the original.
                int[][] newState = deepCopy(currentState);
                newState[row][col] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;

                // Create a new node with the updated state, parent, and depth.
                Node newNode = new Node();
                newNode.state = newState;
                newNode.parent = node;
                newNode.depth = node.depth + 1;

                moves.add(newNode);
            }
        }
        return moves;
    }

    // Create a deep copy of a 2D integer array to avoid modifying the original.
    private int[][] deepCopy(int[][] currentState) {
        int [][] newState = new int[currentState.length][];
        for(int i = 0; i < currentState.length; i++)
            newState[i] = currentState[i].clone();
        return newState;
    }

    // Convert the path (sequence of nodes) to a string for printing.
    private String toStringPath(Node node){
        String path ="";
        String state = "";
            for (int[] row : node.state) {
                state = state + "\n"+Arrays.toString(row);
            }
        path = state;
        while (node.parent != null) {
            state = "";
            for (int[] row : node.state) {
                state = state+ "\n"+ Arrays.toString(row);
            }
            path = state+"\n"+
            "    |" +"\n" +
            "    V"+"\n"
            + path;
            node = node.parent;
        }
        return path;
    }
}
