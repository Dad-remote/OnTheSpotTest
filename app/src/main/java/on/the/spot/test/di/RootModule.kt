package on.the.spot.test.di

import android.app.Application
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import on.the.spot.test.db.TestDatabase
import on.the.spot.test.notifications.NotificationHelper
import on.the.spot.test.repository.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RootModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TestDatabase = TestDatabase.build(application)

    @Provides
    @Singleton
    fun provideMainRepository(database: TestDatabase, coroutineScope: CoroutineScope, workManager: WorkManager): MainRepository {
        return MainRepository(database, coroutineScope, workManager)
    }

    @Singleton
    @Provides
    fun provideCoroutineExceptionHandler() : CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(
        exceptionHandler: CoroutineExceptionHandler
    ) : CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
    }

    @Singleton
    @Provides
    fun provideWorkManager(
        application: Application
    ): WorkManager {
        return WorkManager.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideNotificationHelper(
        application: Application
    ): NotificationHelper {
        return NotificationHelper(application)
    }
}
