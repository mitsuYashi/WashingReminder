import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
data class WeatherData @JsonCreator constructor(
    @JsonProperty("location") val location: Location,
    @JsonProperty("current") val current: Current,
    @JsonProperty("forecast") val forecast: Forecast
)

data class Location @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("region") val region: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("lat") val lat: Double,
    @JsonProperty("lon") val lon: Double,
    @JsonProperty("tz_id") val tz_id: String,
    @JsonProperty("localtime_epoch") val localtime_epoch: Long,
    @JsonProperty("localtime") val localtime: String
)

data class Current @JsonCreator constructor(
    @JsonProperty("last_updated_epoch") val last_updated_epoch: Long,
    @JsonProperty("last_updated") val last_updated: String,
    @JsonProperty("temp_c") val temp_c: Double,
    @JsonProperty("temp_f") val temp_f: Double,
    @JsonProperty("is_day") val is_day: Int,
    @JsonProperty("condition") val condition: Condition,
    @JsonProperty("wind_mph") val wind_mph: Double,
    @JsonProperty("wind_kph") val wind_kph: Double,
    @JsonProperty("wind_degree") val wind_degree: Int,
    @JsonProperty("wind_dir") val wind_dir: String,
    @JsonProperty("pressure_mb") val pressure_mb: Double,
    @JsonProperty("pressure_in") val pressure_in: Double,
    @JsonProperty("precip_mm") val precip_mm: Double,
    @JsonProperty("precip_in") val precip_in: Double,
    @JsonProperty("humidity") val humidity: Int,
    @JsonProperty("cloud") val cloud: Int,
    @JsonProperty("feelslike_c") val feelslike_c: Double,
    @JsonProperty("feelslike_f") val feelslike_f: Double,
    @JsonProperty("vis_km") val vis_km: Double,
    @JsonProperty("vis_miles") val vis_miles: Double,
    @JsonProperty("uv") val uv: Double,
    @JsonProperty("gust_mph") val gust_mph: Double,
    @JsonProperty("gust_kph") val gust_kph: Double
)

data class Condition @JsonCreator constructor(
    @JsonProperty("text") val text: String,
    @JsonProperty("icon") val icon: String,
    @JsonProperty("code") val code: Int
)

data class Forecast @JsonCreator constructor(
    @JsonProperty("forecastday") val forecastday: List<ForecastDay>
)

data class ForecastDay @JsonCreator constructor(
    @JsonProperty("date") val date: String,
    @JsonProperty("date_epoch") val date_epoch: Long,
    @JsonProperty("day") val day: Day,
    @JsonProperty("astro") val astro: Astro,
    @JsonProperty("hour") val hour: List<Hour>
)

data class Day @JsonCreator constructor(
    @JsonProperty("maxtemp_c") val maxtemp_c: Double,
    @JsonProperty("maxtemp_f") val maxtemp_f: Double,
    @JsonProperty("mintemp_c") val mintemp_c: Double,
    @JsonProperty("mintemp_f") val mintemp_f: Double,
    @JsonProperty("avgtemp_c") val avgtemp_c: Double,
    @JsonProperty("avgtemp_f") val avgtemp_f: Double,
    @JsonProperty("maxwind_mph") val maxwind_mph: Double,
    @JsonProperty("maxwind_kph") val maxwind_kph: Double,
    @JsonProperty("totalprecip_mm") val totalprecip_mm: Double,
    @JsonProperty("totalprecip_in") val totalprecip_in: Double,
    @JsonProperty("totalsnow_cm") val totalsnow_cm: Double,
    @JsonProperty("totalsnow_in") val totalsnow_in: Double,
    @JsonProperty("avgvis_km") val avgvis_km: Double,
    @JsonProperty("avgvis_miles") val avgvis_miles: Double,
    @JsonProperty("avghumidity") val avghumidity: Double,
    @JsonProperty("daily_will_it_rain") val daily_will_it_rain: Int,
    @JsonProperty("daily_chance_of_rain") val daily_chance_of_rain: Int,
    @JsonProperty("daily_will_it_snow") val daily_will_it_snow: Int,
    @JsonProperty("daily_chance_of_snow") val daily_chance_of_snow: Int,
    @JsonProperty("condition") val condition: Condition,
    @JsonProperty("uv") val uv: Double
)

data class Astro @JsonCreator constructor(
    @JsonProperty("sunrise") val sunrise: String,
    @JsonProperty("sunset") val sunset: String,
    @JsonProperty("moonrise") val moonrise: String,
    @JsonProperty("moonset") val moonset: String,
    @JsonProperty("moon_phase") val moon_phase: String,
    @JsonProperty("moon_illumination") val moon_illumination: String,
    @JsonProperty("is_moon_up") val is_moon_up: Int,
    @JsonProperty("is_sun_up") val is_sun_up: Int
)

data class Hour @JsonCreator constructor(
    @JsonProperty("time_epoch") val time_epoch: Long,
    @JsonProperty("time") val time: String,
    @JsonProperty("temp_c") val temp_c: Double,
    @JsonProperty("temp_f") val temp_f: Double,
    @JsonProperty("is_day") val is_day: Int,
    @JsonProperty("condition") val condition: Condition,
    @JsonProperty("wind_mph") val wind_mph: Double,
    @JsonProperty("wind_kph") val wind_kph: Double,
    @JsonProperty("wind_degree") val wind_degree: Int,
    @JsonProperty("wind_dir") val wind_dir: String,
    @JsonProperty("pressure_mb") val pressure_mb: Double,
    @JsonProperty("pressure_in") val pressure_in: Double,
    @JsonProperty("precip_mm") val precip_mm: Double,
    @JsonProperty("precip_in") val precip_in: Double,
    @JsonProperty("humidity") val humidity: Int,
    @JsonProperty("cloud") val cloud: Int,
    @JsonProperty("feelslike_c") val feelslike_c: Double,
    @JsonProperty("feelslike_f") val feelslike_f: Double,
    @JsonProperty("windchill_c") val windchill_c: Double,
    @JsonProperty("windchill_f") val windchill_f: Double,
    @JsonProperty("heatindex_c") val heatindex_c: Double,
    @JsonProperty("heatindex_f") val heatindex_f: Double,
    @JsonProperty("dewpoint_c") val dewpoint_c: Double,
    @JsonProperty("dewpoint_f") val dewpoint_f: Double,
    @JsonProperty("will_it_rain") val will_it_rain: Int,
    @JsonProperty("chance_of_rain") val chance_of_rain: Int,
    @JsonProperty("will_it_snow") val will_it_snow: Int,
    @JsonProperty("chance_of_snow") val chance_of_snow: Int,
    @JsonProperty("vis_km") val vis_km: Double,
    @JsonProperty("vis_miles") val vis_miles: Double,
    @JsonProperty("gust_mph") val gust_mph: Double,
    @JsonProperty("gust_kph") val gust_kph: Double,
    @JsonProperty("uv") val uv: Double
)
