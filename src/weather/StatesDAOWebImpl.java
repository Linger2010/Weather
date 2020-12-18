package weather;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatesDAOWebImpl
implements StatesDAO {
    /**
     * Represents the URL from which the stations data can be accessed.
     */
    private static final String STATIONS_URL = "https://w1.weather.gov/xml/current_obs/index.xml";

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("StatesDAOWebImpl");

    /**
     * {@inheritDoc}
     */
    @Override
    public States load() throws DAOException {
        var states = new ArrayList<String>();
        try {
            XmlMapper mapper = new XmlMapper();
            var url = new URL(STATIONS_URL);
            var stations = mapper.readValue(url, WxStationIndexTO.class);

            for(var station : stations.station) {
                if(!states.contains(station.state)) {
                    states.add(station.state);
                } // else the state is in the set doNothing();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DAOException(ex.getMessage());
        }
        return new States(states);
    }
}
