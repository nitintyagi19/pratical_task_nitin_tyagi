package com.app.tasknitintyagi.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.FragmentMainBinding
import com.app.tasknitintyagi.model.FeedListModelItem
import com.app.tasknitintyagi.ui.adapter.CityAdapter
import com.app.tasknitintyagi.ui.adapter.ItemClick
import com.app.tasknitintyagi.util.Utility
import com.app.tasknitintyagi.util.Utility.getListFilter
import com.app.tasknitintyagi.util.Utility.isSelectionEnable


class AllFragment : Fragment(), ItemClick {

    private var mAdapter: CityAdapter? = null
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.searchItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter!!.updateList(
                    Utility.getFilter(
                        s.toString(),
                        getListFilter("ALL")
                    ) as java.util.ArrayList<FeedListModelItem>
                )
            }

            override fun afterTextChanged(s: Editable) {}
        })

        setAdapterData()
        return binding.root
    }


    override fun onResume() {
        Log.e("all list", "${getListFilter("ALL")}")
        super.onResume()
    }

    /**
     *  Set Adapter Data
     * */
    private fun setAdapterData() {
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        Log.e("all list", "${getListFilter("ALL")}")

        mAdapter = CityAdapter(context, getListFilter("ALL"))
        mAdapter!!.setClickListener(this)
        if (isSelectionEnable) {
            mAdapter!!.setClickEnable(true)
        } else {
            mAdapter!!.setClickEnable(false)
        }
        binding.cityList.layoutManager = layoutManager
        binding.cityList.adapter = mAdapter
    }

    override fun onNotifydata() {
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(item: FeedListModelItem) {
    }

    override fun onLongPress(position: Int, item: FeedListModelItem) {
        isSelectionEnable = if (isSelectionEnable) {
            !isSelectionEnable
        } else {
            !isSelectionEnable
        }
        mAdapter!!.setClickEnable(isSelectionEnable)
        mAdapter!!.notifyDataSetChanged()
    }
}