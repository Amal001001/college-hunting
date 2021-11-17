package com.example.amal.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amal.DbFragment
import com.example.amal.database.DBUniversities
import com.example.amal.databinding.ItemRow2Binding


class UniversitiesAdapter (private val fragment: DbFragment): RecyclerView.Adapter<UniversitiesAdapter.ItemViewHolder>() {
        private var items = emptyList<DBUniversities>()
        class ItemViewHolder(val binding: ItemRow2Binding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversitiesAdapter.ItemViewHolder {
            return ItemViewHolder(
                ItemRow2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: UniversitiesAdapter.ItemViewHolder, position: Int) {
            val item = items[position]

            holder.binding.apply {
                tvUniversityName.text = item.name
                tvUniversityCountry.text = item.country


                ItemRow2Layout.setOnClickListener { fragment.toastNote(item.note) }

                btnUpdate.setOnClickListener {
                    fragment.updateDialog(item.id,item.name,item.country)
                }

                btnDelete.setOnClickListener {
                    fragment.deleteDialog(item.id)
                }
            }
        }

        override fun getItemCount() = items.size

        @SuppressLint("NotifyDataSetChanged")
        fun update(items: List<DBUniversities>){
            this.items = items
            notifyDataSetChanged()
        }
}