package io.text;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.text.TextConst.*;
import static java.nio.charset.StandardCharsets.*;


public class ReaderWriterMainV2 {

    public static void main(String[] args) throws IOException {

        String writeStr = "ABC";
        System.out.println("writeStr : " + writeStr);

        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        OutputStreamWriter osw = new OutputStreamWriter(fos, UTF_8);
        osw.write(writeStr);
        osw.close();

        FileInputStream fis = new FileInputStream(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis, UTF_8);

        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = isr.read()) != -1) {
            sb.append((char)ch);
        }
        isr.close();

        System.out.println("result : " + String.join(" ", sb.toString()));
    }
}
