import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLWA3 extends ProcessLW {

    public ProcessLWA3() {
        try {
            token = 0;
            id = "A3";
            socketSC = new Socket("localhost", 3030);
            //socketCC = new Socket("localhost", 1133);
            input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeDelay() throws InterruptedException {
        Thread.sleep(2000);
    }
}
