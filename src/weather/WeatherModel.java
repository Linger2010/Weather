package weather;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WeatherModel {

    /**
     * Represents the currently chosen state.
     */
    private String state;

    /**
     * Represents the list of stations from the chosen state.
     */
    private List<Station> stations;

    /**
     * Represents the current observation of the chosen station.
     */
    private CurrentObservation currentObservation;

    /**
     * It is used to access the data to initialize a weather model update.
     */
    private final static WeatherModelDAO LOCAL_DAO = new WeatherModelDAOSqlImpl();

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("WeatherModel");

    /**
     * Constructs an empty WeatherModel.
     */
    public WeatherModel() {
        this.setState("");
        this.setStations(new ArrayList<Station>());
        this.currentObservation = CurrentObservation.EMPTY;
    }

    /**
     * Gets a list of stations from the given state. The returned
     * list is retrieved from local files; if not found in these
     * files, it is downloaded from the server.
     *
     * @param state the given state.
     *
     * @return a list of stations from the given state.
     */
    public static List<Station> getStations(String state) {
        Objects.requireNonNull(state);
        try{
            var list = LOCAL_DAO.getStations(state);
            if(list.isPresent()) {
                var result = list.get();
                return result;
            } else{
                var executor = Executors.newSingleThreadExecutor();
                var result = executor.submit(new LoadStations(state)).get().get();
                Collections.sort(result);
                saveStations(result);
                return result;
            }
        } catch (DAOException | InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            System.out.println(ex);
            throw new DataAccessException("Cannot access station data. Contact support.");
        }
    }

    /**
     * Gets a current observation with the given station ID from
     * the server.
     *
     * @param stationId the given station ID.
     *
     * @return the current observation with the given ID.
     */
    public static CurrentObservation getCurrentObservation(String stationId) {
        Objects.requireNonNull(stationId);
        try {
            var executor = Executors.newSingleThreadExecutor();
            var result = executor.submit(new LoadCurrentObservation(stationId)).get().get();
            return result;
        } catch (InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException("Cannot connect to weather conditions. Contact support.");
        }
    }

    /**
     * Saves a given list of stations to the local files.
     *
     * @param stations a given list of stations.
     */
    public static void saveStations(List<Station> stations) {
        try{
            LOCAL_DAO.saveStations(stations);
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException("Cannot save stations. Contact support.");
        }
    }

    /**
     * Deletes all data in the local files.
     */
    public static void deleteAllLocalData() {
        try{
            LOCAL_DAO.clear();
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException("Cannot delete all data. Contact support.");
        }
    }

    /**
     * Gets all stations stored in the local files.
     *
     * @return a list of stations.
     */
    public static List<String> getAllLocalStates() {
        try{
            var list = LOCAL_DAO.getAllStates();
            if(list.isPresent()) {
                return list.get();
            } else {
                throw new DataAccessException("There is no local state.");
            }
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException("Cannot connect to the local data. Contact support.");
        }
    }

    /**
     * Gets the state of this WeatherModel.
     *
     * @return the current state.
     */
    public String getState() {
        return this.state;
    }

    /**
     * Sets the state of this WeatherModel with the given
     * state.
     *
     * @param state a given state.
     */
    public void setState(String state) {
        Objects.requireNonNull(state);
        this.state = state;
    }

    /**
     * Gets the list of stations of this WeatherModel.
     *
     * @return a list of stations.
     */
    public List<Station> getStations() {
        return new ArrayList<>(this.stations);
    }

    /**
     * Sets the stations of this WeatherModel with the
     * given list.
     *
     * @param stations a given list of stations.
     */
    public void setStations(List<Station> stations) {
        Objects.requireNonNull(stations);
        this.stations = Collections.unmodifiableList(stations);
    }

    /**
     * Gets the current observation of this WeatherModel.
     *
     * @return the current observation of this WeatherModel
     */
    public CurrentObservation getCurrentObservation() {
        return this.currentObservation;
    }

    /**
     * Sets the current observation of this WeatherModel with
     * the given current observation.
     *
     * @param currentObservation a currentObservation
     */
    public void setCurrentObservation(CurrentObservation currentObservation) {
        Objects.requireNonNull(currentObservation);
        this.currentObservation = currentObservation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherModel)) return false;
        WeatherModel that = (WeatherModel) o;
        return Objects.equals(this.state, that.state) &&
                Objects.equals(this.stations, that.stations) &&
                Objects.equals(this.currentObservation, that.currentObservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.state, this.stations, this.currentObservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherModel{");
        sb.append("state='").append(state).append('\'');
        sb.append(", stations=").append(stations).append('\n');
        sb.append(", currentObservation=").append(currentObservation);
        sb.append('}');
        return sb.toString();
    }
}
