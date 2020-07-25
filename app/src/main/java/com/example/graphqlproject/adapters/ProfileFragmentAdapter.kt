package com.example.graphqlproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqlproject.GetUserPostsQuery
import com.example.graphqlproject.utilities.Id
import com.example.graphqlproject.utilities.Layout

class ProfileFragmentAdapter(private val list: MutableList<GetUserPostsQuery.Todo>) :
    RecyclerView.Adapter<ProfileFragmentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(Layout.user_post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName?.text = list[position].user().name()
        holder.postDate?.text = with(list[position].created_at().toString(), {
            this.substring(0, this.indexOf("T"))
        })
        holder.post?.text = list[position].title()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView? = null
        var postDate: TextView? = null
        var post: TextView? = null

        init {
            userName = itemView.findViewById(Id.profileName)
            postDate = itemView.findViewById(Id.postDate)
            post = itemView.findViewById(Id.profilePostContent)
        }
    }


}