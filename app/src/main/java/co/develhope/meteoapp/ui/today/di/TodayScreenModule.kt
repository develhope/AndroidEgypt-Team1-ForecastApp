package co.develhope.meteoapp.ui.today.di

import co.develhope.meteoapp.network.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object TodayScreenModule {
    @Singleton
    @Provides
    fun providesWeatherRepo()=WeatherRepo()

}