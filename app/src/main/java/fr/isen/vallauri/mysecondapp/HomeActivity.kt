package fr.isen.vallauri.mysecondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cycleVieIMG.setOnClickListener{
            goToLifeCycle()
        }
    }
    private fun goToLifeCycle() {
        startActivity(Intent(this@HomeActivity, LifeCycleActivity::class.java))
    }
}
