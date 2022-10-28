import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessHW extends Thread{
    protected int token;
    protected String id;
    protected int NUM_LIGHTWEIGHTS;
    protected int answersLW;
    protected ServerSocket serverClient;
    protected Socket socketSS;
    protected Socket[] socketSC;
    protected InputStream input;
    protected BufferedReader br;
    protected DataOutputStream output;

    public void startProcess() {
        makeConnections();
        while (true) {
            try {
                while (token == 0) {
                    listenHeavyweight();
                }
                sendActionToLightweight();
                while (answersLW < NUM_LIGHTWEIGHTS) {
                    listenLightweight();
                }
                token = 0;
                answersLW = 0;
                sendTokenToHeavyweight();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private void listenHeavyweight() throws IOException {
        input = socketSS.getInputStream();
        br = new BufferedReader(new InputStreamReader(input));
        String str = br.readLine();
        if (str.equals("TOKENOK")) {
            token = 1;
        }
    }

    private void sendActionToLightweight() throws Exception {
        for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
            output = new DataOutputStream(socketSC[i].getOutputStream());
            output.writeBytes("TOKENLWOK\n\r");
            //Thread.sleep(0, 15);
            output.flush();
        }
    }

    private void listenLightweight() throws IOException {
        for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
            input = socketSC[i].getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            String str = br.readLine();
            if (str.equals("LWOK")) {
                answersLW++;
            }
        }
    }

    private void sendTokenToHeavyweight() throws Exception {
        output = new DataOutputStream(socketSS.getOutputStream());
        output.writeBytes("TOKENOK\n\r");
        //Thread.sleep(0, 15);
        output.flush();

    }

    protected void makeConnections() {}

    @Override
    public void run() {
        startProcess();
    }
}
