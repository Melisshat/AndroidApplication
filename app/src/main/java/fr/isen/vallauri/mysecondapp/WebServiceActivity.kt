package fr.isen.vallauri.mysecondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_web_service.*

class WebServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_service)
        Api()
    }

    private fun Api(): User {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var user = User()
        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://randomuser.me/api/?inc=name%2Cpicture%2Clocation%2Cemail&noinfo=&nat=fr&format=pretty&results=10",
            null,
            Response.Listener { response ->
                val gson = Gson()
                user = gson.fromJson(response.toString(), User::class.java)
                /* IL MANQUE DES TRUCS */

            },
            Response.ErrorListener {
                Log.d("TAG", "Error")
            }
        )

        /*
        val url =
            "https://randomuser.me/api/?inc=name%2Cpicture%2Clocation%2Cemail&noinfo=&nat=fr&format=pretty&results=10"

        // Request a string response from the provided URL.

        val stringRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->

                val gson = Gson()
                user = gson.fromJson(response.toString(), User::class.java)

                // Display the first 500 characters of the response string.
                myUserRV.text = "Response is: $response"
            },
            Response.ErrorListener {
                myUserRV.text = "Error! ${it.message}"
            })
*/
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        return user
    }
}
