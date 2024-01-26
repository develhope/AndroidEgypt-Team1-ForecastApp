package co.develhope.meteoapp.data.dto


import com.google.gson.annotations.SerializedName

data class SearchDataRemote(
    @SerializedName("generationtime_ms")
    val generationtimeMs: Double?,
    @SerializedName("results")
    val results: List<Result?>?
)