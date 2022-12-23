package com.samanvay.a1oro.homePage.Adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.samanvay.a1oro.R
import com.samanvay.a1oro.homePage.Adapters.availableLocationsAdapter.VH
import com.samanvay.a1oro.homePage.Models.availableLocationModel
import com.samanvay.a1oro.login.Login


class availableLocationsAdapter(var listener: OnItemClickListener,var list:ArrayList<availableLocationModel>) :
    RecyclerView.Adapter<VH>(){
    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val name:TextView=itemView.findViewById(R.id.nameAvailableLocations)
        val image:ImageView=itemView.findViewById(R.id.imageAvailableLocations)
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION) listener.onItemClick(position)
        }


    }
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): availableLocationsAdapter.VH {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.available_locations_card_design,parent,false);
        return VH(v)
    }

    override fun onBindViewHolder(holder: availableLocationsAdapter.VH, p: Int) {
        holder.name.text=list[p].name
        Glide.with(holder.image).load(list[p].image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}