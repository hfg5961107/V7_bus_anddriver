package com.rvakva.bus.home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.callPhone
import com.rvakva.travel.devkit.expend.glideWithRoundInto

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
        val order = data[position]

        order.customerAvatar?.let {
            if (it.contains("http") || it.contains("https")) {
                holder.passengerHeaderIv.glideWithRoundInto(it, 10)
            } else {
                holder.passengerHeaderIv.glideWithRoundInto(Config.IMAGE_SERVER + it, 10)
            }
        } ?: holder.passengerHeaderIv.setImageResource(R.drawable.com_icon_passenger)

        holder.passengerNameTv.text = "${order.customerName} ${order.passengerNum}人"

        showStatus(holder.passengerStatusTv, order)

        if (order.status < OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value) {
            holder.passengerPhoneIv.visibility = View.VISIBLE

            holder.passengerPhoneIv.setOnClickListener {
                if (!order.customerPhone.isNullOrBlank()) {
                    context.callPhone(order.customerPhone)
                }
            }

        } else {
            holder.passengerPhoneIv.visibility = View.GONE
        }

        order.orderAddress?.forEach {
            if (it.type == 1) {
                holder.passengerStartSiteTv.text = it.address
            } else if (it.type == 2) {
                holder.passengerEndSiteTv.text = it.address
            }
        }

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

    private fun showStatus(textView: TextView, passengerModel: PassengerModel) = run {
        when (passengerModel.status) {
            OrderStatusTypeEnum.ORDER_STATUS_WAIT_START.value,
            OrderStatusTypeEnum.ORDER_STATUS_START.value -> {
                textView.text = " / 待上车"
                textView.setTextColor(context.resources.getColor(R.color.black_desc))
            }
            OrderStatusTypeEnum.ORDER_STATUS_PICKUP.value -> {
                textView.text = " / 正在接人"
                textView.setTextColor(context.resources.getColor(R.color.color_yellow))
            }
            OrderStatusTypeEnum.ORDER_STATUS_WAITING.value -> {
                textView.text = " / 等待乘客上车"
                textView.setTextColor(context.resources.getColor(R.color.color_yellow))
            }
            OrderStatusTypeEnum.ORDER_STATUS_SKIP.value -> {
                textView.text = " / 未上车"
                textView.setTextColor(context.resources.getColor(R.color.color_red))
            }
            OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value -> {
                textView.text = " / 已上车"
                textView.setTextColor(context.resources.getColor(R.color.color_green))
            }
            OrderStatusTypeEnum.ORDER_STATUS_SENDING.value -> {
                textView.text = " / 正在送人"
                textView.setTextColor(context.resources.getColor(R.color.color_yellow))
            }
            OrderStatusTypeEnum.ORDER_STATUS_COMPLETE.value -> {
                textView.text = " / 已送达"
                textView.setTextColor(context.resources.getColor(R.color.black_desc))
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