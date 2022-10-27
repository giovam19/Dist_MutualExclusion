import java.io.*;
import java.net.Socket;

public class ProcessLW extends Thread {
    protected int token;
    protected String id;
    protected Socket socketSC;
    protected Socket socketCC;
    protected InputStream input;
    protected BufferedReader br;
    protected DataOutputStream output;

    public void startProcess() {
        while (true) {
            try {
                waitHeavyweight();
                //requestCS
                for (int i = 0; i < 10; i++) {
                    System.out.println(i + ") Soc el proces lightweight " + id);
                    Thread.sleep(1000);
                }
                //releaseCS
                notifyHeavyweight();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private void waitHeavyweight() throws Exception {
        String s = br.readLine();
        if (s.equals("TOKENLWOK")) {
            token = 1;
        }
    }

    private void notifyHeavyweight() throws Exception {
        output.writeBytes("LWOK");
        //Thread.sleep(0, 15);
        output.flush();
        token = 0;
    }

    protected void makeDelay() throws InterruptedException {}

    @Override
    public void run() {
        startProcess();
    }
}
