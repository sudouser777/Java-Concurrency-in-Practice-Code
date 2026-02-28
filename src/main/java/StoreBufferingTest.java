public class StoreBufferingTest {

    static int x = 0;
    static int y = 0;

    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws Exception {

        long iterations = 0;

        while (true) {
            iterations++;

            x = 0;
            y = 0;
            r1 = 0;
            r2 = 0;

            Thread t1 = new Thread(() -> {
                x = 1;
                r1 = y;
            });

            Thread t2 = new Thread(() -> {
                y = 1;
                r2 = x;
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            if (r1 == 0 && r2 == 0) {
                System.out.println("Reordered after " + iterations + " iterations!");
                break;
            }
        }
    }
}