package com.movieapp.flashapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.movieapp.flashapp.R
import com.movieapp.flashapp.data.FlashLightData
import com.movieapp.flashapp.databinding.RowBinding

class FlashLightAdapter : RecyclerView.Adapter<FlashLightAdapter.RecyclerViewHolder>() {


    private var flashData=listOf<FlashLightData>()

    fun setFlashData(newData:List<FlashLightData>){
        this.flashData=newData
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(var binding: RowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inf=RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerViewHolder(inf)
    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.binding.name.text= flashData[position].name
            holder.binding.ratingValue.text=flashData[position].ratingValue.toString()
            holder.binding.downloads.text=flashData[position].downloads
            holder.binding.ratingCount.text=flashData[position].ratingCount.toString()


        if (flashData[position].publishDate.day<10 && flashData[position].publishDate.month<10){
            holder.binding.publishDate.text="0${flashData[position].publishDate.day}-0${flashData[position].publishDate.month}-${flashData[position].publishDate.year}"
        }else if(flashData[position].publishDate.day<10)
            holder.binding.publishDate.text="0${flashData[position].publishDate.day}-${flashData[position].publishDate.month}-${flashData[position].publishDate.year}"
        else if (flashData[position].publishDate.month<10){
            holder.binding.publishDate.text="${flashData[position].publishDate.day}-0${flashData[position].publishDate.month}-${flashData[position].publishDate.year}"
        }
        else{
            holder.binding.publishDate.text="${flashData[position].publishDate.day}-${flashData[position].publishDate.month}-${flashData[position].publishDate.year}"
        }

            Glide.with(holder.itemView.context)
                .load(flashData[position].iconUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.baseline_error_24)
                .into(holder.binding.iconUrl)
    }

    override fun getItemCount(): Int {
        return flashData.size
    }
}