package com.rvakva.bus.home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.home.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/3 下午1:55
 */
class OrderPassengerAdapter(private val context: Context) :
    RecyclerView.Adapter<OrderPassengerAdapter.MyViewHolder>() {

    var data: MutableList<PassengerModel> = mutableListOf()
    var type: Int = 0

    fun setData(list: MutableList<PassengerModel>, type: Int) {
        this.data.clear()
        this.data.addAll(list)
        this.type = type
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_order_passenger,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (type) {
            1 -> {
//              站点-站点
                holder.passengerStartSiteLl.visibility = View.GONE
                holder.passengerEndSiteLl.visibility = View.GONE
            }
            2 -> {
//                站点-送人
                holder.passengerStartSiteLl.visibility = View.GONE
                holder.passengerEndSiteLl.visibility = View.VISIBLE
            }
            3 -> {
//                接人-送人
                holder.passengerStartSiteLl.visibility = View.VISIBLE
                holder.passengerEndSiteLl.visibility = View.VISIBLE
            }
            4 -> {
//                接人-站点
                holder.passengerStartSiteLl.visibility = View.VISIBLE
                holder.passengerEndSiteLl.visibility = View.GONE
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val passengerHeaderIv: ImageView = itemView.findViewById(R.id.passengerHeaderIv)
        val passengerNameTv: TextView = itemView.findViewById(R.id.passengerNameTv)
        val passengerStatusTv: TextView = itemView.findViewById(R.id.passengerStatusTv)
        val passengerPhoneIv: ImageView = itemView.findViewById(R.id.passengerPhoneIv)
        val passengerStartSiteLl: LinearLayout = itemView.findViewById(R.id.passengerStartSiteLl)
        val passengerStartSiteTv: TextView = itemView.findViewById(R.id.passengerStartSiteTv)
        val passengerEndSiteLl: LinearLayout = itemView.findViewById(R.id.passengerEndSiteLl)
        val passengerEndSiteTv: TextView = itemView.findViewById(R.id.passengerEndSiteTv)
    }
}