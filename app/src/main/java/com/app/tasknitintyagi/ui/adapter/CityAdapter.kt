package com.app.tasknitintyagi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.model.FeedListModelItem

class CityAdapter(
    var context: Context?,
    private var mList: ArrayList<FeedListModelItem>?
) :
    RecyclerView.Adapter<ViewHolder>() {

    private var listener: ItemClick? = null
    private var enable: Boolean = false
    fun setClickListener(clickListener: ItemClick) {
        this.listener = clickListener
    }

    fun setClickEnable(enable: Boolean) {
        this.enable = enable
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_list_view, viewGroup, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.firstName.text = mList!![position].first_name
        holder.lastName.text = mList!![position].last_name
        holder.cityName.text = mList!![position].city

        holder.isCheckbox.isChecked = mList!![position].isSelected

        if (enable) {
            holder.isCheckbox.visibility = View.VISIBLE
            holder.viewLayout.setOnClickListener {
                mList!![position].isSelected = !mList!![position].isSelected
                listener?.onNotifydata()
            }
        } else {
            holder.isCheckbox.visibility = View.GONE
        }

        holder.viewLayout.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                mList!![position].isSelected = !mList!![position].isSelected
                listener!!.onLongPress(position, mList!![position])
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return mList?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var firstName = view.findViewById<TextView>(R.id.firstName)
    var lastName = view.findViewById<TextView>(R.id.lastName)
    var cityName = view.findViewById<TextView>(R.id.cityName)
    var isCheckbox = view.findViewById<CheckBox>(R.id.isCheckbox)
    var viewLayout = view.findViewById<RelativeLayout>(R.id.viewLayout)
}

interface ItemClick {
    fun onNotifydata()
    fun onClick(item: FeedListModelItem)
    fun onLongPress(position: Int, item: FeedListModelItem)
}
