package fr.isen.vallauri.mysecondapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.design_web_service.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewWeb(private val users: User, val context: Context) :
    RecyclerView.Adapter<RecyclerViewWeb.WebHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.design_web_service, parent, false)
        return WebHolder(users, view, context)
    }

    override fun getItemCount(): Int {
        return users.results.size
    }

    override fun onBindViewHolder(holder: WebHolder, position: Int) {
        holder.loadInfo(position)
    }

    class WebHolder(private val webUsers: User, view: View, val context: Context) :
        RecyclerView.ViewHolder(view) {
        private val name: TextView = view.nameWebServ
        private val image: ImageView = view.pictWebServ
        private val address: TextView = view.addressWebServ
        private val email: TextView = view.mailWebServ


        fun loadInfo(index: Int) {
            val nameWebServ =
                webUsers.results[index].name.first + " " + webUsers.results[index].name.last
            val addressWebServ =
                webUsers.results[index].location.street.number.toString() + " " + webUsers.results[index].location.street.name + " " + webUsers.results[index].location.city
            Glide.with(context)
                .load(webUsers.results[index].picture.large)
                .apply(RequestOptions.circleCropTransform())
                .into(image)

            name.text = nameWebServ
            email.text = webUsers.results[index].email
            address.text = addressWebServ
        }
    }
}