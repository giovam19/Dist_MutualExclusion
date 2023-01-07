import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLWA1 extends ProcessLW{

    public ProcessLWA1() {
        try {
            numHermanos = 2;
            token = 1;
            id = 0;
            name = "A1";
            myts = 0;
            serverCC = new ServerSocket(1122);
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
            socketCC[1] = serverCC.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void requestCS() {
        lamportRequest();
    }

    @Override
    protected void releaseCS() throws IOException {
        lamportRelease();
    }
}
