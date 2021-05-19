package com.example.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumerapp.databinding.ActivityUserBinding


class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    private val userItem = ArrayList<User>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUser(items: ArrayList<User>) {
        userItem.clear()
        userItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewViewHolder(private val binding: ActivityUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.photo)
                    .centerCrop()
                    .into(itemPhoto)
                tvUsername.text = user.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewViewHolder {
        val binding = ActivityUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewViewHolder(binding)
    }

    override fun getItemCount(): Int = userItem.size

    override fun onBindViewHolder(holder: ListViewViewHolder, position: Int) {
        holder.bind(userItem[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataUsers: User)
    }
}