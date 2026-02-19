package chapter02;

public class LoggingWidget extends Widget {

    @Override
    public synchronized void doSomething() {
        System.out.println(this + ": calling doSomething");
        super.doSomething();
    }
}
