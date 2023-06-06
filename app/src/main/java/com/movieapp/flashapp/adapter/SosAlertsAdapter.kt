package com.movieapp.flashapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.movieapp.flashapp.R
import com.movieapp.flashapp.data.ColorLightData
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.data.SosAlertsData
import com.movieapp.flashapp.databinding.RowBinding

class SosAlertsAdapter : RecyclerView.Adapter<SosAlertsAdapter.RecyclerViewHolder>() {


    private var sosalerts=listOf<SosAlertsData>()

    fun setFlashData(newData:List<SosAlertsData>){
        this.sosalerts=newData
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inf=RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerViewHolder(inf)
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.binding.name.text= sosalerts[position].name
            holder.binding.ratingValue.text=sosalerts[position].ratingValue.toString()
            holder.binding.downloads.text=sosalerts[position].downloads
            holder.binding.ratingCount.text=sosalerts[position].ratingCount.toString()

        if (sosalerts[position].publishDate.day<10 && sosalerts[position].publishDate.month<10){
            holder.binding.publishDate.text="0${sosalerts[position].publishDate.day}-0${sosalerts[position].publishDate.month}-${sosalerts[position].publishDate.year}"
        }else if(sosalerts[position].publishDate.day<10)
            holder.binding.publishDate.text="0${sosalerts[position].publishDate.day}-${sosalerts[position].publishDate.month}-${sosalerts[position].publishDate.year}"
        else if (sosalerts[position].publishDate.month<10){
            holder.binding.publishDate.text="${sosalerts[position].publishDate.day}-0${sosalerts[position].publishDate.month}-${sosalerts[position].publishDate.year}"
        }
        else{
            holder.binding.publishDate.text="${sosalerts[position].publishDate.day}-${sosalerts[position].publishDate.month}-${sosalerts[position].publishDate.year}"
        }

        Glide.with(holder.itemView.context)
                .load(sosalerts[position].iconUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.baseline_error_24)
                .into(holder.binding.iconUrl)
    }

    override fun getItemCount(): Int {
        return sosalerts.size
    }
}