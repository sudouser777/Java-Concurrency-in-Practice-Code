package chapter08;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentPuzzleSolver<P, M> {

    private final Puzzle<P, M> puzzle;

    private final ExecutorService executor;

    private final ConcurrentMap<P, Boolean> seen;

    final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
        this.executor = Executors.newCachedThreadPool();
        this.seen = new ConcurrentHashMap<>();
    }

    public ConcurrentPuzzleSolver() {
    }


    public List<M> solve() throws InterruptedException {
        try {
            P pos = puzzle.initialPosition();
            executor.execute(newTask(pos, null, null));
            // block until solution found
            Node<P, M> solution = this.solution.getValue();
            return solution == null ? null : solution.asMoveList();
        } finally {
            executor.shutdown();
        }
    }


    protected Runnable newTask(P pos, M move, Node<P, M> node) {
        return new SolverTask(pos, move, node);
    }

    class SolverTask extends Node<P, M> implements Runnable {
        SolverTask(P position, M move, Node<P, M> prev) {
            super(position, move, prev);
        }

        @Override
        public void run() {
            if (solution.isSet() || seen.putIfAbsent(position, true) != null) {
                return; // already solved or seen this position
            }
            if (puzzle.isGoal(position)) {
                solution.setValue(this);
            } else {
                for (M move : puzzle.legalMoves(position)) {
                    executor.execute(newTask(puzzle.move(position, move), move, this));
                }
            }
        }
    }
}
