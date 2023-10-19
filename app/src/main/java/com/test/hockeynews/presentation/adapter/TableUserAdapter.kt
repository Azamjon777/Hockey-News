package com.test.hockeynews.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.hockeynews.R
import com.test.hockeynews.presentation.models.TableUserModel

class TableUserAdapter : RecyclerView.Adapter<TableUserAdapter.UserTableViewHolder>() {
    private var userList = emptyList<TableUserModel>()

    class UserTableViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTableViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_table_layout, parent, false)
        return UserTableViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserTableViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.country_place).text =
            userList[position].countryPlace.toString()
        holder.itemView.findViewById<TextView>(R.id.country_name).text =
            userList[position].countryName
        holder.itemView.findViewById<TextView>(R.id.country_difference).text =
            userList[position].countryDifference.toString()
        holder.itemView.findViewById<TextView>(R.id.country_points).text =
            userList[position].countryPoints.toString()
    }


    override fun getItemCount(): Int {
        return userList.size
    }

    fun setTableList(list: List<TableUserModel>) {
        userList = list
        notifyDataSetChanged()
    }
}