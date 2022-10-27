import java.io.*;
import java.net.*;

public class ProcessA extends ProcessHW {
    private ServerSocket serverServer;

    public ProcessA() {
        try {
            token = 1;
            NUM_LIGHTWEIGHTS = 3;
            answersLW = 0;
            socketSC = new Socket[NUM_LIGHTWEIGHTS];
            serverServer = new ServerSocket(8080);
            serverClient = new ServerSocket(3030);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void makeConnections() {
        try {
            System.out.println("Waiting connections in A ...");
            socketSS = serverServer.accept();
            System.out.println("B connected to A");
            for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
                socketSC[i] = serverClient.accept();
            }
            System.out.println("All connected in A ...\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
