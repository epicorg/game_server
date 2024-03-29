package email_confirmation;

import data_management.RegisteredUser;
import database.Paths;

import java.io.*;

/**
 * @author Noris
 * @date 05/05/2015
 */
public class EmailConfirmation {

    public static void saveCode(RegisteredUser user, String code) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(Paths.getConfirmPath() + user.getUsername()));
            writer.write(code);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCode(String username, String code) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(Paths.getConfirmPath()
                    + username));
        } catch (FileNotFoundException e) {
            return false;
        }

        String savedCode;
        try {
            savedCode = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (code.equals(savedCode)) {
            new File(Paths.getConfirmPath() + username).delete();
            return true;
        }

        return false;
    }

    public static boolean isEmailConfirmed(String username) {
        try {
            new BufferedReader(
                    new FileReader(Paths.getConfirmPath() + username));
        } catch (FileNotFoundException e) {
            return true;
        }
        return false;
    }

}
