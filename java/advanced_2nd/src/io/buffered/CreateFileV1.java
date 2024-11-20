package io.buffered;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV1 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i <FILE_SIZE; i++) {
            fos.write(1);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("File created : " + FILE_NAME);
        System.out.println("File size : " + FILE_SIZE / 1024 / 1024 + " MB");
        System.out.println("during time : " + (endTime - startTime) + " ms");

    }
}
