package database.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.EmailAlreadyUsedException;
import exceptions.UsernameAlreadyUsedException;

/**
 * Use files to manages data.
 * 
 * @author Modica
 * @author Gavina
 * @date 2015/04/17
 */
public class RegistrationFileChecker implements IRegistrationChecker {

	private String userPath;
	private String emailsFile;

	/**
	 * @param userPath the path in which the user data is saved
	 * @param emailsFile the file in which e-mails list is located
	 */
	public RegistrationFileChecker(String userPath, String emailsFile) {
		super();
		this.userPath = userPath;
		this.emailsFile = emailsFile;
	}

	@Override
	public void checkUsername(String username) throws UsernameAlreadyUsedException {
		if (new File(userPath + username).exists())
			throw new UsernameAlreadyUsedException();
	}

	@Override
	public void checkEmail(String email) throws EmailAlreadyUsedException, IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(emailsFile));
			String line = reader.readLine();

			while (line != null) {

				if (line.trim().equals(email.trim())) {
					reader.close();
					throw new EmailAlreadyUsedException();
				}

				line = reader.readLine();
			}

			reader.close();

		} catch (FileNotFoundException e) {
			File file = new File(emailsFile);
			file.createNewFile();
		}

	}
}
