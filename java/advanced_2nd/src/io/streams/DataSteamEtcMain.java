package io.streams;

import java.io.*;

public class DataSteamEtcMain {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/data.dat");
        DataOutputStream dos = new DataOutputStream(fos);

        dos.writeUTF("회원 A");
        dos.writeInt(20);
        dos.writeDouble(2.5);
        dos.writeBoolean(true);
        dos.close();

        DataInputStream dis = new DataInputStream(new FileInputStream("temp/data.dat"));
        System.out.println(dis.readUTF());
        System.out.println(dis.readInt());
        System.out.println(dis.readDouble());
        System.out.println(dis.readBoolean());
        dis.close();
    }
}
