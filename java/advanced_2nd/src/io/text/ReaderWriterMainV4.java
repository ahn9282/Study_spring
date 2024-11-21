package io.text;

import java.io.*;

import static io.text.TextConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ReaderWriterMainV4 {
    public static final int BUFFER_SIZE = 8192;
    public static void main(String[] args) throws IOException {

        String writeStr = "Abc\n가나다";
        System.out.println("=== Write String ===");
        System.out.println("writeStr = " + writeStr);

        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        BufferedWriter bw = new BufferedWriter(fw, BUFFER_SIZE);
        bw.write(writeStr);
        bw.close();

        StringBuilder sb = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        BufferedReader br = new BufferedReader(fr, BUFFER_SIZE);
        String line;
        while((line =  br.readLine()) != null){
            sb.append(line).append("\n");
        }
        br.close();

        System.out.println("=== Read String ===");
        System.out.println("read result = " + sb.toString());
    }

}
