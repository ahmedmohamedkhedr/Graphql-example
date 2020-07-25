package com.example.graphqlproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqlproject.DataQuery
import com.example.graphqlproject.utilities.Id
import com.example.graphqlproject.utilities.Layout

class HomeFragmentAdapter(private val list: MutableList<DataQuery.Todo>) :
    RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(Layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName?.text = list[position].user().name()
        holder.createdAt?.text = with(list[position].created_at().toString(), {
            this.substring(0, this.indexOf("T"))
        })
        holder.content?.text = list[position].title()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView? = null
        var createdAt: TextView? = null
        var content: TextView? = null

        init {
            userName = itemView.findViewById(Id.userName)
            createdAt = itemView.findViewById(Id.createdAt)
            content = itemView.findViewById(Id.content)
        }
    }


}