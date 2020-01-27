package fr.isen.vallauri.mysecondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class LoginActivity : AppCompatActivity() {

    companion object {
        private const val GOOD_ID = "admin"
        private const val GOOD_PASS = "123"
        const val MY_ID_KEY = "id"
        const val MY_MDP_KEY = "mdp"
        const val PREF_KEY = "myfile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreference =  getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        if (sharedPreference.getString(MY_ID_KEY, "") == GOOD_ID && sharedPreference.getString(MY_MDP_KEY, "") == GOOD_PASS){
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
        //Click on the button
        validerBT.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin(){
        val myId = MonIdentifiant.text.toString()
        val myPassword = MonPassword.text.toString()

        if(myId == GOOD_ID && myPassword == GOOD_PASS){

            saveUserCredential(myId, myPassword)
            //Pass to another activity page
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }

    private fun saveUserCredential(myId: String, myPassword: String){
        val sharedPreference =  getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString(MY_ID_KEY, myId)
        editor.putString(MY_MDP_KEY, myPassword)
        editor.apply()
    }
}
