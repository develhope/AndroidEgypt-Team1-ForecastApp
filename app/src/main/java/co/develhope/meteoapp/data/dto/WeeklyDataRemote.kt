package co.develhope.meteoapp.data.dto
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import com.google.gson.annotations.SerializedName
import org.threeten.bp.OffsetDateTime
import retrofit2.Response


data class WeeklyDataRemote(
    @SerializedName("daily")
    val daily: Daily?,
    @SerializedName("daily_units")
    val dailyUnits: DailyUnits?,
    @SerializedName("elevation")
    val elevation: Int?,
    @SerializedName("generationtime_ms")
    val generationtimeMs: Double?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String?,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int?
)

data class Daily(
    @SerializedName("precipitation_sum")
    val precipitationSum: List<Double?>?,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double?>?,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double?>?,
    @SerializedName("time")
    val time: List<OffsetDateTime>?,
    @SerializedName("weathercode")
    val weathercode: List<Int?>?,
    @SerializedName("windspeed_10m_max")
    val windspeed10mMax: List<Double?>?
)

data class DailyUnits(
    @SerializedName("precipitation_sum")
    val precipitationSum: String?,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: String?,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: String?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("weathercode")
    val weathercode: String?,
    @SerializedName("windspeed_10m_max")
    val windspeed10mMax: String?
)
fun Response<WeeklyDataRemote>.toWeeklyDataLocal(): WeeklyDataLocal? {
    return if(this.isSuccessful){
        val response = this.body()

        val model = WeeklyDataLocal()


        response?.daily?.time?.let {
            it.forEachIndexed { index, s ->
                model.add(
                    WeeklyDataLocal.WeeklyLocal(
                        date = s,
                        minTemperature = response.daily.temperature2mMin?.getOrNull(index),
                        maxTemperature = response.daily.temperature2mMax?.getOrNull(index),
                        weatherIcon = response.daily.weathercode?.getOrNull(index),
                        precipitation = response.daily.precipitationSum?.getOrNull(index),
                        windSpeed = response.daily.windspeed10mMax?.getOrNull(index),
                    )
                )
            }
        }
        model
    } else {
        null
    }
}