import java.net.Socket;
import java.util.Date;

public class ProcessLWA3 extends ProcessLW {

    public ProcessLWA3() {
        try {
            numHermanos = 2;
            token = 0;
            id = 2;
            name = "A3";
            socketCC = new Socket[numHermanos];
            timestamps = new long[numHermanos+1];
            fillTimestamps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 3030);
            socketCC[0] = new Socket("localhost", 1133);
            socketCC[1] = new Socket("localhost", 1122);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void requestCS() {
        lamportRequest();
    }

    @Override
    protected void releaseCS() {
        lamportRelease();
    }
}
