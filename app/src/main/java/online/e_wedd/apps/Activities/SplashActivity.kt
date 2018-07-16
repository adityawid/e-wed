package online.e_wedd.apps.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import online.e_wedd.apps.R

class SplashActivity : AppCompatActivity() {
    var runnable: Runnable? = null
    val handler = Handler()
    var TIME_OUT_SPLASH : Int = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        HandlerStart()

    }

    private fun HandlerStart(){
        runnable = object : Runnable {
            override fun run() {
                try {
                    //handler.postDelayed(this, TIME_OUT_SPLASH.toLong()*1000)
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish() /*finish biar splash screen tidak berulang*/
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, TIME_OUT_SPLASH.toLong()*1000)
    }
}
