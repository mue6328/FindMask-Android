package com.example.findmask.adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.findmask.R
import com.example.findmask.database.FavoriteDatabase
import com.example.findmask.model.MoreInfo
import java.util.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.Holder>() {

    private var storeSale: List<MoreInfo> = ArrayList()

    private lateinit var context: Context

    private var favoriteDatabase: FavoriteDatabase? = null



    fun setItem(list: List<MoreInfo>, context: Context) {
            this.storeSale = list
            this.context = context
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

        holder.storeDelete.setOnClickListener {
            favoriteDatabase = FavoriteDatabase.getInstance(context)

            val runnable = Runnable {
                if (favoriteDatabase?.favoriteDao()?.getFavorites() != null) {
                    favoriteDatabase?.favoriteDao()?.delete(storeSale[position])
                }
                this.storeSale = favoriteDatabase?.favoriteDao()?.getFavorites()!!
                Log.d("delete", "" + favoriteDatabase?.favoriteDao()?.getFavorites())
            }

            val thread = Thread(runnable)
            AlertDialog.Builder(context)
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton(
                    "예"
                ) { d: DialogInterface?, w: Int ->
                    thread.start()
                    notifyDataSetChanged()
                }
                .setNegativeButton(
                    "아니오"
                ) { d: DialogInterface?, w: Int -> }
                .create()
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return Holder(view)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var storeName: TextView = itemView.findViewById(R.id.storeName)
        var storeAddress: TextView = itemView.findViewById(R.id.storeAddress)
        var remain_stat: TextView = itemView.findViewById(R.id.remain_stat)
        var create_at: TextView = itemView.findViewById(R.id.created_at)
        var stock_at: TextView = itemView.findViewById(R.id.stock_at)
        var storeDelete: ImageView = itemView.findViewById(R.id.storeDelete)
    }
}