package exception.ex3;


import exception.ex3.exception.ConnectExceptionV3;
import exception.ex3.exception.SendExceptionV3;

public class NetworkServiceV3 {
    public void sendMessage(String data) {
        String address = "http://example.com";
        NetworkClientV3 client = new NetworkClientV3(address);
        client.initError(data);

        try {
            client.connect();
            client.send(data);
        } catch (ConnectExceptionV3 e) {
            System.out.println("[오류] 코드 : " + e.getAddress() + ", 메시지 : " + e.getMessage());
        }catch (SendExceptionV3 e2){
            System.out.println("[오류] 코드 : " + e2.getSendData() + ", 메시지 : " + e2.getMessage());

        }finally {
            client.disconnect();
        }
    }

}
