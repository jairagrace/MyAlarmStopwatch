package jaira.cabarrubias.com.alarmstopwatch.user.alarmstopwatchapplication


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 */
class StopwatchFragment : Fragment() {

    lateinit var btnStart: Button
    lateinit var btnPause: Button
    lateinit var btnLap: Button
    lateinit var txtTimer: TextView
    internal var customHandler = Handler()
    lateinit var container: LinearLayout

    internal var startTimer = 0L
    internal var timeInMilliseconds = 0L
    internal var timeBuff = 0L
    internal var updateTime = 0L


    internal var updateTimerThread: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTimer

            updateTime = timeBuff + timeInMilliseconds

            var secs = (updateTime / 1000).toInt()

            val mins = secs / 60

            secs %= 60

            val milliseconds = (updateTime / 100).toInt()

            txtTimer.text = "" + mins + ":" + String.format("%2d", secs) + ":" + String.format("%03d", milliseconds)

            customHandler.postDelayed(this, 0)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_stopwatch, container, false)

        bindViews(rootView)

        return rootView
    }

    @SuppressLint("WrongViewCast")
    private fun bindViews(view: View) {
        btnStart = view.findViewById(R.id.btnStart)
        btnPause = view.findViewById(R.id.btnPause)
        btnLap = view.findViewById(R.id.btnLap)
        txtTimer = view.findViewById(R.id.tvTimerValue)
        container = view.findViewById(R.id.container)

        btnStart.setOnClickListener {
            startTimer = SystemClock.uptimeMillis()
            customHandler.postDelayed(updateTimerThread, 0)
        }

        btnPause.setOnClickListener {
            timeBuff += timeInMilliseconds
            customHandler.removeCallbacks(updateTimerThread)
        }


        btnLap.setOnClickListener {
            val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val addView = inflater.inflate(R.layout.appbar, null)
            val textValue = addView.findViewById<TextView>(R.id.txtContent)
            textValue.text = txtTimer.text
            container.addView(addView)
        }
    }

    }// Required empty public constructor
