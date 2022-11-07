package cat.copernic.bergants

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //title bar hide
        supportActionBar!!.hide() //hide the title bar
        Handler(Looper.myLooper()!!).postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@SplashActivity, Login::class.java)
            startActivity(i)
            finish()
        }, 1000)

//if you want to change your splash screen stay 5 second or more then just  edit 5000
    }
}
