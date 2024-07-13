package on.the.spot.test.repository

import android.util.Log
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import on.the.spot.test.db.TestDatabase
import on.the.spot.test.db.model.BootEntity
import on.the.spot.test.workers.NotificationWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainRepository(
    private val database: TestDatabase,
    private val coroutineScope: CoroutineScope,
    private val workManager: WorkManager,
) {

    companion object {
        val builder = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS
        )
    }

    fun saveNewBoot() {
        coroutineScope.launch {
            database.bootsDao().save(BootEntity(time = System.currentTimeMillis()))
            workManager.enqueue(builder.build())
        }
    }

    fun loadLastBootTime(): Flow<Long?> {
        return database.bootsDao().loadLast().map { it?.time }
    }
}
