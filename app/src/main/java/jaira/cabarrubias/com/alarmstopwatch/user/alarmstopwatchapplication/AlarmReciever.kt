package jaira.cabarrubias.com.alarmstopwatch.user.alarmstopwatchapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by user on 13/02/2018.
 */
class AlarmReciever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {


        Log.e("We are in the receiver!","Yay!")

        val getResult: String = intent!!.getStringExtra("extra")

        val service: Intent = Intent(context,RingService::class.java)
        service.putExtra("extra",getResult)
        context!!.startService(service)
    }
}