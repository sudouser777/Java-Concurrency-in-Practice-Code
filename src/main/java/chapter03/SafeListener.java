package chapter03;


public class SafeListener {

    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event event) {
                doSomething(event);
            }
        };

    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerEventListener(safe.listener);
        return safe;
    }

    private void doSomething(Event event) {

    }
}
