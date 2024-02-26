package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.domain.SearchDataLocal
import co.develhope.meteoapp.data.domain.toSearchDataLocal
import co.develhope.meteoapp.data.dto.SearchDataRemote
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.OffsetDateTime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchRepo @Inject constructor(private val service: SearchService) :SearchService {


    suspend fun getSearch(
        cityName: String
    ): SearchDataLocal? {
        val response = service.getSearchCity(cityName, 10, "it")
        return response.toSearchDataLocal()
    }

    override suspend fun getSearchCity(
        name: String,
        count: Int,
        language: String
    ): Response<SearchDataRemote> {
       val response = service.getSearchCity(name, count, language)
       return response
    }


}
