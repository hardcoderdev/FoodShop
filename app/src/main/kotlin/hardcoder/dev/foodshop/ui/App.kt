package hardcoder.dev.foodshop.ui

import android.app.Application
import hardcoder.dev.foodshop.di.databaseModule
import hardcoder.dev.foodshop.di.networkModule
import hardcoder.dev.foodshop.di.repositoriesModule
import hardcoder.dev.foodshop.di.useCasesModule
import hardcoder.dev.foodshop.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule(this@App),
                useCasesModule,
                repositoriesModule,
                viewModelsModule
            )
        }
    }
}