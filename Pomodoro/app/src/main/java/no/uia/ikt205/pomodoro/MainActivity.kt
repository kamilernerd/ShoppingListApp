package no.uia.ikt205.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var dropDown:Spinner

    private var hasTimerStarted: Boolean = false

    private val countdownTimeArray = arrayOf<Long>(
        1800000L,
        3600000L,
        5400000L,
        7200000L
    )
    private var timeToCountDownInMs = countdownTimeArray[0]
    private val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dropDown = findViewById<View>(R.id.dropdown_menuTimes) as Spinner
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, countdownTimeArray)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(dropDown) {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@MainActivity
            prompt = "Select time interval"
            gravity = Gravity.CENTER
        }
        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)
    }

    private fun startCountDown(v: View){
        if (hasTimerStarted) {
            Toast.makeText(this@MainActivity, "Timer is already running", Toast.LENGTH_SHORT).show()
            return
        }

        timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                hasTimerStarted = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
        hasTimerStarted = true
    }

    fun updateCountDownDisplay(timeInMs: Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (hasTimerStarted) {
            timer.cancel()
            hasTimerStarted = false
        }

        Toast.makeText(this@MainActivity, "You selected ${(countdownTimeArray[position] / (1000 * 60))} minutes", Toast.LENGTH_SHORT).show()
        timeToCountDownInMs = countdownTimeArray[position]
        updateCountDownDisplay(timeToCountDownInMs)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}