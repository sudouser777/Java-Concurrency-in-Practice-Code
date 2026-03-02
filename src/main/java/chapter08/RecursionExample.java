package chapter08;


import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class RecursionExample {

    public class Node<T> {
        public T compute() {
            return null;
        }

        public List<Node<T>> getChildren() {
            return null;
        }
    }

    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    public <T> void parallelRecursive(Executor executor, List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            executor.execute(() -> results.add(node.compute()));
            parallelRecursive(executor, node.getChildren(), results);

        }
    }

    public <T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Queue<T> results = new ConcurrentLinkedQueue<>();
        parallelRecursive(executor, nodes, results);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return results;
    }
}
