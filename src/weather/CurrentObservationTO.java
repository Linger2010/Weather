package weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Represents a transfer object that models all information of a weather station.
 */
@JacksonXmlRootElement(namespace = "http://www.w3.org/2001/XMLSchema", localName = "current_observation")
public class CurrentObservationTO {

    @JacksonXmlProperty(isAttribute = true)
    public String version;

    @JacksonXmlProperty(isAttribute = true, namespace = "xsi")
    public String noNamespaceSchemaLocation;

    public String credit;

    @JsonProperty("credit_URL")
    public String creditURL;

    public ImageTO image;

    @JsonProperty("suggested_pickup")
    public String suggestedPickup;

    @JsonProperty("suggested_pickup_period")
    public String suggestedPickupPeriod;

    public String location;

    @JsonProperty("station_id")
    public String stationId;

    public String latitude;

    public String longitude;

    public String elevation;

    @JsonProperty("observation_time")
    public String observationTime;

    @JsonProperty("observation_time_rfc822")
    public String observationTimeRfc822;

    public String weather;

    @JsonProperty("temperature_string")
    public String temperatureString;


    @JsonProperty("temp_f")
    public String tempF;

    @JsonProperty("temp_c")
    public String tempC;

    @JsonProperty("water_temp_f")
    public String waterTempF;

    @JsonProperty("water_temp_c")
    public String waterTempC;

    @JsonProperty("relative_humidity")
    public String relativeHumidity;

    @JsonProperty("wind_string")
    public String windString;

    @JsonProperty("wind_dir")
    public String windDir;

    @JsonProperty("wind_degrees")
    public String windDegrees;

    @JsonProperty("wind_mph")
    public String windMph;

    @JsonProperty("wind_gust_mph")
    public String windGustMph;

    @JsonProperty("wind_kt")
    public String windKt;

    @JsonProperty("wind_gust_kt")
    public String windGustKt;

    @JsonProperty("pressure_string")
    public String pressureString;

    @JsonProperty("pressure_mb")
    public String pressureMb;

    @JsonProperty("pressure_in")
    public String pressureIn;

    @JsonProperty("pressure_tendency_mb")
    public String pressureTendencyMb;

    @JsonProperty("pressure_tendency_in")
    public String pressureTendencyIn;

    @JsonProperty("dewpoint_string")
    public String dewpointString;

    @JsonProperty("dewpoint_f")
    public String dewpointF;

    @JsonProperty("dewpoint_c")
    public String dewpointC;

    @JsonProperty("heat_index_string")
    public String heatIndexString;

    @JsonProperty("heat_index_f")
    public String heatIndexF;

    @JsonProperty("heat_index_c")
    public String heatIndexC;

    @JsonProperty("windchill_string")
    public String windchillString;

    @JsonProperty("windchill_f")
    public String windchillF;

    @JsonProperty("windchill_c")
    public String windchillC;

    @JsonProperty("visibility_mi")
    public String visibilityMi;

    @JsonProperty("wave_height_m")
    public String waveHeightM;

    @JsonProperty("wave_height_ft")
    public String waveHeightFt;

    @JsonProperty("dominant_period_sec")
    public String dominantPeriodSec;

    @JsonProperty("average_period_sec")
    public String averagePeriodSec;

    @JsonProperty("mean_wave_dir")
    public String meanWaveDir;

    @JsonProperty("mean_wave_degrees")
    public String meanWaveDegrees;

    @JsonProperty("tide_ft")
    public String tideFt;

    public String steepness;

    @JsonProperty("water_column_height")
    public String waterColumnHeight;

    @JsonProperty("surf_height_ft")
    public String surfHeightFt;

    @JsonProperty("swell_dir")
    public String swellDir;

    @JsonProperty("swell_degrees")
    public String swellDegrees;

    @JsonProperty("swell_period")
    public String swellPeriod;

    @JsonProperty("icon_url_base")
    public String iconUrlBase;

    @JsonProperty("icon_name")
    public String iconName;

    @JsonProperty("two_day_history_url")
    public String twoDayHistoryUrl;

    @JsonProperty("icon_url_name")
    public String iconUrlName;

    @JsonProperty("ob_url")
    public String obUrl;

    @JsonProperty("disclaimer_url")
    public String disclaimerUrl;

    @JsonProperty("copyright_url")
    public String copyrightUrl;

    @JsonProperty("privacy_policy_url")
    public String privacyPolicyUrl;

}
