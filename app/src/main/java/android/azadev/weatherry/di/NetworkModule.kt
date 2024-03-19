package android.azadev.weatherry.di

import android.azadev.weatherry.BuildConfig
import android.azadev.weatherry.data.remote.api.WeatherApi
import android.azadev.weatherry.data.repository.ProdWeatherRepository
import android.azadev.weatherry.domain.repository.WeatherRepository
import android.azadev.weatherry.ui.home.viewmodel.HomeViewModel
import android.azadev.weatherry.utils.Constants
import android.azadev.weatherry.utils.NetworkHelper
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = Level.BODY
    OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
} else OkHttpClient.Builder().build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build()

private fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)

val networkModule = module {

    single {
        provideNetworkHelper(get())
    }

    single {
        provideOkHttpClient()
    }

    single {
        provideRetrofit(get())
    }

    single {
        provideApiService(get())
    }

    single<WeatherRepository> {
        ProdWeatherRepository(get())
    }

    viewModel {
        HomeViewModel(get(), get())
    }
}