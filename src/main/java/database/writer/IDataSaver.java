package database.writer;

import data_management.RegisteredUser;

import java.io.IOException;

/**
 * Interface for user data saving. It allows to switch painless through different data managing systems.
 *
 * @author Micieli
 * @author Noris
 * @date 2015/04/17
 */
public interface IDataSaver {

    /**
     * Permanently save user data.
     *
     * @param user the user to save
     * @throws IOException throw if there is a communication problem with the data managing system
     */
    void saveData(RegisteredUser user) throws IOException;

}