package jaira.cabarrubias.com.alarmstopwatch.user.alarmstopwatchapplication

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.NotificationCompat

/**
 * Created by user on 13/02/2018.
 */
class RingService : Service() {


    var id: Int = 0
    var isRunning: Boolean = false

    companion object {
        lateinit var r: Ringtone
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val state: String = intent!!.getStringExtra("extra")!!
        when(state)
        {
            "on" -> id = 1
            "off" -> id = 0
        }
        if (!this.isRunning && id == 1){
            playAlarm()
            this.isRunning = true
            this.id = 0
            fireNotification()

        } else if(this.isRunning && id == 0){
            r.stop()
            this.isRunning = false
            this.id = 0

        } else if (this.isRunning && id == 0){
            this.isRunning = false
            this.id = 0

        } else if (this.isRunning && id == 1){
            this.isRunning = true
            this.id = 1
        } else {

        }
        playAlarm()

        return START_NOT_STICKY
    }

    private fun fireNotification() {
        val main_activity_intent: Intent = Intent(this,MainActivity::class.java)
        val pending_Intent = PendingIntent.getActivity(this,0,main_activity_intent,0)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notify_manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification = NotificationCompat.Builder(this)
                .setContentTitle("Alarm is going off")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSound)
                .setContentText("Click Me")
                .setContentIntent(pending_Intent)
                .setAutoCancel(true)
                .build()

        notify_manager.notify(0,notification)
    }

    private fun playAlarm() {
        var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (false){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        r = RingtoneManager.getRingtone(baseContext, alarmUri)
        r.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }
}