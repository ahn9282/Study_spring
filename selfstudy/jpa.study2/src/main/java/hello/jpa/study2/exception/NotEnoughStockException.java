package hello.jpa.study2.exception;

public class NotEnoughStockException extends  RuntimeException{
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }
}
