package chapter06;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadRenderer {

    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data : imageData) {
            renderImage(data);
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
