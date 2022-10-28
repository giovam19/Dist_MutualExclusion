import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessB extends ProcessHW{

    public ProcessB() {
        try {
            token = 0;
            id = "B";
            NUM_LIGHTWEIGHTS = 2;
            answersLW = 0;
            socketSC = new Socket[NUM_LIGHTWEIGHTS];
            serverClient = new ServerSocket(2211);
            socketSS = new Socket("localhost", 8080);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void makeConnections() {
        try {
            for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
                socketSC[i] = serverClient.accept();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
