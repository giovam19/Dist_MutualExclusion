import java.net.Socket;

public class ProcessLWA1 extends ProcessLW{

    public ProcessLWA1() {
        try {
            token = 1;
            id = "A1";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 3030);
            //socketCC = new Socket("localhost", 1133);
            /*input = socketSC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            output = new DataOutputStream(socketSC.getOutputStream());*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
