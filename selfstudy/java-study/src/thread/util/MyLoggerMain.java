package thread.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static thread.util.MyLogger.*;

public  class MyLoggerMain {
    public static void main(String[] args) {
        log("Hello thread");
        log(123);
    }
}
