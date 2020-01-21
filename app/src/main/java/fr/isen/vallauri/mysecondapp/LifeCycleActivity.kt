package fr.isen.vallauri.mysecondapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import kotlinx.android.synthetic.main.activity_life_cycle.*

class LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        notification("onCreate", isActive = true)
    }

    private fun notification(message : String, isActive : Boolean){
        if(isActive)
           lifeCycleText.text = message
        else
            Log.d("TAG", message)
        makeText( this, "$message", Toast.LENGTH_SHORT)
    }

    override fun onStart(){
        super.onStart()
        notification("onStart", isActive = true)
    }

    override fun onResume(){
        super.onResume()
        notification("onResume", isActive = true)
    }

    override fun onStop(){
        super.onStop()
        notification("onStop", isActive = false)
    }

    override fun onDestroy(){
        super.onDestroy()
        notification("onDestroy", isActive = false)
    }

    override fun onPause(){
        super.onPause()
        notification("onStop", isActive = false)
    }
}
