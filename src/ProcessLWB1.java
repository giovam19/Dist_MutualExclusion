import java.io.*;
import java.net.Socket;

public class ProcessLWB1 extends ProcessLW{

    public ProcessLWB1() {
        try {
            token = 0;
            id = "B1";
            socketSC = new Socket("localhost", 2211);
            //socketCC = new Socket("localhost", 3311);
            input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
