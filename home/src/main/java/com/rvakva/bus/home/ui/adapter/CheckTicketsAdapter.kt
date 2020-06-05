package com.rvakva.bus.home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.glideWithRoundInto

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/5 下午3:23
 */
class CheckTicketsAdapter (private val context: Context,val data: MutableList<PassengerModel>) :
    RecyclerView.Adapter<CheckTicketsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_check_tickets,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = data[position]

        holder.itemPassengerNameTv.text = "${order.customerName} ${order.passengerNum}人"

        if (order.status < OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value) {
            holder.itemPassengerPhoneTv.visibility = View.VISIBLE
        } else {
            holder.itemPassengerPhoneTv.visibility = View.GONE
        }

        holder.itemHasCarRl.setOnClickListener {
            order.loadType = 3
            notifyItemChanged(position)
        }

        holder.itemNoCarRl.setOnClickListener {
            order.loadType = 2
            notifyItemChanged(position)
        }

        when (order.loadType) {
            1 -> {
                holder.itemHasCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_line_white_bg)
                holder.itemNoCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_line_white_bg)
                holder.itemHasCarIv.visibility = View.GONE
                holder.itemNoCarIv.visibility = View.GONE
                holder.itemHasCarTv.setTextColor(context.resources.getColor(R.color.black_desc))
                holder.itemNoCarTv.setTextColor(context.resources.getColor(R.color.black_desc))
            }
            2 -> {
                holder.itemHasCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_line_white_bg)
                holder.itemNoCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_blue_line_white_bg)
                holder.itemHasCarIv.visibility = View.GONE
                holder.itemNoCarIv.visibility = View.VISIBLE
                holder.itemHasCarTv.setTextColor(context.resources.getColor(R.color.black_desc))
                holder.itemNoCarTv.setTextColor(context.resources.getColor(R.color.color_blue))
            }
            3 -> {
                holder.itemHasCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_blue_line_white_bg)
                holder.itemNoCarRl.background = context.resources.getDrawable(R.drawable.cor4_dotted_line_white_bg)
                holder.itemHasCarIv.visibility = View.VISIBLE
                holder.itemNoCarIv.visibility = View.GONE
                holder.itemHasCarTv.setTextColor(context.resources.getColor(R.color.color_blue))
                holder.itemNoCarTv.setTextColor(context.resources.getColor(R.color.black_desc))
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPassengerNameTv: TextView = itemView.findViewById(R.id.itemPassengerNameTv)
        val itemPassengerPhoneTv: TextView = itemView.findViewById(R.id.itemPassengerPhoneTv)
        val itemHasCarRl: RelativeLayout = itemView.findViewById(R.id.itemHasCarRl)
        val itemHasCarIv: ImageView = itemView.findViewById(R.id.itemHasCarIv)
        val itemNoCarRl: RelativeLayout = itemView.findViewById(R.id.itemNoCarRl)
        val itemNoCarIv: ImageView = itemView.findViewById(R.id.itemNoCarIv)

        val itemHasCarTv: TextView = itemView.findViewById(R.id.itemHasCarTv)
        val itemNoCarTv: TextView = itemView.findViewById(R.id.itemNoCarTv)
    }
}