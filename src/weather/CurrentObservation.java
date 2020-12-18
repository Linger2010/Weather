package weather;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CurrentObservation {

    /**
     * Represents the location of the station.
     */
    private String location;

    /**
     * Represents the ID of the station.
     */
    private String stationId;

    /**
     * Represents the latest observation time of the weather.
     */
    private String observationTime;

    /**
     * Represents an RFC-822 form of the observation time.
     */
    private String observationTimeRfc822;

    /**
     * Represents the temperature of the weather update.
     */
    private String temperature;

    /**
     * Represents the dewpoint of the weather update.
     */
    private String dewpoint;

    /**
     * Represents the humidity of the weather update.
     */
    private String humidity;

    /**
     * Represents the wind information of the weather update.
     */
    private String wind;

    /**
     * Represents the pressure conditions of the weather update.
     */
    private String pressure;

    /**
     * It is used to access the data to initialize a weather update.
     */
    private static final CurrentObservationDAO DAO = new CurrentObservationDAOWebImpl();

    /**
     * Represents a Logger retrieved by the Manager class.
     */
    private static final Logger LOG = Manager.getLogger("CurrentObservation");

    public static final CurrentObservation EMPTY
            = new CurrentObservation.Builder("", "")
            .observationTime("")
            .observationTimeRfc822("")
            .temperature("")
            .dewpoint("")
            .humidity("")
            .wind("")
            .pressure("")
            .build();

    /**
     * A Builder class to initialize all weather data from user input.
     */
    public static class Builder {
        /**
         * Represents the location of the station.
         */
        private String location;

        /**
         * Represents the ID of the station.
         */
        private String stationId;

        /**
         * Represents the latest observation time of the weather.
         */
        private String observationTime = "";

        /**
         * Represents an RFC-822 form of the observation time.
         */
        private String observationTimeRfc822 = "";

        /**
         * Represents the temperature of the weather update.
         */
        private String temperature = "";

        /**
         * Represents the dewpoint of the weather update.
         */
        private String dewpoint = "";

        /**
         * Represents the humidity of the weather update.
         */
        private String humidity = "";

        /**
         * Represents the wind information of the weather update.
         */
        private String wind = "";

        /**
         * Represents the pressure conditions of the weather update.
         */
        private String pressure = "";

        /**
         * Constructs a Builder with a given location and a given
         * station ID. The location and station ID cannot be null.
         *
         * @param location  the location of the station.
         * @param stationId the ID of the station.
         */
        public Builder(String location, String stationId) {
            this.setLocation(location);
            this.setStationId(stationId);
        }

        /**
         * Sets the location of the station with a given location.
         * The given location cannot be null.
         *
         * @param location the given location.
         */
        private void setLocation(String location) {
            try {
                this.location = location;
            } catch (NullPointerException ex) {
                this.location = "Not provided.";
            }
        }

        /**
         * Sets the ID of the station with the given ID.
         * The given ID cannot be null.
         *
         * @param stationId the give ID.
         */
        private void setStationId(String stationId) {
            try {
                this.stationId = stationId;
            } catch (NullPointerException ex) {
                this.stationId = "Not provided.";
            }
        }

        /**
         * Sets the observation time for the builder. The observation
         * time cannot be null. If it is not set, it stays empty.
         *
         * @param observationTime the given observation time.
         * @return a Builder with its observation time set.
         */
        public Builder observationTime(String observationTime) {
            try {
                this.observationTime = observationTime;
            } catch (NullPointerException ex) {
                this.observationTime = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the RFC-822 form of the observation time with the given
         * time. If it is not set, it stays empty.
         *
         * @param observationTimeRfc822 the given observation time.
         * @return a Builder with its RFC-822 observation time set.
         */
        public Builder observationTimeRfc822(String observationTimeRfc822) {
            try {
                this.observationTimeRfc822 = observationTimeRfc822;
            } catch (NullPointerException ex) {
                this.observationTimeRfc822 = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the temperature with the given temperature. The given temperature
         * cannot be null. If it is not set, it stays empty.
         *
         * @param temperature the given temperature
         * @return a Builder with its temperature set.
         */
        public Builder temperature(String temperature) {
            try {
                this.temperature = temperature;
            } catch (NullPointerException ex) {
                this.temperature = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the temperature with the given dewpoint. The given dewpoint
         * cannot be null. If it is not set, it stays empty.
         *
         * @param dewpoint the given dewpoint
         * @return a Builder with its dewpoint set.
         */
        public Builder dewpoint(String dewpoint) {
            try {
                this.dewpoint = dewpoint;
            } catch (NullPointerException ex) {
                this.dewpoint = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the humidity with the given humidity. The given humidity
         * cannot be null. If it is not set, it stays empty.
         *
         * @param humidity the given humidity
         * @return a Builder with its humidity set.
         */
        public Builder humidity(String humidity) {
            try {
                this.humidity = humidity;
            } catch (NullPointerException ex) {
                this.humidity = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the wind with the given wind. The given wind
         * cannot be null. If it is not set, it stays empty.
         *
         * @param wind the given wind
         * @return a Builder with its wind set.
         */
        public Builder wind(String wind) {
            try {
                this.wind = wind;
            } catch (NullPointerException ex) {
                this.wind = "Not provided.";
            }
            return this;
        }

        /**
         * Sets the pressure with the given pressure. The given pressure
         * cannot be null. If it is not set, it stays empty.
         *
         * @param pressure the given pressure
         * @return a Builder with its pressure set.
         */
        public Builder pressure(String pressure) {
            try {
                this.pressure = pressure;
            } catch (NullPointerException ex) {
                this.pressure = "Not provided.";
            }
            return this;
        }

        /**
         * Builds a Weather update with this Build.
         *
         * @return a Weather.
         */
        public CurrentObservation build() {
            return new CurrentObservation(this);
        }
    }

    /**
     * Constructs a Weather update with a Builder.
     *
     * @param builder a given Builder.
     */
    private CurrentObservation(Builder builder) {
        this.location = builder.location;
        this.stationId = builder.stationId;
        this.observationTime = builder.observationTime;
        this.observationTimeRfc822 = builder.observationTimeRfc822;
        this.temperature = builder.temperature;
        this.dewpoint = builder.dewpoint;
        this.humidity = builder.humidity;
        this.wind = builder.wind;
        this.pressure = builder.pressure;
    }

    /**
     * Gets a Weather from the data source with the given station
     * ID.
     *
     * @param stationId the given station ID.
     * @return a Weather update with the matching station ID.
     */
    public static Optional<CurrentObservation> get(String stationId) {
        Objects.requireNonNull(stationId);
        try {
            return DAO.get(stationId);
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new DataAccessException("Cannot connect to weather conditions. Contact support.");
        }
    }

    /**
     * Gets the location of the station.
     *
     * @return the location of the station.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the ID of the station.
     *
     * @return the ID of the station.
     */
    public String getStationId() {
        return this.stationId;
    }

    /**
     * Gets the observation time of the weather update.
     *
     * @return the observation time.
     */
    public String getObservationTime() {
        return this.observationTime;
    }

    /**
     * Gets the RFC-822 form of the observation time.
     *
     * @return the RFC-822 form of the observation time.
     */
    public String getObervationTimeRfc822() {
        return this.observationTimeRfc822;
    }

    /**
     * Gets the temperature in the weather update.
     *
     * @return the temperature
     */
    public String getTemperature() {
        return this.temperature;
    }

    /**
     * Gets the dewpoint in the weather update.
     *
     * @return the dewpoint.
     */
    public String getDewpoint() {
        return this.dewpoint;
    }

    /**
     * Gets the humidity in the weather update.
     *
     * @return the humidity.
     */
    public String getHumidity() {
        return this.humidity;
    }

    /**
     * Gets the wind conditions in the weather update.
     *
     * @return the wind conditions.
     */
    public String getWind() {
        return this.wind;
    }

    /**
     * Gets the pressure conditions in the weather update.
     *
     * @return the pressure.
     */
    public String getPressure() {
        return this.pressure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        if(!this.equals(EMPTY)) {
            builder.append(String.format(
                    "%s(%s)%s",
                    this.getLocation(),
                    this.getStationId(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "Last Update: %s%s",
                    this.getObservationTime(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "             %s%s",
                    this.getObervationTimeRfc822(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "Temperature: %s%s",
                    this.getTemperature(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "Dewpoint: %s%s",
                    this.getDewpoint(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "Relative Humidity: %s%%%s",
                    this.getHumidity(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "Wind: %s%s",
                    this.getWind(),
                    System.lineSeparator()));
            builder.append(String.format(
                    "MSL Pressure: %s%s",
                    this.getPressure(),
                    System.lineSeparator()));
        } // else there is nothing to display doNothing();
        return builder.toString();
    }
}
