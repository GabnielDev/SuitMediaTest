package com.example.suitmediatest.screens.event

import android.view.View
import androidx.core.net.toUri
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.suitmediatest.R
import com.example.suitmediatest.databinding.ItemEventBinding
import com.example.suitmediatest.model.User
import com.example.suitmediatest.screens.event.EventAdapter.ViewHolder

class EventAdapter : BaseQuickAdapter<User, ViewHolder>(R.layout.item_event) {

    override fun convert(holder: ViewHolder, item: User) {
        holder.bindData(item)
    }

    class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ItemEventBinding.bind(view)

        fun bindData(item: User) {
            binding.run {
                imgPhoto.load(item.image?.toUri())
                txtName.text = item.name
            }
        }
    }


}