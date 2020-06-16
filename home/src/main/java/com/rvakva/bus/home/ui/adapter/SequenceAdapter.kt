package com.rvakva.bus.home.ui.adapter

import android.content.Context
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.home.R
import com.rvakva.bus.home.weiget.OnItemTouchListener
import com.rvakva.travel.devkit.expend.loge
import java.util.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/10 上午9:38
 */
class SequenceAdapter(private val context: Context)
    : RecyclerView.Adapter<SequenceAdapter.ViewHolder>(), OnItemTouchListener {

     private var itemTouchHelper: ItemTouchHelper? = null

    var data: MutableList<PassengerModel> = mutableListOf()

    /**
     * 设置排序列表
     *
     * @param sequences
     */
    fun setDataList(dataList: MutableList<PassengerModel>) {
        this.data = dataList

        setMinAndMax()

        notifyDataSetChanged()
    }

    /**
     * 拖动排序帮助类
     *
     * @param itemTouchHelper
     */
    fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper?) {
        this.itemTouchHelper = itemTouchHelper
    }

    /**
     * 最大最小位置
     */
    private var minPos = -1
    private var maxPos = -1

    /**
     * 设置最小最大数
     *
     * @param min
     * @param max
     */
    fun setMinAndMax() {
        for (i in data.indices) {
            if (data[i].type == 1) {
                minPos = i
                return
            }
        }
        maxPos = data.size-1
        "$minPos/$maxPos".loge()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SequenceAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_sequence, parent, false)
        val frameLayout = FrameLayout(parent.context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        frameLayout.addView(view, params)

        return ViewHolder(frameLayout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = data[position]

        holder.linSeqNum.setOnTouchListener { v, event ->
            "----" + event.action + "\n-----" + event.actionMasked.loge();

            if (position >= minPos) {
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper?.startDrag(holder);
                }
            }
            return@setOnTouchListener false
        }

        holder.linSeqNum.visibility = View.VISIBLE;
        holder.tvSeqNum.text = "${model.shiftNo}";

        if (!checkStatus(data)){
            if (model.status >= OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                holder.seqImg.visibility = View.GONE;
                holder.tvStatus.visibility = View.GONE;
                holder.linSeqNum.background.alpha = 128;

                holder.tv_get.visibility = View.VISIBLE;

                if (model.status == OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                    if (model.type == 1){
                        holder.linSeqNum.visibility = View.GONE;
                        holder.tv_get.visibility = View.GONE;
                        holder.seqImg.visibility = View.VISIBLE;
                    }else{
                        holder.tv_get.text = "跳过";
                    }
                }else if (model.status == OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value){
                    holder.tv_get.text = "已上车";
                }
            }else if (model.status >= OrderStatusTypeEnum.ORDER_STATUS_WAIT_START.value && model.status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value){
                holder.tvStatus.visibility = View.GONE;
                holder.seqImg.visibility = View.GONE;
                holder.linSeqNum.background.alpha = 255;
            }
        }else{
            if (model.status == OrderStatusTypeEnum.ORDER_STATUS_COMPLETE.value || model.status == OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                holder.seqImg.visibility = View.GONE;
                holder.tvStatus.visibility = View.GONE;
                holder.linSeqNum.background.alpha = 128;

                holder.tv_get.visibility = View.VISIBLE;

                if (model.type == 1){
                    holder.linSeqNum.visibility = View.GONE;
                    holder.tv_get.visibility = View.GONE;
                    holder.seqImg.visibility = View.VISIBLE;
                }else{
                    if (model.loadType == 2) {
                        holder.tv_get.text = "跳过";
                    }else if (model.loadType == 3){
                        holder.tv_get.text = "已下车";
                    }
                }
            }else if (model.status == OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value || model.status == OrderStatusTypeEnum.ORDER_STATUS_SENDING.value){
                holder.tvStatus.visibility = View.GONE;
                holder.seqImg.visibility = View.GONE;
                holder.linSeqNum.background.alpha = 255;
            }
        }
    }


    /**
     * 判断上车还是下车 ture 下车 false上车
     */
    private fun checkStatus(list: List<PassengerModel>): Boolean {
        for (i in list.indices){
            if (list[i].status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                return false
            }
        }
        return true
    }


     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var seqImg: ImageView
        var linSeqNum: RelativeLayout
        var tvStatus: TextView
        var tvSeqNum: TextView
        var tv_get: TextView

        init {
            seqImg = itemView.findViewById(R.id.seq_car_img)
            linSeqNum = itemView.findViewById(R.id.lin_seq_num)
            tvStatus = itemView.findViewById(R.id.tv_status)
            tvSeqNum = itemView.findViewById(R.id.tv_seq_num)
            tv_get = itemView.findViewById(R.id.tv_get)
        }
    }

    override fun onMove(from: Int,to: Int): Boolean {
        var toPosition = to
        var fromPosition = from

        if (maxPos != -1) {
            if (toPosition >= maxPos) {
                toPosition = maxPos - 1;
            }
        }
        if (minPos != -1) {
            if (toPosition <= minPos) {
                toPosition = minPos + 1;
            }
        }

        if (fromPosition < toPosition) { //从上往下拖动，每滑动一个item，都将list中的item向下交换，向上滑同理。
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        //注意此处只是notifyItemMoved并没有notifyDataSetChanged
        //原因下面会说明
        onItemMoveListener?.let {
            it.onMove()
        }
        return true
    }

    override fun onSwiped(position: Int) {

    }


    private var onItemMoveListener: OnItemMoveListener? = null

    /**
     * 设置列表点击监听
     *
     * @param onItemMoveListener
     */
    fun setOnItemMoveListener(onItemMoveListener: OnItemMoveListener) {
        this.onItemMoveListener = onItemMoveListener
    }

    /**
     * 子项点击接口
     */
    interface OnItemMoveListener {
        /**
         * 移动监听
         *
         */
        fun onMove()
    }
}