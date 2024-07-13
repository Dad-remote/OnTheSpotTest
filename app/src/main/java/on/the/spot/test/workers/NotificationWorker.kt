package on.the.spot.test.workers

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking
import on.the.spot.test.R
import on.the.spot.test.db.TestDatabase
import on.the.spot.test.db.model.BootEntity
import on.the.spot.test.notifications.NotificationHelper
import on.the.spot.test.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val database: TestDatabase
) : Worker(context, params) {

    companion object {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
    }

    override fun doWork(): Result {
        return try {
            runBlocking {
                val allItems = database.bootsDao().getAll()
                val title = applicationContext.getString(R.string.special_body)
                NotificationHelper(applicationContext).showNotification(title, buildText(allItems))
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }

    private fun buildText(allItems: List<BootEntity>): String {
        return when (allItems.size) {
            0 -> applicationContext.getString(R.string.no_boots)
            1 -> applicationContext.getString(R.string.boots_detected, dateFormat.format(Date(allItems.first().time)))
            else -> applicationContext.getString(R.string.last_boots_delta, allItems.last().time - allItems[allItems.size - 2].time)
        }
    }

}
