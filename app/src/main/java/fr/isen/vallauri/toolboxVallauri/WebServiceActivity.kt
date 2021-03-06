package fr.isen.vallauri.toolboxVallauri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
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
                progressBar2.visibility = View.INVISIBLE
                myUserRV.layoutManager = LinearLayoutManager(this)
                myUserRV.adapter = RecyclerViewWeb(user,this)
                myUserRV.visibility = View.VISIBLE
            },
            Response.ErrorListener {
                Log.d("TAG", "Error")
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        return user
    }
}
