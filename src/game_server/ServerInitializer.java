package game_server;

import loader.FileChecker;
import loader.LoginChecker;
import writer.EmailFormatter;
import writer.EmailSaver;
import writer.UserCreator;
import writer.UserLineFormatter;
import data_management.DataManager;
import data_management.RegisterDataSaver;
import data_management.RegisteredUser;
import data_management.password_encrypter.Md5StringEncrypter;
import data_management.password_encrypter.PasswordEncrypter;

/**
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */

public class ServerInitializer {

	public void initDataManager() {

		DataManager dataManager = DataManager.getInstance();
		String path = dataManager.getPath();

		RegisterDataSaver registerDataSaver = new RegisterDataSaver(
				new UserCreator(path, new UserLineFormatter()), new EmailSaver(
						path + "emails", new EmailFormatter()));

		dataManager.setRegisterDataSaver(registerDataSaver);

		dataManager.setChecker(new FileChecker(path, "emails"));

		dataManager.setLoginChecker(new LoginChecker(path));

		RegisteredUser.setPasswordEncrypter(new PasswordEncrypter(
				new Md5StringEncrypter()));
	}

}
