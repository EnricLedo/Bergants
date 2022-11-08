package cat.copernic.bergants

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMER: Long = 3000

    //Variables
    private lateinit var backgroundImage: ImageView

    // Animations
    private lateinit var sideAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //Hooks
        backgroundImage = findViewById(R.id.background_image)

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim)

        //set Animations on elements
        backgroundImage.animation = sideAnim

        Handler().postDelayed({
            var intent: Intent
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()

        }, SPLASH_TIMER)
    }
}