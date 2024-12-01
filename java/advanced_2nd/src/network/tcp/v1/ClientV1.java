package network.tcp.v1;

import util.MyLogger;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.*;

public class ClientV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");
        Socket socket = new Socket("localhost", PORT);

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());
        log("소켓 연결 : " + socket);

        //서버에게 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server : " + toSend);


        //서버로부터 문자 받기
        String receive = input.readUTF();
        log("client <- server : " + socket);
        input.close();
        output.close();
        socket.close();
    }
}