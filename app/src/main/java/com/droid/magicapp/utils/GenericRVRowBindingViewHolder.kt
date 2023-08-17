package com.droid.magicapp.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class GenericRVRowBindingViewHolder<T>(itemView: View, val binding : T) :
    RecyclerView.ViewHolder(itemView)