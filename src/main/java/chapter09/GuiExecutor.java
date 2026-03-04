package chapter09;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class GuiExecutor extends AbstractExecutorService {

    private static final GuiExecutor instance = new GuiExecutor();

    private GuiExecutor() {
    }

    public static GuiExecutor getInstance() {
        return instance;
    }

    @Override
    public void shutdown() {
        instance.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return instance.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return instance.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return instance.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return instance.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        if (SwingUtilities.isEventDispatchThread())
            command.run();
        else
            SwingUtilities.invokeLater(command);
    }
}
