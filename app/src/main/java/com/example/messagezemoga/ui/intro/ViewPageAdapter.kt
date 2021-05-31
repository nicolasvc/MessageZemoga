package com.example.messagezemoga.ui.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messagezemoga.R

class ViewPageAdapter (private var listScreenItem: List<ScreenItem>,
                       private val context: Context
) :
    RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {

    //region Override
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvtitleintro: TextView = itemView.findViewById(R.id.tv_titlle_intro)
        val tvdescripintro: TextView = itemView.findViewById(R.id.tv_descrip_intro)
        val tvimageview: ImageView = itemView.findViewById(R.id.iv_intro)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_screen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val screenItem = listScreenItem[position]
        holder.tvtitleintro.text = screenItem.title
        holder.tvdescripintro.text = screenItem.description
        holder.tvimageview.setImageResource(screenItem.screenImg)
    }

    override fun getItemCount(): Int = listScreenItem.size
    //endregion


    fun setListaCiudades(listScreen: List<ScreenItem>) {
        listScreenItem = listScreen
    }




}