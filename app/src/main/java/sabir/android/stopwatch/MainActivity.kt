package sabir.android.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import sabir.android.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var job: Job? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startTimer()
        }

        binding.stopButton.setOnClickListener {
            stopTimer()
        }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(1000)
                time++
                val seconds = time % 60
                val minutes = (time / 60) % 60
                val hours = (time / 3600)
                binding.timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        }
    }

    private fun stopTimer() {
        job?.cancel()
    }

    private fun resetTimer() {
        stopTimer()
        time = 0
        binding.timerTextView.text = "00:00:00"
    }
}
