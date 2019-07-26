package com.example.administrator.garphdemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Adapter(internal var data: List<LoadGithubRepositoriesQuery.Node>?) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_userdata, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var owner: TextView

        init {
            name = itemView.findViewById(R.id.tvName)
            owner = itemView.findViewById(R.id.tvOwner)
        }

        fun onBind(position: Int) {
            name.text = data!![position].name
            owner.text = data!![position].nameWithOwner

        }
    }
}