import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProcessLWB1 {
    private int token;
    private Socket socketSC;
    private Socket socketCC;
    private DataInputStream input;
    private DataOutputStream output;

    public ProcessLWB1() {
        try {
            token = 0;
            socketSC = new Socket("localhost", 2211);
            input = new DataInputStream(socketSC.getInputStream());
            output = new DataOutputStream(socketSC.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startProcess() {
        while(true) {
            try {
                waitHeavyweight();
                //requestCS
                for (int i = 0; i < 10; i++) {
                    System.out.println("Soc el proces lightweight B1");
                    Thread.sleep(1000);
                }
                //releaseCS
                notifyHeavyweight();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void waitHeavyweight() throws IOException {
        String s = input.readUTF();
        if (s.equals("TOKENOK")) {
            token = 1;
        } else {
            System.out.println("Error in connection");
        }
    }

    private void notifyHeavyweight() throws IOException {
        output.writeUTF("LWOK");
        output.flush();
        token = 0;
    }

    public static void main(String[] args) {
        ProcessLWB1 p = new ProcessLWB1();
        p.startProcess();
    }
}
