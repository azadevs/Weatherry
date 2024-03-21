package android.azadev.weatherry.application

import android.app.Application
import android.azadev.weatherry.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(appModule)
        }
    }
}