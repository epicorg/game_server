package database.writer;

import data_management.RegisteredUser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves e-mails of the registration in a specific file.
 *
 * @author Micieli
 * @date 17/04/2015
 */
public class EmailSaver implements IDataSaver {

    private final String filename;
    private final ILineFormatter lineFormatter;

    /**
     * @param filename      the name of the file where to save the e-mail
     * @param lineFormatter a line formatter
     */
    public EmailSaver(String filename, ILineFormatter lineFormatter) {
        super();
        this.filename = filename;
        this.lineFormatter = lineFormatter;
    }

    @Override
    public void saveData(RegisteredUser user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.append(lineFormatter.formatLine(user));
        writer.close();
    }

}
