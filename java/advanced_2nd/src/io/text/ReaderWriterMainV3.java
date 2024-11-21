package io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.text.TextConst.*;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV3 {
    public static void main(String[] args) throws IOException {

        String writeStr = "Abc";
        System.out.println("writeStr = " + writeStr);

        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writeStr);
        fw.close();

        StringBuilder sb = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        int ch;
        while((ch =  fr.read()) != -1){
            sb.append((char)ch);
        }
        fw.close();

        System.out.println("read result = " + sb.toString());
    }

}
