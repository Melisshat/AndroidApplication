package fr.isen.vallauri.mysecondapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact.view.*

class ContactAdapter(private val contacts: List<String>): RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ContactHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.contact.text = contacts[position]
    }

    class ContactHolder(contactView: View): RecyclerView.ViewHolder(contactView){
        val contact = contactView.contactTV
    }
}
