import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLWA2 extends ProcessLW {

    public ProcessLWA2() {
        try {
            token = 0;
            id = "A2";
            serverCC = new ServerSocket(1133);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 3030);
            /*input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeDelay() throws InterruptedException {
        Thread.sleep(1000);
    }
}
