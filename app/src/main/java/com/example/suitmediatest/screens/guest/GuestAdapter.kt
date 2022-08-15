package com.example.suitmediatest.screens.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.suitmediatest.databinding.ItemRegresBinding
import com.example.suitmediatest.model.DataItem
import com.example.suitmediatest.screens.guest.GuestAdapter.ViewHolder

class GuestAdapter : PagingDataAdapter<DataItem, ViewHolder>(RegresDiffCallback()) {
    class ViewHolder(
        val binding: ItemRegresBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem?) {
            binding.run {
                imgPhoto.load(item?.avatar)
                txtName.text = "${item?.first_name} " + " " + "${item?.last_name}"
                txtEmail.text = item?.email
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRegresBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class RegresDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

}