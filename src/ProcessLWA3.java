import java.net.Socket;

public class ProcessLWA3 extends ProcessLW {

    public ProcessLWA3() {
        try {
            token = 0;
            id = "A3";
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

    @Override
    protected void makeDelay() throws InterruptedException {
        Thread.sleep(2000);
    }
}
