package on.the.spot.test.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import on.the.spot.test.R
import on.the.spot.test.ui.MainActivity

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "default_channel_id"
        const val NOTIFICATION_ID = 123
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, body: String) {
        val notification = createNotification(title, body, NOTIFICATION_ID)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Dad", "no permissions")
        } else {
            NotificationManagerCompat
                .from(context)
                .notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotification(title: String, body: String, notificationId: Int): Notification {
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT,
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(body ?: "")
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        return builder.build()
    }
}
