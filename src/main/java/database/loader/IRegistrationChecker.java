package database.loader;

import exceptions.EmailAlreadyUsedException;
import exceptions.UsernameAlreadyUsedException;

import java.io.IOException;

/**
 * An interface for registration data checking.
 *
 * @author Gavina
 * @author Modica
 * @date 2015/04/17
 */
public interface IRegistrationChecker {

    /**
     * Check if username already exists.
     *
     * @param username the username to check
     */
    void checkUsername(String username) throws UsernameAlreadyUsedException;

    /**
     * Check if email already exists.
     *
     * @param email the email to check
     */
    void checkEmail(String email) throws EmailAlreadyUsedException, IOException;

}
