package co.develhope.meteoapp.network.di

import co.develhope.meteoapp.network.OffsetDateTimeTypeAdapter
import co.develhope.meteoapp.network.SearchRepo
import co.develhope.meteoapp.network.SearchService
import co.develhope.meteoapp.network.TryCatchInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.OffsetDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun providesGson():Gson{
       return GsonBuilder()
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeTypeAdapter())
            .create()
    }
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
            .also { it.level = HttpLoggingInterceptor.Level.BODY }
    }
    @Provides
    @Singleton
    fun providesOkHttp(loggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(TryCatchInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,gson:Gson):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun providesSearchApi(retrofit: Retrofit):SearchService{
        return retrofit.create(SearchService::class.java)
    }
}