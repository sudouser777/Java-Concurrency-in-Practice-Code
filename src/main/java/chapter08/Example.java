package chapter08;

import javax.swing.text.Element;
import java.util.List;
import java.util.concurrent.Executor;

public class Example {

    void processSequentially(List<Element> elements) {
        elements.forEach(this::process);
    }

    void processInParallel(Executor executor, List<Element> elements) {
        elements.forEach(element -> executor.execute(() -> process(element)));
    }

    private void process(Element element) {

    }
}
