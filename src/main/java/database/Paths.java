package database;

import java.io.File;

/**
 * Defines path where data is located.
 *
 * @author Noris
 * @date 2015/04/25
 */
public final class Paths {

    private static final String DATABASE_PATH = "database";
    private static final String USERS_PATH = "users";
    private static final String EMAILS_PATH = "emails";
    private static final String EMAILS_FILENAME = "emails";
    private static final String CONFIRM_PATH = "confirm";

    public static String getUsersPath() {
        return DATABASE_PATH + File.separator + USERS_PATH + File.separator;
    }

    public static String getEmailsPath() {
        return DATABASE_PATH + File.separator + EMAILS_PATH + File.separator + EMAILS_FILENAME;
    }

    public static String getConfirmPath() {
        return DATABASE_PATH + File.separator + USERS_PATH + File.separator + CONFIRM_PATH + File.separator;
    }

}
