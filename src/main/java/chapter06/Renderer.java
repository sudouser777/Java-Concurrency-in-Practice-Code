package chapter06;

import chapter05.LaunderThrowable;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;

public class Renderer {

    private final ExecutorService executor;

    public Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (ImageInfo imageInfo : imageInfos) {
            completionService.submit(imageInfo::downloadImage);
        }

        renderText(source);

        try {
            for (int i = 0; i < imageInfos.size(); i++) {
                ImageData data = completionService.take().get();
                renderImage(data);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
