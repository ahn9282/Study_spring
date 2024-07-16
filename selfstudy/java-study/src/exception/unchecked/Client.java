package exception.unchecked;

public class Client {
    public void call(){
        throw new MyUnCheckedException("ex");
    }

}
