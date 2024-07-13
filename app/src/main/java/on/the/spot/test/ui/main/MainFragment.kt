package on.the.spot.test.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import on.the.spot.test.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    val text = it?.let { getString(R.string.last_boot, dateFormat.format(Date(it))) } ?: getString(R.string.no_boots)
                    textView.setText(text)
                }
        }

        textView.setOnClickListener {
            viewModel.onClicked()
        }
    }
}
