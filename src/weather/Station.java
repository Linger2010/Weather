package weather;

import javafx.scene.control.ComboBox;

import java.util.Objects;
import java.util.logging.Logger;

public final class Station
implements Comparable<Station> {
    /**
     * Represents the ID of the station. Cannot be null.
     */
    private String stationId;

    /**
     * Represents the abbreviation of the state. Cannot be null.
     */
    private String state;

    /**
     * Represents the full name of the station. Cannot be null.
     */
    private String stationName;

    /**
     * Represents a Logger retrieved by the Manager class.
     */

    /**
     * Constructs a station with an ID, state abbreviation, and the full
     * name of the station. None of these values can be null.
     *
     * @param stationId the ID of the station. Cannot be null.
     *
     * @param state the abbreviation of the state. Cannot be null.
     *
     * @param stationName the full name of the station. Cannot be null.
     *
     * @throws NullPointerException if any of the given values is null.
     */
    public Station(String stationId, String state, String stationName) {
        this.setStationId(stationId);
        this.setState(state);
        this.setStationName(stationName);
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
     * Sets the ID of the station. The ID cannot be null.
     *
     * @param stationId the given ID.
     *
     * @throws NullPointerException if the given ID is null.
     */
    private void setStationId(String stationId) {
        Objects.requireNonNull(stationId);
        this.stationId = stationId;
    }

    /**
     * Gets the state of the station.
     *
     * @return the state of the station.
     */
    public String getState() {
        return this.state;
    }

    /**
     * Sets the state of the station with a given state. The
     * given state cannot be null.
     *
     * @param state the given state.
     *
     * @throws NullPointerException if the given state is null.
     */
    private void setState(String state) {
        Objects.requireNonNull(state);
        this.state = state;
    }

    /**
     * Gets the full name of the station.
     *
     * @return the full name of the station.
     */
    public String getStationName() {
        return this.stationName;
    }

    /**
     * Sets the full name of the station with the given name.
     * The given name cannot be null.
     *
     * @param stationName the given name.
     *
     * @throws NullPointerException if the given name is null.
     */
    private void setStationName(String stationName) {
        Objects.requireNonNull(stationName);
        this.stationName = stationName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return Objects.equals(this.stationId, station.stationId) &&
                Objects.equals(this.state, station.state) &&
                Objects.equals(this.stationName, station.stationName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.stationId, this.state, this.stationName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Station o) {
        Objects.requireNonNull(o);
        return this.getStationName().compareTo(o.getStationName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append(this.stationName)
                .append(" (")
                .append(this.stationId)
                .append(")");
        return builder.toString();
    }
}
