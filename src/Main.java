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

        ProcessLWB1 pb1 = new ProcessLWB1();
        ProcessLWB2 pb2 = new ProcessLWB2();

        pa1.start();
        pa2.start();
        pa3.start();

        pb1.start();
        pb2.start();
    }
}
