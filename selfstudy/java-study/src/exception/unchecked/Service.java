package exception.unchecked;

public class Service {

    Client client = new Client();

    public void callCatch(){
        try{
            client.call();
        }catch(Exception e){

            System.out.println("예외 처리, message = " + e.getMessage());

        }
        System.out.println("정상 로직");

    }

    public void callThrow(){
        client.call();
    }

    public void hello(){
        System.out.println("hello!!");
        client.call();
        System.out.println("hihi");

    }
}
