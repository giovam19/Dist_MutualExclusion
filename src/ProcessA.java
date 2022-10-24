import java.io.*;
import java.net.*;

public class ProcessA {
    private int token;
    private final int NUM_LIGHTWEIGHTS = 3;
    private int answersLW;
    private ServerSocket serverServer;
    private ServerSocket serverClient;
    private Socket socketSS;
    private Socket[] socketSC;
    private DataInputStream input;
    private DataOutputStream output;

    public ProcessA() throws IOException {
        token = 1;
        answersLW = 0;
        socketSC = new Socket[NUM_LIGHTWEIGHTS];
        serverServer = new ServerSocket(1111);
        serverClient = new ServerSocket(1122);
        socketSS = serverServer.accept();
        for (int i = 0; i < NUM_LIGHTWEIGHTS; i++) {
            socketSC[i] = serverClient.accept();
        }
    }

    public void startProcess() throws Exception {
        while (true) {
            while (token != 1)
                listenHeavyweight();
            for (int i = 0; i < NUM_LIGHTWEIGHTS; i++)
                sendActionToLightweight();
            while (answersLW < NUM_LIGHTWEIGHTS)
                listenLightweight();
            token = 0;
            sendTokenToHeavyweight();
        }
    }

    private void listenHeavyweight() throws IOException {
        String str;
        input = new DataInputStream(socketSS.getInputStream());
        str = input.readUTF();
        if (str.equals("TOKENOK")) {
            token = 1;
        }
    }

    private void sendActionToLightweight() throws IOException {
        answersLW = 0;
        for (Socket s : socketSC) {
            output = new DataOutputStream(s.getOutputStream());
            output.writeUTF("TOKENOK");
            output.flush();
        }
    }

    private void listenLightweight() throws IOException {
        String str;
        for (Socket s : socketSC) {
            input = new DataInputStream(s.getInputStream());
            str = input.readUTF();
            if (str.equals("LWOK"))
                answersLW++;
        }
    }

    private void sendTokenToHeavyweight() throws IOException {
        output = new DataOutputStream(socketSS.getOutputStream());
        output.writeUTF("TOKENOK");
        output.flush();

    }

    /*----------------------- MAIN -----------------------*/
    public static void main(String[] args) throws Exception {
        ProcessA a = new ProcessA();
        a.startProcess();
    }
}
