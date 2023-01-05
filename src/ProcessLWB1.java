import java.net.Socket;

public class ProcessLWB1 extends ProcessLW{
    public ProcessLWB1() {
        try {
            token = 1;
            id = "B1";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 2211);
            //socketCC = new Socket("localhost", 3311);
            /*input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
