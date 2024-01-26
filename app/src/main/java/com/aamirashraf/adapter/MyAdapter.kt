package com.aamirashraf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aamirashraf.model.Item
import com.aamirashraf.pitch_catalyst_assignment.databinding.RvItemsBinding

class MyAdapter(private var list:List<Item>):Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: RvItemsBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr=list[position]
        holder.binding.apply {
            tvtitle1.text=curr.title
            tvBody.text=curr.body
            cbDone.isChecked=curr.isChecked
        }
    }
    fun updateData(newList: List<Item>) {
        list = newList
        notifyDataSetChanged()
    }
}