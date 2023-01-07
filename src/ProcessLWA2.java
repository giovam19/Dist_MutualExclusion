import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ProcessLWA2 extends ProcessLW {

    public ProcessLWA2() {
        try {
            numHermanos = 2;
            token = 0;
            id = 1;
            name = "A2";
            serverCC = new ServerSocket(1133);
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
            for (int i = 0; i < numHermanos; i++) {
                socketCC[i] = serverCC.accept();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
