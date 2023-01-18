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
import androidx.annotation.Keep
import java.util.*

@Keep
class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMER: Long = 3000

    //Variables
    private lateinit var backgroundImage: ImageView

    // Animations
    private lateinit var sideAnim: Animation

    /**

    Aquesta funció s'executa en la creació de l'activity.
    S'assigna la vista i es creen les variables per les imatges de fons i les animacions.
    S'estableixen les animacions en les imatges de fons.
    Utilitza un controlador per a la creació d'un temporitzador que redirigeix a l'usuari a la pantalla d'inici de sessió un cop s'ha acabat el temps establert en SPLASH_TIMER.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //Hooks
        backgroundImage = findViewById(R.id.background_image)

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim)

        //set Animations on elements
        backgroundImage.animation = sideAnim

        Handler(Looper.myLooper()!!).postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@SplashActivity, Login::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIMER)

    }
}