import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private class Node implements Comparable<Node> {
		private Board board;
		private int numMoves;
		private Node previous;
		public Node(Board board, Node previous){
			this.board = board;
			this.previous = previous;
			if(previous==null) this.numMoves = 0;
			else this.numMoves = previous.numMoves + 1;
		}
		@Override
		public int compareTo(Node o) {
			return (this.board.manhattan() - o.board.manhattan()) + (this.numMoves - o.numMoves);
		}
		
	};
	
	private Node lastNode = null;
	
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)  	
    	MinPQ<Node> Q = new MinPQ<Node>();
    	MinPQ<Node> SQ = new MinPQ<Node>();
    	Q.insert(new Node(initial, null));
    	SQ.insert(new Node(initial.twin(), null));
    	while(!Q.isEmpty() &&  !SQ.isEmpty()){
    		Node q = Q.delMin();
    		Node sq = SQ.delMin();
    		if(q.board.isGoal()){
    			lastNode = q;
    			break;
    		}else{
    			for(Board b : q.board.neighbors()){
    				if(q.previous == null || !q.previous.board.equals(b))
    					Q.insert(new Node(b,q));
    			}
    		}
    		if(sq.board.isGoal())
    			break;
    		else{
    			for(Board b : sq.board.neighbors()){
    				if(sq.previous == null || !sq.previous.board.equals(b))
    					SQ.insert(new Node(b,sq));
    			}
    		}
    	}
    }
    
    public boolean isSolvable() {
    	// is the initial board solvable?
    	return lastNode!=null;
    }
    
    public int moves() {
    	// min number of moves to solve initial board; -1 if no solution
    	if(lastNode!=null) return lastNode.numMoves;
    	else return -1;
    }
    
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if no solution
    	if(lastNode!=null){
    		Stack<Board> res = new Stack<Board>();
    		for(Node tail=lastNode; tail!=null; tail = tail.previous){
    			res.push(tail.board);
    		}
    		return res;
    	}
    	else return null;
    }
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}