package database.loader;

import data_management.RegisteredUser;
import exceptions.LoginFailedException;

import java.io.IOException;

/**
 * Interface for user checking in the login. It allows to switch different data
 * managing systems painless.
 *
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 * @see RegisteredUser
 */
public interface ILoginChecker {

    /**
     * Checks if the user password matches with the one saved during the registration.
     *
     * @param registeredUser the user to check
     * @throws LoginFailedException if the username and the password doesn't match
     * @throws IOException          if there is a communication problem with the data managing system
     */
    void checkUser(RegisteredUser registeredUser) throws LoginFailedException, IOException;

}