package database.writer;

import data_management.RegisteredUser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves all user data of the registration in a file. In order
 * to speed up the checking phase, each user has its own file.
 *
 * @author Micieli
 * @author Gavina
 * @author Modica
 * @author Noris
 * @date 14/04/2015
 */
public class UserSaver implements IDataSaver {

    private final String path;
    private final ILineFormatter lineFormatter;

    /**
     * @param path          the path in which user files are saved
     * @param lineFormatter a line formatter
     */
    public UserSaver(String path, ILineFormatter lineFormatter) {
        super();
        this.path = path;
        this.lineFormatter = lineFormatter;
    }

    @Override
    public void saveData(RegisteredUser user) throws IOException {
        File file = new File(path + user.getUsername());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(lineFormatter.formatLine(user));
        writer.close();
    }

}
