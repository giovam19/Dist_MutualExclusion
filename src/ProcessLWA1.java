import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProcessLWA1 {
    private int token;
    private Socket socketSC;
    private Socket socketCC;
    private DataInputStream input;
    private DataOutputStream output;

    public ProcessLWA1() {
        try {
            token = 0;
            socketSC = new Socket("localhost", 1122);
            //socketCC = new Socket("localhost", 1133);
            input = new DataInputStream(socketSC.getInputStream());
            output = new DataOutputStream(socketSC.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startProcess() {
        while (true) {
            try {
                waitHeavyweight();
                //requestCS
                for (int i = 0; i < 10; i++) {
                    System.out.println(i + ") Soc el proces lightweight A1");
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
        ProcessLWA1 p = new ProcessLWA1();
        p.startProcess();
    }
}
