package weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

/**
 * Represents a transfer object that models a list of all NOAA stations.
 */
public class WxStationIndexTO {

    public String credit;

    @JsonProperty("credit_URL")
    public String creditURL;

    public ImageTO image;

    @JsonProperty("suggested_pickup")
    public String suggestedPickup;

    @JsonProperty("suggested_pickup_period")
    public String suggestedPickupPeriod;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<StationTO> station;

}
