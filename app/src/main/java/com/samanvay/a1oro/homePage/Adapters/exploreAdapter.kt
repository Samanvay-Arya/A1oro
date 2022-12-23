package com.samanvay.a1oro.homePage.Adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samanvay.a1oro.R
import com.samanvay.a1oro.exploreListings.Models.exploreModel
import com.samanvay.a1oro.homePage.home


class exploreAdapter(var listener: home, var list:ArrayList<exploreModel>) :
    RecyclerView.Adapter<exploreAdapter.VH>(){
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val name:TextView=itemView.findViewById(R.id.hotelListingName)
        val image:ImageView=itemView.findViewById(R.id.hotelListingImage)
        val address:TextView=itemView.findViewById(R.id.hotelListingAddress)
        val price:TextView=itemView.findViewById(R.id.hotelListingPrice)
        val discount:TextView=itemView.findViewById(R.id.hotelListingDiscount)
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION) listener.onItemClick(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.hotel_listing_card_design,parent,false)
        return VH(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, p: Int) {
        holder.name.text=list[p].title
        Glide.with(holder.image).load(list[p].primaryImage).into(holder.image)
        holder.address.text=list[p].hotelAddress
        holder.price.text="\u20B9"+list[p].discountedPrice
    }

    override fun getItemCount(): Int {
        return list.size
    }


}



