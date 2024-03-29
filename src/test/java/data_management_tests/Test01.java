package data_management_tests;

import data_management.RegisteredUser;
import data_management.UsersDataManager;
import exceptions.RegistrationFailedException;
import server.ServerInitializer;

/**
 * File saving test with {@link UsersDataManager}.
 *
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 * @see UsersDataManager
 * @see RegisteredUser
 */
class Test01 {

    public static void main(String[] args) throws RegistrationFailedException {
        new ServerInitializer().init();
        UsersDataManager dataManager = UsersDataManager.getInstance();
        RegisteredUser user = new RegisteredUser("Hegel", "I_AM_A_LOL", "hegel@epic.org");
        dataManager.saveRegistrationFields(user);
        RegisteredUser user2 = new RegisteredUser("Kant", "I_AM_TROLL", "kant@epic.org");
        dataManager.saveRegistrationFields(user2);
    }

}
