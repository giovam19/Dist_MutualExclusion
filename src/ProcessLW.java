import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessLW extends Thread {
    protected int token;
    protected String id;
    protected ServerSocket serverCC;
    protected Socket socketSC;
    protected Socket socketCC;
    protected InputStream input;
    protected BufferedReader br;
    protected DataOutputStream output;

    public void startProcess() {
        while (true) {
            try {
                waitHeavyweight();
                requestCS();
                for (int i = 0; i < 10; i++) {
                    System.out.println(i + ") Soc el proces lightweight " + id);
                    Thread.sleep(1000);
                }
                releaseCS();
                notifyHeavyweight();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private void waitHeavyweight() throws Exception {
        input = socketSC.getInputStream();
        br = new BufferedReader(new InputStreamReader(input));
        String s = "";
        while (!s.equals("TOKENLWOK")) {
            s = br.readLine();
        }
    }

    private void notifyHeavyweight() throws Exception {
        output = new DataOutputStream(socketSC.getOutputStream());
        output.writeBytes("LWOK\n\r");
        //Thread.sleep(0, 15);
        output.flush();
    }

    protected void requestCS() throws Exception {
        if (token == 0) {
            input = socketCC.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            String check = "";
            while (!check.equals("OKCC")) {
                check = br.readLine();
            }
            token = 1;
        }
    }

    protected void releaseCS() throws Exception {
        output = new DataOutputStream(socketCC.getOutputStream());
        output.writeBytes("OKCC\n\r");
        output.flush();
        token = 0;
    }

    protected void makeDelay() throws InterruptedException {}

    @Override
    public void run() {
        startProcess();
    }
}
