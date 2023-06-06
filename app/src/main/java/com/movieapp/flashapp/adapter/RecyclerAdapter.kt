package com.movieapp.flashapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.movieapp.flashapp.R
import com.movieapp.flashapp.data.ColorLightData

import com.movieapp.flashapp.databinding.RowBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private var data = listOf<ColorLightData>()


    fun setData(newData:List<ColorLightData>){
        this.data=newData
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inf=RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerViewHolder(inf)
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

            holder.binding.name.text= data[position].name
            holder.binding.ratingValue.text=data[position].ratingValue.toString()
            holder.binding.downloads.text=data[position].downloads
            holder.binding.ratingCount.text=data[position].ratingCount.toString()

            //Böyle yapma sebebimse ay ve günlerin başında 0 yok 10dan küçük tek haneli sayıların başına 0 eklemek istedim
            if (data[position].publishDate.day<10 && data[position].publishDate.month<10){
                holder.binding.publishDate.text="0${data[position].publishDate.day}-0${data[position].publishDate.month}-${data[position].publishDate.year}"
            }else if(data[position].publishDate.day<10)
                holder.binding.publishDate.text="0${data[position].publishDate.day}-${data[position].publishDate.month}-${data[position].publishDate.year}"
            else if (data[position].publishDate.month<10){
                holder.binding.publishDate.text="${data[position].publishDate.day}-0${data[position].publishDate.month}-${data[position].publishDate.year}"
            }
            else{
                holder.binding.publishDate.text="${data[position].publishDate.day}-${data[position].publishDate.month}-${data[position].publishDate.year}"
            }
        Glide.with(holder.itemView.context)
                .load(data[position].iconUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.baseline_error_24)
                .into(holder.binding.iconUrl)


    }

    override fun getItemCount(): Int {
        return data.size
    }
}