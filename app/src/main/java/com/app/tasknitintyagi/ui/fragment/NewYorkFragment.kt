package com.app.tasknitintyagi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.model.FeedListModelItem
import com.app.tasknitintyagi.ui.adapter.CityAdapter
import com.app.tasknitintyagi.ui.adapter.ItemClick
import com.app.tasknitintyagi.util.Utility
import com.app.tasknitintyagi.util.Utility.isSelectionEnable


class NewYorkFragment : Fragment(), ItemClick {

    private var mAdapter: CityAdapter? = null
    var cityList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        cityList = view.findViewById<RecyclerView>(R.id.cityList)
        setAdapterData()
        return view
    }

    /**
     *  Set Adapter Data
     * */
    private fun setAdapterData() {
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = CityAdapter(context, Utility.getListFilter("NewYork"))
        mAdapter!!.setClickListener(this)
        if(isSelectionEnable){
            mAdapter!!.setClickEnable(true)
        }else{
            mAdapter!!.setClickEnable(false)
        }
        cityList!!.layoutManager = layoutManager
        cityList!!.adapter = mAdapter
    }

    override fun onNotifydata() {
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(item: FeedListModelItem) {
    }

    override fun onLongPress(position: Int, item: FeedListModelItem) {
        isSelectionEnable = if(isSelectionEnable){
            !isSelectionEnable
        }else{
            !isSelectionEnable
        }
        mAdapter!!.setClickEnable(isSelectionEnable)
        mAdapter!!.notifyDataSetChanged()
    }
}