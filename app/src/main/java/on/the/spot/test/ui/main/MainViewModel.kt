package on.the.spot.test.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import on.the.spot.test.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val state = mainRepository.loadLastBootTime()

    fun onClicked() {
        mainRepository.saveNewBoot()
    }
}
