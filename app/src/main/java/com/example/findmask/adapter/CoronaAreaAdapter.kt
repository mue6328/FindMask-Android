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
import android.content.DialogInterface
import com.example.findmask.database.FavoriteDatabase
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.findmask.database.CoronaAreaDatabase
import com.example.findmask.databinding.ItemCoronaAreaBinding
import com.example.findmask.databinding.ItemMoreinfoBinding
import com.example.findmask.model.CoronaArea
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class CoronaAreaAdapter : RecyclerView.Adapter<CoronaAreaAdapter.Holder>() {

    private var coronaArea = listOf<CoronaArea>()

    private lateinit var context: Context

    private var coronaAreaDatabase: CoronaAreaDatabase? = null

    fun setItem(list: List<CoronaArea>, context: Context) {
        this.coronaArea = list
        this.context = context
        notifyDataSetChanged()
    }

    fun addItem(coronaElement: CoronaArea) {
        coronaAreaDatabase = CoronaAreaDatabase.getInstance(context)
        var runnable = Runnable {
            coronaAreaDatabase?.coronaAreaDao()?.insert(coronaElement)
        }
        var thread = Thread(runnable)
        thread.start()
    }

    override fun getItemCount(): Int {
        return if (coronaArea != null)
            coronaArea.size
        else
            0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.areaName.text = coronaArea[position].areaName
        holder.binding.newCasePersons.text = coronaArea[position].newCase + " 명" +
                "(해외 " + coronaArea[position].newFcase + "명, " +
                "지역 " + coronaArea[position].newCcase + "명)"
        holder.binding.totalCasePersons.text = coronaArea[position].totalCase + " 명"
        holder.binding.recoveredPersons.text = coronaArea[position].recovered + " 명"
        var covered = coronaArea[position].totalCase.replace(",","").toInt() -
                coronaArea[position].recovered.replace(",","").toInt() -
                coronaArea[position].death.replace(",","").toInt()
        holder.binding.coveredPersons.text = covered.toString() + " 명"
        holder.binding.deathPersons.text = coronaArea[position].death + " 명"

        holder.binding.delete.setOnClickListener {
            coronaAreaDatabase = CoronaAreaDatabase.getInstance(context)

            val runnable = Runnable {
                if (coronaAreaDatabase?.coronaAreaDao()?.getAreas() != null) {
                    coronaAreaDatabase?.coronaAreaDao()?.delete(coronaArea[position])
                }
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
        val binding =
            ItemCoronaAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val binding: ItemCoronaAreaBinding) : RecyclerView.ViewHolder(binding.root) {}
}