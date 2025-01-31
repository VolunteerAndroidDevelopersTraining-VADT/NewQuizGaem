package ali.hrhera.newquizgaem

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class NeqQuizApp : Application(), ViewModelStoreOwner, Configuration.Provider {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory())
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .build()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    companion object {
        private val appViewModelStore: ViewModelStore by lazy {
            ViewModelStore()
        }
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }

}