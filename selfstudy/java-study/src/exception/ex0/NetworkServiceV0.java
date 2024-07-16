package exception.ex0;

import exception.ex1.NetworkClientV1;

public class NetworkServiceV0 {
    public void sendMessage(String data) {
        String address = "http://example.com";
    NetworkClientV1 client = new NetworkClientV1(address);

        client.connect();
        client.send(data);
        client.disconnect();

    }

}
