package no.uia.ikt205.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import java.nio.file.Files.size


class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var pauseTimer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var timeSelectSlider:SeekBar
    lateinit var repetitionInput:EditText
    lateinit var isPauseText:TextView
    lateinit var pauseTimeSelect:SeekBar

    private var hasTimerStarted: Boolean = false
    private var hasPauseTimerStarted: Boolean = false

    // Default timer value is 15 minutes
    private var timeToCountDownInMs = 15 * 60000L // Default 15 minutes
    private val timeTicks = 1000L
    private var pauseTime = 15 * 60000L // Default 15 minutes

    // Default amount of repetitions
    private var timerRepetitionAmount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isPauseText = findViewById(R.id.isPauseText)
        isPauseText.visibility = View.INVISIBLE

        timeSelectSlider = findViewById<SeekBar>(R.id.timeSelectSlider)
        timeSelectSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (hasTimerStarted) {
                    timer.cancel()
                    hasTimerStarted = false
                }
                timeToCountDownInMs = progress * 60000L

                // Update timer visually when sliding
                updateCountDownDisplay(timeToCountDownInMs)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

        pauseTimeSelect = findViewById<SeekBar>(R.id.pauseTimeSelect)
        pauseTimeSelect.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pauseTime = progress * 60000L
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

        repetitionInput = findViewById<EditText>(R.id.repetitionAmount)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

        // Enforce timer to show 15 minutes as start value on activity mount
        updateCountDownDisplay(timeToCountDownInMs)
    }

    private fun startCountDown(v: View){
        if (hasTimerStarted) {
            timer.cancel()
            hasTimerStarted = false
            Toast.makeText(this@MainActivity, "Stopping all timers", Toast.LENGTH_SHORT).show()
            return
        }

        timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                hasTimerStarted = false

                timerRepetitionAmount = repetitionInput.text.toString().toInt()
                if (timerRepetitionAmount > 0) {
                    Toast.makeText(this@MainActivity, "Tid for en pause", Toast.LENGTH_SHORT).show()
                    startPauseTimer(v)
                    timerRepetitionAmount--
                    repetitionInput.setText(timerRepetitionAmount.toString())
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
        hasTimerStarted = true
    }

    private fun startPauseTimer(v: View) {
        if (hasPauseTimerStarted) {
            pauseTimer.cancel()
            hasPauseTimerStarted = false
        }

        pauseTimer = object : CountDownTimer(pauseTime, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Pausen er over", Toast.LENGTH_SHORT).show()
                hasPauseTimerStarted = false

                timerRepetitionAmount = repetitionInput.text.toString().toInt()
                if (timerRepetitionAmount > 0) {
                    timer.start()
                } else {
                    timer.cancel()
                }

                isPauseText.visibility = View.INVISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                isPauseText.visibility = View.VISIBLE
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        pauseTimer.start()
        hasPauseTimerStarted = true
    }

    fun updateCountDownDisplay(timeInMs: Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}