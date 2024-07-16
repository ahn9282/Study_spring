package exception.ex1;


public class NetworkServiceV1_2 {
    public void sendMessage(String data) {
        String address = "http://example.com";
        NetworkClientV1 client = new NetworkClientV1(address);
        client.initError(data);

        if (isError(client.connect())) return;

        if (isError(client.send(data))) return;

        client.disconnect();

    }

    private static boolean isError(String connectResult) {
        if (!connectResult.equals("success")) {
            System.out.println("[네트워크 오류 발생] 오류 코드 : " + connectResult);
            return true;
        }
        return false;
    }


}
