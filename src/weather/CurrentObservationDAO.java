package weather;

import java.util.Optional;

public interface CurrentObservationDAO {
    /**
     * Gets a Weather update from the existing data source
     * that matches the given station ID.
     *
     * @param stationId the given ID of a station.
     *
     * @return a Weather update of the given station ID.
     *
     * @throws DAOException if errors occur while accessing the
     * data.
     */
    Optional<CurrentObservation> get(String stationId) throws DAOException;
}
