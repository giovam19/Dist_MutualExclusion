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
        setTimeouts();
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

    private int receiveMessage() {
        boolean HW = true;
        String s;
        int value = -1;

        while (value == -1) {
            try {
                if (HW) {
                    //leer del HW
                    input = new ObjectInputStream(socketSC.getInputStream());
                    s = (String) input.readObject();
                    //process message
                    value = manageString(s, socketSC);
                } else {
                    //leer del LW
                    for (int i = 0; i < numHermanos; i++) {
                        try {
                            input = new ObjectInputStream(socketCC[i].getInputStream());
                            s = (String) input.readObject();
                            //process message
                             value = manageString(s, socketCC[i]);
                        } catch (Exception ignored) {}
                    }

                    HW ^= true; //togglea el boolean
                }
            } catch (Exception e) {
                HW ^= true; //togglea el boolean
            }
        }

        return value;
    }

    private void waitHeavyweight() {
        int res = -1;
        while (res != 1){
            res = receiveMessage();
        }
    }

    private void notifyHeavyweight() throws Exception {
        output = new ObjectOutputStream(socketSC.getOutputStream());
        output.writeObject("LWOK");
    }

    protected void requestCS() {
    }

    protected void lamportRequest() {
        try {
            myts = System.currentTimeMillis(); //get timestamp
            resetReleases();
            timestamps[id] = myts; //save it in queue

            String token = "requestLa-" + myts + "-" + id;
            broadcastMessage(token); //broadcast request
            receiveMessage();
            while (!okayCS()) { //resolve priority
                waitCS();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        receiveMessage();
    }

    private int manageString(String s, Socket socket) throws IOException {
        if (s.equals("TOKENLWOK")) {
            return 1;
        } else if (s.contains("requestLa")) {
            String[] split = s.split("-");
            long ts = Long.parseLong(split[1]);
            int id = Integer.parseInt(split[2]);
            timestamps[id] = ts;
            //send ack
            output = new ObjectOutputStream(socket.getOutputStream());
            String ack = "ACK-" + myts + "-" + this.id;
            output.writeObject(ack);
            return 2;
        } else if (s.contains("releaseLa")) {
            String[] split = s.split("-");
            int id =  Integer.parseInt(split[1]);
            timestamps[id] = 0;
            return 3;
        } else if (s.contains("ACK")) {
            String[] split = s.split("-");
            long ts = Long.parseLong(split[1]);
            int id = Integer.parseInt(split[2]);
            timestamps[id] = ts;
            return 4;
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
            return 5;
        } else if (s.contains("OKAY")) {
            String[] split = s.split("-");
            int id = Integer.parseInt(split[1]);
            if (this.id == id)
                return 6;
        }

        return -1;
    }

    private void setTimeouts() {
        try {
            socketSC.setSoTimeout(2);
            for (int i = 0; i < socketCC.length; i++) {
                socketCC[i].setSoTimeout(2);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String msg) throws IOException {
        for (int i = 0; i < numHermanos; i++) { //broadcast timestamp
            output = new ObjectOutputStream(socketCC[i].getOutputStream());
            output.writeObject(msg);
        }
    }

    protected void lamportRelease() throws IOException {
        String s = "releaseLa-" + id;
        broadcastMessage(s);
    }

    protected void releaseCS() throws IOException {

    }

    protected void ricartRequest() {
        try {
            myts = System.currentTimeMillis(); //get timestamp
            String token = "requestRi-" + myts + "-" + id;
            broadcastMessage(token);
            int numOkay = 0;
            while (numOkay < numHermanos) {
                int res = receiveMessage();
                if (res == 6)
                    numOkay++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void ricartRelease() throws IOException {
        myts = Long.MAX_VALUE;
        for (int i = 0; i < timestamps.length; i++) {
            if (timestamps[i] != Long.MAX_VALUE) {
                String s = "OKAY-"+i;
                broadcastMessage(s);
                timestamps[i] = Long.MAX_VALUE;
            }
        }
    }

    protected void makeConnections() {

    }

    protected void fillTimestamps() {
        Arrays.fill(timestamps, Long.MAX_VALUE);
    }

    protected void resetReleases() {
        for (int i = 0; i < timestamps.length; i++) {
            if (timestamps[i] == 0) {
                timestamps[i] = Long.MAX_VALUE;
            }
        }
    }

    @Override
    public void run() {
        startProcess();
    }
}




