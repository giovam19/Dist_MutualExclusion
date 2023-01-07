import java.net.Socket;

public class ProcessLWB1 extends ProcessLW{
    public ProcessLWB1() {
        try {
            numHermanos = 1;
            token = 1;
            id = 0;
            name = "B1";
            myts = Long.MAX_VALUE;
            socketCC = new Socket[numHermanos];
            timestamps = new long[numHermanos+1];
            fillTimestamps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            socketSC = new Socket("localhost", 2211);
            socketCC[0] = new Socket("localhost", 3311);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void requestCS() {
        ricartRequest();
    }
}
