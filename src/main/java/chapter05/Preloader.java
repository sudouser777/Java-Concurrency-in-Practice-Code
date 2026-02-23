package chapter05;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {

    private final FutureTask<ProductInfo> future = new FutureTask<>(this::loadProductInfo);

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws DataLoadException, ExecutionException {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw LaunderThrowable.launderThrowable(cause);
        }
    }

    private ProductInfo loadProductInfo() {
        return new ProductInfo();
    }


}
