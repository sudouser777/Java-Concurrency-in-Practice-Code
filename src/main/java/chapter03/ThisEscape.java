package chapter03;


public class ThisEscape {

    public ThisEscape(EventSource source) {
        source.registerEventListener(
                new EventListener() {
                    public void onEvent(Event event) {
                        doSomething(event);
                    }
                }
        );
    }

    private void doSomething(Event event) {

    }
}
