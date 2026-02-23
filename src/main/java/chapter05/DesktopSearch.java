package chapter05;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class DesktopSearch {

    public static void startIndexing(File[] roots) {
        BlockingDeque<File> queue = new LinkedBlockingDeque<>();
        FileFilter filter = _ -> true;

        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < roots.length; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }

    static void main() {
        startIndexing(new File[]{
                new File("/root"),
        });
    }
}
