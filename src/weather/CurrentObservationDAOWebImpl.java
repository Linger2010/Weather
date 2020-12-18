package weather;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentObservationDAOWebImpl
implements CurrentObservationDAO {

    /**
     * Represents the URL from which the weather data can be accessed.
     */
    private static final String WEATHER_URL = "https://w1.weather.gov/xml/current_obs/%s.xml";

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("CurrentObservationDAOWebImpl");

    /**
     * Gets a valid URL to access weather data of the
     * given station ID.
     *
     * @param stationId the given station ID.
     *
     * @return a valid URL.
     */
    private URL buildURL(String stationId) {
        Objects.requireNonNull(stationId);
        try {
            return new URL(String.format(WEATHER_URL, stationId));
        } catch(MalformedURLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IllegalArgumentException("Invalid station ID.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurrentObservation> get(String stationId) throws DAOException {

        try {
            XmlMapper mapper = new XmlMapper();
            var url = buildURL(stationId);
            var currentObservation = mapper.readValue(url, CurrentObservationTO.class);
            var location = currentObservation.location;
            var observationTime = currentObservation.observationTime;
            var observationTimeRfc822 = currentObservation.observationTimeRfc822;
            var temperature = currentObservation.temperatureString;
            var dewpoint = currentObservation.dewpointString;
            var humidity = currentObservation.relativeHumidity;
            var wind = currentObservation.windString;
            var pressure = currentObservation.pressureString;
            return Optional.of(new CurrentObservation.Builder(location, stationId)
                    .observationTime(observationTime)
                    .observationTimeRfc822(observationTimeRfc822)
                    .temperature(temperature)
                    .dewpoint(dewpoint)
                    .humidity(humidity)
                    .wind(wind)
                    .pressure(pressure)
                    .build());

        }catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}
