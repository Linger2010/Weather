package weather;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

public class LoadCurrentObservation
implements Callable<Optional<CurrentObservation>> {

    /**
     * Represents the ID of the station.
     */
    private String stationId;

    /**
     * It is used to access the data to initialize a current observation update.
     */
    private static final CurrentObservationDAO DAO = new CurrentObservationDAOWebImpl();

    /**
     * Constructs a LoadCurrentObservation with a station ID.
     *
     * @param stationId the ID of the station.
     */
    public LoadCurrentObservation(String stationId) {
        this.setStationId(stationId);
    }

    /**
     * Gets the ID of the station of this LoadCurrentObservation.
     *
     * @return the ID of the station.
     */
    public String getStationId() {
        return this.stationId;
    }

    /**
     * Sets the ID of the station of this LoadCurrentObservation.
     *
     * @param stationId the given station ID.
     */
    public void setStationId(String stationId) {
        Objects.requireNonNull(stationId);
        this.stationId = stationId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurrentObservation> call() throws DAOException {
        return DAO.get(this.getStationId());
    }
}
