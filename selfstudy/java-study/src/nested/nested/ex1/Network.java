package nested.nested.ex1;

public class Network {
    public void sendMessage(String text) {
        NetworkMessage message = new NetworkMessage(text);
        message.print();
    }
}
