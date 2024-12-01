package network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class SessionV3 implements Runnable{

    private  final Socket socket;

    public SessionV3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {

                String received = dataInputStream.readUTF();
                log("client -> server : " + received);

                if (received.equals("exit")) break;

                String toSend = received + " World!";
                dataOutputStream.writeUTF(toSend);
                log("client <- server : " + toSend);

            }

            log("연결 종료 : " + socket);
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();

        }catch(IOException e){
            throw new RuntimeException();
        }
    }
}
