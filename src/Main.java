import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ProcessA pa = new ProcessA();
        ProcessB pb = new ProcessB();

        pa.start();
        pb.start();

        ProcessLWA1 pa1 = new ProcessLWA1();
        ProcessLWA2 pa2 = new ProcessLWA2();
        ProcessLWA3 pa3 = new ProcessLWA3();

        pa1.start();
        pa2.start();
        pa3.start();
    }
}
