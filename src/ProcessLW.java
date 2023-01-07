import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class ProcessLW extends Thread {
    protected int token;
    protected int numHermanos;
    protected int id;
    protected String name;
    protected ServerSocket serverCC;
    protected Socket socketSC;
    protected Socket[] socketCC;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

    protected long[] timestamps;
    protected long myts;

    public void startProcess() {
        makeConnections();
        while (true) {
            try {
                waitHeavyweight();
                requestCS();
                for (int i = 0; i < 10; i++) {
                    System.out.println((i+1) + ") Soc el proces lightweight " + name);
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
        input = new ObjectInputStream(socketSC.getInputStream());
        String s = "";
        while (!s.equals("TOKENLWOK")) {
            s = (String) input.readObject();
        }
    }

    private void notifyHeavyweight() throws Exception {
        output = new ObjectOutputStream(socketSC.getOutputStream());
        output.writeObject("LWOK");
    }

    protected void requestCS() {

    }

    private boolean okayCS() {
        for (int i = 0; i < timestamps.length; i++) {
            if (timestamps[i] == 0)
                continue;

            if (timestamps[i] == Long.MAX_VALUE)
                return false;

            if ((timestamps[id] > timestamps[i]) || ((timestamps[id] == timestamps[i]) && (id > i)))
                return false;
        }

        return true;
    }

    private void waitCS() {
        //need to read from others
        for (int i = 0; i < numHermanos; i++) {
            try {
                socketCC[i].setSoTimeout(2);
                input = new ObjectInputStream(socketCC[i].getInputStream());
                String s = (String) input.readObject();
                //process message
                manageString(s);
            } catch (Exception ignored) {
            }
        }
    }

    private void manageString(String s) throws IOException {
        if (s.contains("requestLa")) {
            String[] split = s.split("-");
            long ts = Long.parseLong(split[1]);
            int id = Integer.parseInt(split[2]);
            timestamps[id] = ts;
        } else if (s.contains("releaseLa")) {
            String[] split = s.split("-");
            int id =  Integer.parseInt(split[1]);
            timestamps[id] = 0;
        } else if (s.contains("requestRi")) {
            String[] split = s.split("-");
            long ts = Long.parseLong(split[1]);
            int id = Integer.parseInt(split[2]);

            if ((myts == Long.MAX_VALUE) || (ts < myts) || (ts == myts && id < this.id)) {
                //send okay
                output = new ObjectOutputStream(socket.getOutputStream());
                String ack = "OKAY-"+id;
                output.writeObject(ack);
            } else {
                //encuar
                timestamps[id] = ts;
            }
        } else if (s.contains("OKAY")) {
            String[] split = s.split("-");
            int id = Integer.parseInt(split[1]);
            if (this.id == id)
                return 6;
        }
    }

    private void resetTimeout() {
        for (int i = 0; i < numHermanos; i++) {
            try {
                socketCC[i].setSoTimeout(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void broadcastMessage(String msg) throws IOException {
        for (int i = 0; i < numHermanos; i++) { //broadcast timestamp
            output = new ObjectOutputStream(socketCC[i].getOutputStream());
            output.writeObject(msg);
        }
    }

    protected void makeConnections() {

    }

    protected void fillTimestamps() {
        Arrays.fill(timestamps, Long.MAX_VALUE);
    }

    @Override
    public void run() {
        startProcess();
    }
}




