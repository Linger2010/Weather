package weather;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a transfer object that models a station.
 */
public class StationTO {

    @JsonProperty("station_id")
    public String stationId;

    public String state;

    @JsonProperty("station_name")
    public String stationName;

    public String latitude;

    public String longitude;

    @JsonProperty("html_url")
    public String htmlUrl;

    @JsonProperty("rss_url")
    public String rssUrl;

    @JsonProperty("xml_url")
    public String xmlUrl;

}
