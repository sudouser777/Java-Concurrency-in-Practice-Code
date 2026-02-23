package chapter06;

import chapter05.LaunderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureRenderer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<>();
            for (ImageInfo imageInfo : imageInfos) {
                result.add(imageInfo.downloadImage());
            }
            return result;
        };
        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e);
        }
    }

    private void renderImage(ImageData image) {

    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return List.of();
    }

    private void renderText(CharSequence source) {

    }
}
