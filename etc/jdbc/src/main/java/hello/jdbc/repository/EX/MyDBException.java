package hello.jdbc.repository.EX;
/*
* RuntimeException 을 종속시켜 언체크 예외
* */
public class MyDBException extends RuntimeException {

    public MyDBException() {
    }

    public MyDBException(String message) {
        super(message);
    }

    public MyDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDBException(Throwable cause) {
        super(cause);
    }
}
