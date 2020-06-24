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
import com.example.findmask.databinding.ItemFavoriteBinding
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
        return if (storeSale != null)
            storeSale.size
        else
            0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.storeName.text = storeSale[position].name
        holder.binding.storeAddress.text = storeSale[position].addr

        if (storeSale[position].remain_stat == "plenty") {
            holder.binding.remainStat.text = "100개 이상"
            holder.binding.remainStat.setTextColor(Color.parseColor("#32CD32"))
        } else if (storeSale[position].remain_stat == "some") {
            holder.binding.remainStat.text = "30~99개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#ff7f00"))
        } else if (storeSale[position].remain_stat == "few") {
            holder.binding.remainStat.text = "2~29개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#ff0000"))
        } else if (storeSale[position].remain_stat == "empty") {
            holder.binding.remainStat.text = "0~1개"
            holder.binding.remainStat.setTextColor(Color.parseColor("#000000"))
        } else if (storeSale[position].remain_stat == "break") {
            holder.binding.remainStat.text = "판매중지"
            holder.binding.remainStat.setTextColor(Color.parseColor("#808080"))
        }

        holder.binding.createdAt.text = storeSale[position].created_at
        holder.binding.stockAt.text = storeSale[position].stock_at

        holder.binding.storeDelete.setOnClickListener {
            favoriteDatabase = FavoriteDatabase.getInstance(context)

            var thread = Thread(Runnable {
                favoriteDatabase?.favoriteDao()?.delete(storeSale[position])
                //this.storeSale[position] = favoriteDatabase?.favoriteDao()?.getFavorites()
            })
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
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {}
}