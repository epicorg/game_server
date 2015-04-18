package loader;

import java.io.IOException;

import data_management.RegisteredUser;
import exception.LoginFailedException;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public interface ILoginChecker {

	public abstract void checkUser(RegisteredUser registeredUser)
			throws LoginFailedException, IOException;

}