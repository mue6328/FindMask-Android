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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class MoreInfoAdapter : RecyclerView.Adapter<MoreInfoAdapter.Holder>() {

    private var storeSale = ArrayList<MoreInfo>()

    private var storelist = ArrayList<MoreInfo>()

    private lateinit var context: Context

    private var favoriteDatabase: FavoriteDatabase? = null

    private var favoriteList = listOf<MoreInfo>()

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
        holder.storeName.text = storeSale[position].name
        holder.storeAddress.text = storeSale[position].addr

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

        if (storeSale[position].isfavorite) {
            holder.storeFavorite.setImageResource(R.drawable.ic_star)
        }
        else {
            holder.storeFavorite.setImageResource(R.drawable.ic_star_border_black_24dp)
        }

        holder.storeFavorite.setOnClickListener {
            favoriteDatabase = FavoriteDatabase.getInstance(context)

            var toast = ""

            val runnable = Runnable {
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

                val store = MoreInfo(storeSale[position].name, storeSale[position].addr,
                    storeSale[position].remain_stat, storeSale[position].stock_at, storeSale[position].created_at, true)
                if (storeSale[position].isfavorite) {
                    favoriteDatabase?.favoriteDao()?.delete(storeSale[position])
                    this.storeSale[position].isfavorite = false
                }
                else {
                    favoriteDatabase?.favoriteDao()?.insert(store)
          //          favoriteList = favoriteDatabase?.favoriteDao()?.getFavorites()!!
                    this.storeSale[position].isfavorite = true
                }



                //var i = 1
//                favoriteDatabase?.favoriteDao()?.deleteAll()
//                if (favoriteDatabase?.favoriteDao()?.getFavorites()!!.isNotEmpty()) {
//                    for (i in favoriteDatabase?.favoriteDao()?.getFavorites()!!.indices) {
//                        if (favoriteDatabase?.favoriteDao()?.getFavorite(i.toLong())!!.name == store.name
//                            && favoriteDatabase?.favoriteDao()?.getFavorite(i.toLong())!!.addr == store.addr) {
//                            Log.d("favoriteee", store.toString() + favoriteDatabase?.favoriteDao()?.getFavorites())
//                        }
//                        else {
//                            favoriteDatabase?.favoriteDao()?.insert(store)
//                        }
//                    }
//                }
//                else {
//                    favoriteDatabase?.favoriteDao()?.insert(store)
//                }

                //Toast.makeText(context, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show()


                Log.d("favorite", store.toString() + favoriteDatabase?.favoriteDao()?.getFavorites())
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_moreinfo, parent, false)
        return Holder(view)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var storeName = itemView.findViewById<TextView>(R.id.storeName)
        var storeAddress = itemView.findViewById<TextView>(R.id.storeAddress)
        var remain_stat = itemView.findViewById<TextView>(R.id.remain_stat)
        var create_at = itemView.findViewById<TextView>(R.id.created_at)
        var stock_at = itemView.findViewById<TextView>(R.id.stock_at)
        var storeFavorite = itemView.findViewById<ImageView>(R.id.storeFavorite)
    }

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