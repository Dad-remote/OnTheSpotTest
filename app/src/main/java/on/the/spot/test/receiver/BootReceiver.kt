package on.the.spot.test.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import on.the.spot.test.repository.MainRepository
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var mainRepository: MainRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        mainRepository.saveNewBoot()
    }

}
