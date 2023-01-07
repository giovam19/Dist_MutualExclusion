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
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

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

    private void listenHeavyweight() throws Exception {
        input = new ObjectInputStream(socketSS.getInputStream());
        String str = (String) input.readObject();
        if (str.equals("TOKENOK")) {
            token = 1;
        }
    }

    private void sendActionToLightweight() throws Exception {
        for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
            output = new ObjectOutputStream(socketSC[i].getOutputStream());
            output.writeObject("TOKENLWOK");
        }
    }

    private void listenLightweight() throws Exception {
        for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
            input = new ObjectInputStream(socketSC[i].getInputStream());
            String str = (String) input.readObject();
            if (str.equals("LWOK")) {
                answersLW++;
            }
        }
    }

    private void sendTokenToHeavyweight() throws Exception {
        output = new ObjectOutputStream(socketSS.getOutputStream());
        output.writeObject("TOKENOK");

    }

    protected void makeConnections() {}

    @Override
    public void run() {
        startProcess();
    }
}
