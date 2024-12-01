package network.tcp.v1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressMain {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getByName("localhost");
        System.out.println(localHost);

        InetAddress google = InetAddress.getByName("google.com");
        System.out.println(google);

    }
}
