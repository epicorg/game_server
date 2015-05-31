package data_management_tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import server.ServerInitializer;
import data_management.DataManager;
import data_management.RegisteredUser;
import exceptions.RegistrationFailedException;

/**
 * File saving test with {@link DataManager}.
 * 
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 * @see DataManager
 * @see RegisteredUser
 */

public class Test03 {

	private DataManager dataManager;
	private RegisteredUser user;
	private ServerInitializer serverInitializer = new ServerInitializer();

	@Before
	public void setUp() {

		serverInitializer.init();

		dataManager = DataManager.getInstance();
		user = new RegisteredUser("marx", "proletarian707",
				"marx@proletarian.org");

	}

	/**
	 * Test method for
	 * {@link data_management.DataManager#checkPassword(data_management.RegisteredUser)}
	 * .
	 * 
	 * @throws RegistrationFailedException
	 */
	@Test
	public void testCheckPassword() throws RegistrationFailedException {

		assertTrue(dataManager.checkPassword(user));

		dataManager.saveRegistrationFields(user);
		assertTrue(dataManager.checkPassword(user));

	}

}
