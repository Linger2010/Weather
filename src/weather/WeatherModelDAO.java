package weather;

import java.util.List;
import java.util.Optional;

public interface WeatherModelDAO {
    /**
     * Retrieves a list of stations from the given state.
     *
     * @param state a given state
     *
     * @return a list of stations
     *
     * @throws DAOException if error occurs while connecting.
     */
    Optional<List<Station>> getStations(String state) throws DAOException;

    /**
     * Retreives a current observation with the given station ID.
     *
     * @param stationId a given station ID.
     *
     * @return a current observation with the given ID.
     *
     * @throws DAOException if error occurs while connecting.
     */
    Optional<CurrentObservation> getCurrentObservation(String stationId) throws DAOException;

    /**
     * Saves the given list of stations.
     *
     * @param stations a list of stations.
     *
     * @throws DAOException if error occurs while connecting.
     */
    void saveStations(List<Station> stations) throws DAOException;

    /**
     * Clears all data which has been saved.
     *
     * @throws DAOException if error occurs while connecting.
     */
    void clear() throws DAOException;

    /**
     * Retrieves a complete list of states.
     *
     * @return a list of states.
     *
     * @throws DAOException if error occurs while connecting.
     */
    Optional<List<String>> getAllStates() throws DAOException;

}
