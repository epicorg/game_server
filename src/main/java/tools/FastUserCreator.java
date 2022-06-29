package tools;

import data_management.password_encrypter.SHA512StringEncrypter;
import data_management.password_encrypter.StringEncrypter;
import database.Paths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static database.Paths.getUsersPath;

/**
 * Create user bypassing the checking function. Note: actually you can't log in with
 * invalids fields in the client (it checks the login fields like the registration fields).
 *
 * @author Noris
 * @date 05/05/2015
 */
class FastUserCreator {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        StringEncrypter encryptor = new SHA512StringEncrypter();
        String password = encryptor.encryptString(scanner.nextLine());

        System.out.print("Email: ");
        String email = scanner.nextLine();

        BufferedWriter writer = new BufferedWriter(new FileWriter(getUsersPath() + username));
        writer.write(username + " " + password + " " + email);
        writer.close();

        writer = new BufferedWriter(new FileWriter(Paths.getEmailsPath(), true));
        writer.append(email);
        writer.close();
    }

}
