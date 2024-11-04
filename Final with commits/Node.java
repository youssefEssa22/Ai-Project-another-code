public class Node {
    int[][] state;
    Node parent;
    int depth;
    int cost; // g(n) for A*
    int heuristic; // h(n) for A*
    int totalCost; // f(n) = g(n) + h(n) for A*

    // Constructor for creating a new node
    public Node(int[][] state) {
        this.state = state;
        this.parent = null;
        this.cost = 0;
        this.depth = 0;
        this.heuristic = 0;
        this.totalCost = 0;
    }

    // Default constructor (useful for creating temporary nodes)
    public Node() {}
}