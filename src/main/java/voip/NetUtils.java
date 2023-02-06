package voip;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author Micieli
 * @date 2015/04/28
 */
public class NetUtils {

    private static final ArrayList<Integer> inUse = new ArrayList<>();

    public static int findFreePort() {

        ServerSocket socket = null;

        try {

            socket = new ServerSocket(0);

            int port = socket.getLocalPort();

            try {

                socket.close();

            } catch (IOException e) {
                // Ignore IOException on close()
                e.printStackTrace();
            }

            if (inUse.contains(port)) {
                return findFreePort();
            }

            inUse.add(port);
            return port;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new IllegalStateException("Could not find a free TCP/IP port!");
    }

    public static void releasePort(int port) {
        inUse.remove(port);
    }

    public static String getIpByInetAddress(InetAddress address) {
        return address.getHostAddress();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface netInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

}
