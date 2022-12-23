package com.samanvay.a1oro.exploreListings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samanvay.a1oro.databinding.FragmentExploreListingsBinding
import com.samanvay.a1oro.exploreListings.Adapters.exploreAdapter
import com.samanvay.a1oro.exploreListings.Models.exploreModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExploreListings : Fragment(),View.OnClickListener,exploreAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentExploreListingsBinding
    private lateinit var adapter: exploreAdapter
    private  lateinit var list:ArrayList<exploreModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentExploreListingsBinding.inflate(layoutInflater)
        fillExploreRV()
        binding.recyclerView.layoutManager= LinearLayoutManager(activity)
        list= arrayListOf()
        adapter=exploreAdapter(this,list)
        binding.recyclerView.adapter=adapter


        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillExploreRV() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Locations").document("Uttar Pradesh")
            .collection("Districts")
            .document("Muzaffarnagar")
            .collection("Area")
            .document("Budhana")
            .collection("Hotels")
            .document("H001")
            .collection("bedCount")
            .document("single")
            .collection("roomType")
            .orderBy("hotelName", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) Toast.makeText(activity, "ERROR: " + error.message, Toast.LENGTH_SHORT).show()
                for (dc: DocumentChange in value?.documentChanges!!)
                    if (dc.type == DocumentChange.Type.ADDED) list.add(dc.document.toObject(exploreModel::class.java))
                adapter.notifyDataSetChanged()
            }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreListings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "position-> $position, name->${list[position]}", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}