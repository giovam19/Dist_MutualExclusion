import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ProcessLWA1 extends ProcessLW{

    public ProcessLWA1() {
        try {
            numHermanos = 2;
            token = 1;
            id = 0;
            name = "A1";
            serverCC = new ServerSocket(1122);
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
            socketSC = new Socket("localhost", 3030);
            socketCC[0] = new Socket("localhost", 1133);
            socketCC[1] = serverCC.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
