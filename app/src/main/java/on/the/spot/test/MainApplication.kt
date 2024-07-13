package on.the.spot.test

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import on.the.spot.test.notifications.NotificationHelper
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper.createChannel()
    }
    override fun getWorkManagerConfiguration(): Configuration {
        Log.d("Dad", "workerFactory: $workerFactory")
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
