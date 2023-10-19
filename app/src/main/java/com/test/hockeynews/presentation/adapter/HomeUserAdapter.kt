package com.test.hockeynews.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.hockeynews.R
import com.test.hockeynews.presentation.models.UserModel


class HomeUserAdapter(
    private val itemClickListener: OnItemClickListener,
    private val scoreLiveData: LiveData<Int>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<HomeUserAdapter.UserViewHolder>() {

    private var userList = emptyList<UserModel>()

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val score1TextView: TextView = view.findViewById(R.id.score_of_team1)
        val score2TextView: TextView = view.findViewById(R.id.score_of_team2)
    }

    interface OnItemClickListener {
        fun onItemClick(userModel: UserModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_result_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.itemView.findViewById<TextView>(R.id.item_team1_text).text = currentUser.nameOfTeam1
        holder.itemView.findViewById<TextView>(R.id.item_team2_text).text = currentUser.nameOfTeam2

        Glide.with(holder.itemView)
            .load(currentUser.flagOfTeam1)
            .into(holder.itemView.findViewById(R.id.flag_of_team1))

        Glide.with(holder.itemView)
            .load(currentUser.flagOfTeam2)
            .into(holder.itemView.findViewById(R.id.flag_of_team2))

        // Подписываемся на изменения переменной isEnabled
        currentUser.isEnabled.observe(lifecycleOwner) { isEnabled ->
            if (!isEnabled) {
                holder.score1TextView.text = "?"
                holder.score2TextView.text = "?"
            } else {
                holder.score1TextView.text = currentUser.scoreOfTeam1.toString()
                holder.score2TextView.text = currentUser.scoreOfTeam2.toString()
            }
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentUser)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setList(list: List<UserModel>) {
        userList = list
        notifyDataSetChanged()
    }

    init {
        scoreLiveData.observe(lifecycleOwner) { newScore ->
            // Обновляем элементы списка при изменении значения scoreLiveData
            userList.forEachIndexed { index, userModel ->
                notifyItemChanged(index)
            }
        }
    }
}