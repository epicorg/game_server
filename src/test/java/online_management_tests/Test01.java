package online_management_tests;

import exceptions.UserNotOnlineException;
import online_management.OnlineManager;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * This test sets online a defined number of users, and it chooses randomly one of those users. Then, with the username,
 * it searches the hashCode of this user, and with this hashCode it search the username again.
 *
 * @author Noris
 * @date 2015/04/05
 * @see OnlineManager
 */
public class Test01 {

    private final int ONLINE_USERS = 100;

    @Test
    public void test() throws UnknownHostException {
        OnlineManager onlineManager = OnlineManager.getInstance();

        // Set online ONLINE_USERS users
        for (int i = 0; i < ONLINE_USERS; i++) {
            String usernameGen = "user" + i;
            InetAddress ipAddressGen = InetAddress.getByName("192.168.1." + i);
            onlineManager.setOnline(usernameGen, ipAddressGen, null);
        }

        // Get a random user
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(ONLINE_USERS);
        String realUsername = "user" + randomNumber;

        // Get the hashCode of realUsername
        int hashCode;
        try {
            hashCode = onlineManager.getHashCodeByUsername(realUsername);
            System.out.println("HashCode of " + realUsername + ": " + hashCode);
            // Get the username of the previous founded HashCode
            String foundUsername = onlineManager.getUsernameByHashCode(hashCode);
            System.out.println("Username of " + hashCode + ": " + foundUsername);
            assertEquals(realUsername, foundUsername);
        } catch (UserNotOnlineException e) {
            e.printStackTrace();
        }
    }

}
