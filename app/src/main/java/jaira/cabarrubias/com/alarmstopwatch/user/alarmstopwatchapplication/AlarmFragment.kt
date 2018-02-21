package jaira.cabarrubias.com.alarmstopwatch.user.alarmstopwatchapplication


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AlarmFragment : Fragment() {


    lateinit var am: AlarmManager
    lateinit var time: TimePicker
    lateinit var Update_text: TextView
    lateinit var con: Context
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    var hour: Int = 0
    var min: Int = 0
    lateinit var pending_intent: PendingIntent


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootview = inflater!!.inflate(R.layout.fragment_alarm, container, false)
        func(rootview)

        return rootview
    }

    private fun func(view: View){
        this.con = view.context

        am = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        time = view.findViewById(R.id.time) as TimePicker
        Update_text = view.findViewById(R.id.update_text) as TextView
        btnStart = view.findViewById(R.id.set_alarm) as Button
        btnStop = view.findViewById(R.id.stop_alarm) as Button
        val calendar: Calendar = Calendar.getInstance()
        val myIntent = Intent(context,AlarmReciever::class.java)

        btnStart.setOnClickListener(object : View.OnClickListener{
            @SuppressLint("NewApi")
            override fun onClick(p0: View?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    calendar.set(Calendar.HOUR_OF_DAY,time.hour)
                    calendar.set(Calendar.MINUTE,time.minute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = time.hour
                    min = time.minute
                }else{
                    calendar.set(Calendar.HOUR_OF_DAY,time.currentHour)
                    calendar.set(Calendar.MINUTE,time.currentMinute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = time.currentHour
                    min = time.currentMinute
                }
                var hr_str: String = hour.toString()
                var min_str: String = min.toString()
                if (hour>12){
                    hr_str = (hour-12).toString()
                }
                if(min < 10){
                    min_str = "0$min"
                }
                set_alarm_text("Alarm set to: $hr_str : $min_str")
                myIntent.putExtra("extra", "on")
                pending_intent = PendingIntent.getBroadcast(context,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pending_intent)

            }
        })
        btnStop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                set_alarm_text("Alarm off")
                myIntent.putExtra("extra", "off")
                pending_intent = PendingIntent.getBroadcast(context,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel(pending_intent)
                sendBroadcast(myIntent)
            }

        })
    }


    private fun sendBroadcast(myIntent: Intent) {

    }

    private fun set_alarm_text(s: String) {
        Update_text.setText(s)
    }

}
