package chapter08;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executorService.submit(new LoadFileTask("header.html"));
            footer = executorService.submit(new LoadFileTask("footer.html"));

            String page = renderBody();
            // Will deadlock -- task waiting for result of sub task
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return "";
        }
    }

    public static class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            return Files.readString(Paths.get(fileName));
        }
    }
}
