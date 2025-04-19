package com.example.android.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.entity.Order

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.myhold>() {

    private var orders: MutableList<Order> = mutableListOf()

    //    实例化这个接口
    private var longclick: onItemLongClick? = null

    private var clicklistener:onItemclick? = null

    //从外部获取list传给orders
    @SuppressLint("NotifyDataSetChanged")
    fun setdata(lists: MutableList<Order>) {
        this.orders = lists
        notifyDataSetChanged();
    }

    //    初始化视图
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.myhold {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item_list, parent, false)
        return myhold(view)
    }

    //    绑定数据
    override fun onBindViewHolder(holder: OrderAdapter.myhold, position: Int) {
        val order = orders[position]
//        将每一行的数据传入这个方法与组件绑定
        holder.bind(order)
//        长按删除的监听回调
        holder.itemView.setOnLongClickListener {
            longclick?.onItemLongClick(position)
            true
        }
//        点击编辑预约记录回调
        holder.itemView.setOnClickListener {
            clicklistener?.onItemclick(position)
        }

    }

    //返回数据个数
    override fun getItemCount(): Int {
        return orders.size
    }

    //绑定数据到视图
    class myhold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        预约人名字
        private val on: TextView = itemView.findViewById(R.id.order_name)

        //    接种人名字
        private val jn: TextView = itemView.findViewById(R.id.jiezhong_name)

        //    预约疫苗
        private val ov: TextView = itemView.findViewById(R.id.vaccine_name)

        //    预约时间
        private val ot: TextView = itemView.findViewById(R.id.order_time)

        fun bind(order: Order) {
//            将数据与组件绑定
            on.text = order.user_name
            jn.text = order.order_name
            ov.text = order.order_vaccine
            ot.text = order.order_time

        }
    }

    //    长按点击接口
    interface onItemLongClick {
        fun onItemLongClick(position: Int)
    }

    // 设置长按监听器
    fun setOnItemLongClickListener(listener: onItemLongClick) {
        this.longclick = listener
    }
//    设置点击接口
    interface onItemclick{
    fun onItemclick(position: Int)
    }
//    设置点击监听器
fun setonItemclickListener(listener: onItemclick){
    this.clicklistener = listener
}

    //    根据position删除的方法
    fun deletebyposition(position: Int) {
        if (position in 0 until orders.size) {
            orders.removeAt(position)
            notifyItemRemoved(position)//通知单项删除
            notifyItemRangeChanged(position, itemCount - position)//更新后边剩下的项的位置
        }
    }

    //    根据position返回一个order对象
    fun fetchorderbyposition(position: Int): Order {
        val o: Order = orders.get(position)
        Log.d("deleteorderfromDB", o.order_name);
        return o;
    }

    fun clearData() {
        orders.clear()
    }


}
