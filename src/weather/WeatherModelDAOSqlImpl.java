package weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherModelDAOSqlImpl
implements WeatherModelDAO {
    /**
     * Represents the connection string from which the weather data can be accessed.
     */
    public static final String CONNECTION_STRING = "jdbc:sqlite:weather.sqlite";

    /**
     * A database query that selects all stations with a state.
     */
    private static final String SELECT_STATIONS_BY_STATE
            = "SELECT STATION_ID, STATE, STATION_NAME FROM STATIONS WHERE STATE = ?";

    /**
     * A database query that inserts a record in the table.
     */
    private static final String INSERT_STATION_QUERY
            = "INSERT INTO STATIONS(STATION_ID, STATE, STATION_NAME) VALUES(?, ?, ?)";

    /**
     * A database query that deletes all records in the table.
     */
    private static final String DELETE_ALL_QUERY
            = "DELETE FROM STATIONS";

    /**
     * A database query that selects all states in the table.
     */
    private static final String SELECT_STATES_QUERY
            = "SELECT DISTINCT STATE FROM STATIONS";


    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("WeatherModelDAOSqlImpl");

    /**
     * Gets a database connection to a sqlite file.
     *
     * @return a connection to a sqlite file.
     *
     * @throws SQLException if database error occurs.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }

    /**
     * Retrieves an Optional with a list of stations from a
     * result set of stations data.
     *
     * @param resultSet a resultSet with stations data.
     *
     * @return an Optional with a list of stations
     *
     * @throws SQLException if errors occur when accessing the
     * database.
     */
    private Optional<List<Station>> parseStations(ResultSet resultSet) throws SQLException {
        var stations = new ArrayList<Station>();
        while(resultSet.next()) {
            var stationId = resultSet.getString("STATION_ID");
            var state = resultSet.getString("STATE");
            var stationName = resultSet.getString("STATION_NAME");
            stations.add(new Station(stationId, state, stationName));
        }
        if(stations.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(stations);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Station>> getStations(String state) throws DAOException {
        Objects.requireNonNull(state);
        try(var connection = this.getConnection();
            var statement = connection.prepareStatement(SELECT_STATIONS_BY_STATE)) {
            statement.setString(1, state);
            LOG.log(Level.INFO, String.format("Getting stations in the state: %s.", state));
            return parseStations(statement.executeQuery());
        } catch (SQLException sQLEx) {
            LOG.log(Level.SEVERE, sQLEx.getMessage(), sQLEx);
            throw new DAOException(sQLEx.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurrentObservation> getCurrentObservation(String stationId) throws DAOException {
        throw new UnsupportedOperationException("Get current observation Not Supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStations(List<Station> stations) throws DAOException {
        try(var connection = this.getConnection();
            var statement = connection.prepareStatement(INSERT_STATION_QUERY)) {
            for(var station : stations) {
                statement.setString(1, station.getStationId());
                statement.setString(2, station.getState());
                statement.setString(3, station.getStationName());

                LOG.log(Level.INFO, String.format("Saving station with station ID: %s", station.getStationId()));
                statement.executeUpdate();
            }
        } catch (SQLException sQLEx) {
            LOG.log(Level.SEVERE, sQLEx.getMessage(), sQLEx);
            throw new DAOException(sQLEx.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() throws DAOException {
        try (var connection = this.getConnection();
             var statement = connection.prepareStatement(DELETE_ALL_QUERY)) {
            LOG.log(Level.INFO, "Deleting all data.");
            statement.executeUpdate();
        } catch (SQLException sQLEx) {
            LOG.log(Level.SEVERE, sQLEx.getMessage(), sQLEx);
            throw new DAOException(sQLEx.getMessage());
        }
    }

    /**
     * Retrieves an Optional with a list of state names from a
     * result set.
     *
     * @param resultSet a resultSet with state data.
     *
     * @return an Optional with a list of state names
     *
     * @throws SQLException if errors occur when accessing the
     * database.
     */
    private Optional<List<String>> parseStates(ResultSet resultSet) throws SQLException {
        var states = new ArrayList<String>();
        while(resultSet.next()) {
            states.add(resultSet.getString("STATE"));
        }
        if(states.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(states);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> getAllStates() throws DAOException {

        try(var connection = this.getConnection();
            var statement = connection.prepareStatement(SELECT_STATES_QUERY)){
            LOG.log(Level.INFO, String.format("Getting all states."));
            return parseStates(statement.executeQuery());
        } catch (SQLException sQLEx) {
            LOG.log(Level.SEVERE, sQLEx.getMessage(), sQLEx);
            throw new DAOException(sQLEx.getMessage());
        }
    }

}
