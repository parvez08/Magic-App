package com.droid.magicapp.utils



import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Parvez on 08-02-2023.
 */
@SuppressLint("NotifyDataSetChanged")
abstract class GenericRecyclerViewAdapter<T>(private val context: Context) :
    RecyclerView.Adapter<GenericRVRowBindingViewHolder<*>>() {
    private val mObjectList: MutableList<T> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRVRowBindingViewHolder<*> {
        return getViewHolder(context, parent, viewType)
    }

    abstract fun getViewHolder(
        context: Context?,
        parentView: ViewGroup?,
        itemViewType: Int
    ): GenericRVRowBindingViewHolder<*>

    val adapterItems: List<T>
        get() = mObjectList

    fun getItem(position: Int): T? {
        return if (position > mObjectList.size) {
            null
        } else mObjectList[position]
    }

    override fun getItemCount(): Int {
        return if (mObjectList.size == 0) {
            1
        } else mObjectList.size
    }

    fun addData(vehicleModels: List<T>?) {
        mObjectList.addAll(vehicleModels!!)
        notifyDataSetChanged()
    }

    fun updateDataAtPosition(position: Int, data: T) {
        mObjectList[position] = data
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        mObjectList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position == mObjectList.size - 1 || mObjectList.size == 0)) {
            1
        } else super.getItemViewType(position)
    }

    fun clearData() {
        mObjectList.clear()
        notifyDataSetChanged()
    }

    fun clearAndAddData(vehicleModels: List<T>?) {
        mObjectList.clear()
        mObjectList.addAll(vehicleModels!!)
        notifyDataSetChanged()
    }

    fun updateDataAtIndex(vehicleModels: T, index: Int) {
        mObjectList[index] = vehicleModels
        this.notifyItemChanged(index)
    }

    fun removeItemFromIndex(index: Int) {
        mObjectList.removeAt(index)
        notifyDataSetChanged()
    }

    fun removeItemAtIndex(index: Int) {
        mObjectList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItemAtLast(vehicleModels: T) {
        mObjectList.add(vehicleModels)
        notifyItemInserted(mObjectList.size - 1)
    }

    fun addItemAtIndex(vehicleModels: T, index: Int) {
        mObjectList.add(index, vehicleModels)
        notifyItemInserted(index)
    }

    companion object {
        private val TAG = GenericRecyclerViewAdapter::class.java.simpleName
    }
}
