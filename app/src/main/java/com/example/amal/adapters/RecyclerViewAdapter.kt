package com.example.amal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amal.ApiFragment
import com.example.amal.Univirsity
import com.example.amal.databinding.ItemRowBinding

class RecyclerViewAdapter (val fragment: ApiFragment, private val univirsities: ArrayList<Univirsity>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
        class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

            return ItemViewHolder(
                ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val univirsity = univirsities[position]

            holder.binding.apply {
                tvName.text = univirsity.name
                itemRowLayout.setOnClickListener { fragment.addToDBDialog(univirsity.name,univirsity.country) }
            }
        }
        override fun getItemCount() = univirsities.size
}