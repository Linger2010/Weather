package weather;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class WeatherModelDAOWebImpl
implements WeatherModelDAO {
    /**
     * Represents the URL from which the weather data can be accessed.
     */
    private static final String STATIONS_URL = "https://w1.weather.gov/xml/current_obs/index.xml";

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("WeatherModelDAOWebImpl");

    /**
     * It is used to access the data to initialize a weather model update.
     */
    private static final CurrentObservationDAO CURRENT_OBSERVATION_DAO = new CurrentObservationDAOWebImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Station>> getStations(String state) throws DAOException {
        List<Station> list = new ArrayList<>();
        try {
            XmlMapper mapper = new XmlMapper();
            var url = new URL(STATIONS_URL);
            var stations = mapper.readValue(url, WxStationIndexTO.class);

            for(var station : stations.station) {
                if(station.state.equals(state)) {
                    list.add(new Station(station.stationId, station.state, station.stationName));
                } // else the station is not in the given state doNothing();
            }

            if(list.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(list);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DAOException(ex.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurrentObservation> getCurrentObservation(String stationId) throws DAOException {
            return CurrentObservation.get(stationId);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStations(List<Station> stations) throws DAOException {
        throw new UnsupportedOperationException("Save Stations Not Supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() throws DAOException {
        throw new UnsupportedOperationException("Clear all data Not Supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> getAllStates() throws DAOException {
        throw new UnsupportedOperationException("Get all states Not Supported.");
    }
}
