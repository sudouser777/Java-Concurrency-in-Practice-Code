package chapter08;

import net.jcip.annotations.Immutable;

import java.util.LinkedList;
import java.util.List;

@Immutable
public class Node<P, M>{

    final P position;

    final M move;

    final Node<P, M> prev;

    Node(P position, M move, Node<P, M> prev) {
        this.position = position;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> node = this; node != null; node = node.prev) {
            solution.addFirst(node.move);
        }
        return solution;
    }
}
