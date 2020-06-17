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
//import java.util.*
import android.widget.ImageView
import android.content.Context
import com.example.findmask.database.FavoriteDatabase
import android.util.Log
import android.widget.Toast
import com.example.findmask.databinding.ItemMoreinfoBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class MoreInfoAdapter : RecyclerView.Adapter<MoreInfoAdapter.Holder>() {

    private var storeSale = ArrayList<MoreInfo>()

    private var storelist = ArrayList<MoreInfo>()

    private lateinit var context: Context

    private var favoriteDatabase: FavoriteDatabase? = null

    fun setItem(list: ArrayList<MoreInfo>, context: Context) {
            this.storeSale = list
            this.context = context
            this.storelist.addAll(storeSale)
            notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return storeSale.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.storeName.text = storeSale[position].name
        holder.binding.storeAddress.text = storeSale[position].addr

        if (storeSale[position].remain_stat == "plenty") {
            holder.binding.remainStat.text = "100개 이상"
            holder.binding.remainStat.setTextColor(Color.parseColor("#32CD32"))
        }
        else if(storeSale[position].remain_stat == "some") {
            holder.binding.remainStat.text = "30~99개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#ff7f00"))
        }
        else if(storeSale[position].remain_stat == "few") {
            holder.binding.remainStat.text = "2~29개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#ff0000"))
        }
        else if(storeSale[position].remain_stat == "empty") {
            holder.binding.remainStat.text = "0~1개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#000000"))
        }
        else if(storeSale[position].remain_stat == "break") {
            holder.binding.remainStat.text = "판매중지"
            holder.binding.remainStat.setTextColor(Color.parseColor("#808080"))
        }

        holder.binding.createdAt.text = storeSale[position].created_at
        holder.binding.stockAt.text = storeSale[position].stock_at

        if (storeSale[position].isfavorite) {
            holder.binding.storeFavorite.setImageResource(R.drawable.ic_star)
        }
        else {
            holder.binding.storeFavorite.setImageResource(R.drawable.ic_star_border_black_24dp)
        }

        holder.binding.storeFavorite.setOnClickListener {
            favoriteDatabase = FavoriteDatabase.getInstance(context)

            var toast = ""

            val runnable = Runnable {
//                if (storeSale[position].remain_stat == "plenty") {
//                    holder.binding.remainStat.text = "100개 이상"
//                    holder.binding.remainStat.setTextColor(Color.parseColor("#32CD32"))
//                }
//                else if(storeSale[position].remain_stat == "some") {
//                    holder.binding.remainStat.text = "30~99개"
//                    holder.binding.remainStat.setTextColor(Color.parseColor("#ff7f00"))
//                }
//                else if(storeSale[position].remain_stat == "few") {
//                    holder.binding.remainStat.text = "2~29개"
//                    holder.binding.remainStat.setTextColor(Color.parseColor("#ff0000"))
//                }
//                else if(storeSale[position].remain_stat == "empty") {
//                    holder.binding.remainStat.text = "0~1개"
//                    holder.binding.remainStat.setTextColor(Color.parseColor("#000000"))
//                }
//                else if(storeSale[position].remain_stat == "break") {
//                    holder.binding.remainStat.text = "판매중지"
//                    holder.binding.remainStat.setTextColor(Color.parseColor("#808080"))
//                }

                val store = MoreInfo(storeSale[position].name, storeSale[position].addr,
                    storeSale[position].remain_stat, storeSale[position].stock_at, storeSale[position].created_at, true)
                if (storeSale[position].isfavorite) {
                    favoriteDatabase?.favoriteDao()?.delete(storeSale[position])
                    this.storeSale[position].isfavorite = false
                }
                else {
                    favoriteDatabase?.favoriteDao()?.insert(store)
                    this.storeSale[position].isfavorite = true
                }
            }

            val thread = Thread(runnable)
            thread.start()
            if (this.storeSale[position].isfavorite)
                toast = "즐겨찾기에서 삭제되었습니다."
            else
                toast = "즐겨찾기에 추가되었습니다."
            notifyDataSetChanged()
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMoreinfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val binding: ItemMoreinfoBinding) : RecyclerView.ViewHolder(binding.root) {}

    fun filter(text: String) {
        var charText = text.toLowerCase(Locale.getDefault())
        storeSale.clear()
        if (charText.length == 0) {
            storeSale.addAll(storelist)
        } else {
            for (moreinfo : MoreInfo in storelist) {
                var name = moreinfo.name
                if (name.toLowerCase().contains(charText)) {
                    storeSale.add(moreinfo)
                }
            }
        }
        notifyDataSetChanged()
    }


}