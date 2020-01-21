package fr.isen.vallauri.mysecondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val GOOD_ID = "admin"
        private const val GOOD_PASS = "123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Click on the button
        validerBT.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin(){
        val myId = MonIdentifiant.text.toString()
        val myPassword = MonPassword.text.toString()
        if(myId == GOOD_ID && myPassword == GOOD_PASS){
            //Pass to another activity page
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }
}
