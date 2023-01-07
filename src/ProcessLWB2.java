import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLWB2 extends ProcessLW {

    public ProcessLWB2() {
        try {
            numHermanos = 1;
            token = 0;
            id = 1;
            name = "B2";
            myts = Long.MAX_VALUE;
            serverCC = new ServerSocket(3311);
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
            socketSC = new Socket("localhost", 2211);
            for (int i = 0; i < numHermanos; i++) {
                socketCC[i] = serverCC.accept();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void requestCS() {
        ricartRequest();
    }
}
