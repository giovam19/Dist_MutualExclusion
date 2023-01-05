import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLWB2 extends ProcessLW {

    public ProcessLWB2() {
        try {
            token = 0;
            id = "B2";
            serverCC = new ServerSocket(3311);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 2211);
            /*input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
