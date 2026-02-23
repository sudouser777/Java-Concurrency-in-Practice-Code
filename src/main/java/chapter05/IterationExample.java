package chapter05;

import chapter02.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class IterationExample {

    public void iterateVector(Vector<String> vector) {
        for (int i = 0; i < vector.size(); i++) {
            doSomething(vector.get(i));
        }
    }

    private void doSomething(String s) {

    }

    public void safelyIterateVector(Vector<String> vector) {
        synchronized (vector) {
            for (int i = 0; i < vector.size(); i++) {
                doSomething(vector.get(i));
            }
        }
    }

    public void forEachIterate() {
        List<Widget> widgets = Collections.synchronizedList(new ArrayList<>());

        // May throw ConcurrentModificationException
        for (Widget w : widgets) {
            doSomething(w);
        }
    }

    private void doSomething(Widget w) {

    }
}
