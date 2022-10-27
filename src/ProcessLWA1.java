import java.io.*;
import java.net.Socket;

public class ProcessLWA1 extends ProcessLW{

    public ProcessLWA1() {
        try {
            token = 0;
            id = "A1";
            socketSC = new Socket("localhost", 3030);
            //socketCC = new Socket("localhost", 1133);
            input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
