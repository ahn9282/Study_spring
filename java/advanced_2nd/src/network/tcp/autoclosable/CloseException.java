package network.tcp.autoclosable;

public class CloseException extends Exception {

    public CloseException() {
    }

    public CloseException(String message) {
        super(message);
    }
}
