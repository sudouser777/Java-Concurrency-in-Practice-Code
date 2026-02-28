package chapter07;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MailExample {

    public boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        AtomicBoolean hasNewMail = new AtomicBoolean(false);

        try {
            for (String host : hosts) {
                executor.execute(() -> {
                    if (checkMail(host))
                        hasNewMail.set(true);
                });
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(timeout, unit);
            executor.close();
        }

        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        return false;
    }
}
