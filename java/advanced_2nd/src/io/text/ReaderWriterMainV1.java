package io.text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.buffered.BufferedConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV1 {

    public static void main(String[] args) throws IOException {
        String writeStr = "ABC";
        //문자 -  byte UTF-8 인코딩
        byte[] writeBytes = writeStr.getBytes(UTF_8);
        System.out.println("writeStr = " + writeStr);
        System.out.println("writeBytes = " + Arrays.toString(writeBytes));

        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(writeBytes);
        fos.close();

        FileInputStream fis = new FileInputStream(FILE_NAME);
        byte[] readBytes = fis.readAllBytes();
        fis.close();

        String readStr = new String(readBytes, UTF_8);
        System.out.println("readBytes = " + Arrays.toString(readBytes));
        System.out.println("readStr = " + readStr);
    }
}
