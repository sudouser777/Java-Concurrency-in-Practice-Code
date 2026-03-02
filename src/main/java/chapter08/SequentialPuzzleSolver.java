package chapter08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SequentialPuzzleSolver<P, M> {

    private final Puzzle<P, M> puzzle;

    private final Set<P> seen = new HashSet<>();


    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new Node<>(pos, null, null));
    }

    private List<M> search(Node<P, M> node) {
        if (!seen.contains(node.position)) {
            seen.add(node.position);
            if (puzzle.isGoal(node.position)) {
                return node.asMoveList();
            }

            for (M move : puzzle.legalMoves(node.position)) {
                P newPos = puzzle.move(node.position, move);
                Node<P, M> child = new Node<>(newPos, move, node);
                List<M> result = search(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
