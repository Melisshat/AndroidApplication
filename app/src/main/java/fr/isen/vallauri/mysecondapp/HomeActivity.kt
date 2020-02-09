package fr.isen.vallauri.mysecondapp

import android.content.Context
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
        sauvegardeIMG.setOnClickListener{
            goToStorage()
        }
        permissionIMG.setOnClickListener{
            goToPermission()
        }
        webServiceIMG.setOnClickListener{
            goToWebService()
        }
        logoutBtn.setOnClickListener{
            doLogout()
        }
    }

    private fun goToLifeCycle(){
        startActivity(Intent(this@HomeActivity, LifeCycleActivity::class.java))
    }
    private fun goToStorage(){
        startActivity(Intent(this@HomeActivity, StorageActivity::class.java))
    }
    private fun goToPermission(){
        startActivity(Intent(this@HomeActivity, InformationActivity::class.java))
    }
    private fun goToWebService(){
        startActivity(Intent(this@HomeActivity, WebServiceActivity::class.java))
    }


    private fun doLogout(){
        val sharedPreference =  getSharedPreferences(LoginActivity.PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
    }
}
