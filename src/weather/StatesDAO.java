package weather;

public interface StatesDAO {
    /**
     * Retrieves a States with data from the server.
     *
     * @return a States.
     *
     * @throws DAOException if error occurs while connecting to the server.
     */
    States load() throws DAOException;
}
