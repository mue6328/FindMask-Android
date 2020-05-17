package com.example.findmask.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmask.R
import com.example.findmask.model.MoreInfo
import com.example.findmask.model.StoreSale

class MoreInfoAdapter : RecyclerView.Adapter<MoreInfoAdapter.Holder>() {

    private var storeSale: List<MoreInfo> = ArrayList()

    fun setItem(list: List<MoreInfo>) {
        this.storeSale = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return storeSale.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.storeName.text = storeSale[position].name
        if (storeSale[position].remain_stat == "plenty") {
            holder.remain_stat.text = "100개 이상"
            holder.remain_stat.setTextColor(Color.parseColor("#32CD32"))
        }
        else if(storeSale[position].remain_stat == "some") {
            holder.remain_stat.text = "30~99개"
            holder.remain_stat.setTextColor(Color.parseColor("#ff7f00"))
        }
        else if(storeSale[position].remain_stat == "few") {
            holder.remain_stat.text = "2~29개"
            holder.remain_stat.setTextColor(Color.parseColor("#ff0000"))
        }
        else if(storeSale[position].remain_stat == "empty") {
            holder.remain_stat.text = "0~1개"
            holder.remain_stat.setTextColor(Color.parseColor("#000000"))
        }
        else if(storeSale[position].remain_stat == "break") {
            holder.remain_stat.text = "판매중지"
            holder.remain_stat.setTextColor(Color.parseColor("#808080"))
        }

        holder.create_at.text = storeSale[position].created_at
        holder.stock_at.text = storeSale[position].stock_at
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_moreinfo, parent, false)
        return Holder(view)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var storeName = itemView.findViewById<TextView>(R.id.storeName)
        var remain_stat = itemView.findViewById<TextView>(R.id.remain_stat)
        var create_at = itemView.findViewById<TextView>(R.id.created_at)
        var stock_at = itemView.findViewById<TextView>(R.id.stock_at)
    }


}