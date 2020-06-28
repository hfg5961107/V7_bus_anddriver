package com.rvakva.bus.personal.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.utils.MyDividerItemDecoration
import com.rvakva.bus.personal.utils.RecyclerItemClickListenner
import com.xu.xpopupwindow.XPopupWindow

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:日期类型选择弹窗
 * @Author: lch
 * @Date: 2020/6/28 13:51
 **/
class MenuPopupWindow: XPopupWindow {

    private var rv: RecyclerView? = null
    private var adapter: CustomAdapter? = null
    private var list: List<String> = emptyList()
    private var manager: LinearLayoutManager? = null

    var mOnItemClickLitener:RecyclerItemClickListenner?=null

    constructor(ctx: Context): super(ctx)

    constructor(ctx: Context, w: Int, h: Int): super(ctx, w, h)

    override fun getLayoutId(): Int {
        return R.layout.popup_menu
    }

    override fun getLayoutParentNodeId(): Int {
        return R.id.menu_parent
    }

    override fun initViews() {
        rv = findViewById(R.id.rv_menu)
    }

    fun OnItemClickLitener (clickLitener: RecyclerItemClickListenner?){
        mOnItemClickLitener=clickLitener
        adapter?.setClickLitener(mOnItemClickLitener)
    }

    override fun initData() {
        list = listOf("今日", "昨日", "本周","本月","本年")
        adapter = CustomAdapter(list, getContext())
        manager = LinearLayoutManager(getContext())

        val itemDecoration = MyDividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.divider_vertical))
        rv?.addItemDecoration(itemDecoration)

//        rv?.addItemDecoration(DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL))
        rv?.setHasFixedSize(true)
        rv?.layoutManager = manager
        rv?.itemAnimator = DefaultItemAnimator()
        rv?.adapter = adapter
    }

    override fun startAnim(view: View): Animator? {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 500
        return animator
    }

    override fun exitAnim(view: View): Animator? {
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        animator.duration = 500
        return animator
    }

    override fun animStyle(): Int {
        return -1
    }

    class CustomAdapter(private val list: List<String>, private var context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        var mOnItemClickLitener:RecyclerItemClickListenner?=null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_menu,parent, false))
        }

        fun setClickLitener (clickLitener: RecyclerItemClickListenner?){
            mOnItemClickLitener=clickLitener
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvItem?.text = list[position]

            holder.tvItem?.setOnClickListener {
                mOnItemClickLitener?.onRecyclerViewItemClick(position)
            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvItem: TextView? = null

            init {
                tvItem = itemView.findViewById(R.id.rv_item_menu_text)
            }
        }

    }

}