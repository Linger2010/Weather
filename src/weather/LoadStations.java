package weather;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

public class LoadStations
implements Callable<Optional<List<Station>>> {

    /**
     * Represents the state of the stations.
     */
    private String state;

    /**
     * It is used to access the data to initialize a weather model update.
     */
    private static final WeatherModelDAO DAO = new WeatherModelDAOWebImpl();

    /**
     * Constructs a LoadStations with a given state.
     *
     * @param state a given state.
     */
    public LoadStations(String state) {
        this.setState(state);
    }

    /**
     * Gets the state of this LoadStations.
     *
     * @return the state of this LoadStations.
     */
    public String getState() {
        return this.state;
    }

    /**
     * Sets the state of this LoadStations with the given state.
     *
     * @param state the given state.
     */
    private void setState(String state) {
        Objects.requireNonNull(state);
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Station>> call() throws DAOException {
        return DAO.getStations(this.getState());
    }
}
