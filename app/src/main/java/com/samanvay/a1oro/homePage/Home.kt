package com.samanvay.a1oro.homePage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samanvay.a1oro.databinding.FragmentHomeBinding
import com.samanvay.a1oro.homePage.Adapters.exploreAdapter
import com.samanvay.a1oro.exploreListings.Models.exploreModel
import com.samanvay.a1oro.homePage.Adapters.availableLocationsAdapter
import com.samanvay.a1oro.homePage.Models.availableLocationModel
import com.samanvay.a1oro.login.Login

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment(),View.OnClickListener, availableLocationsAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var availableLocationAdapter:availableLocationsAdapter
    private  lateinit var locationList:ArrayList<availableLocationModel>
    private lateinit var expAdapter: exploreAdapter
    private  lateinit var exploreList:ArrayList<exploreModel>

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
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(layoutInflater)
        fillAvailableLocationRV()
        fillExploreRV()

        binding.availableLocations.layoutManager=LinearLayoutManager(activity)
        locationList= arrayListOf()
       availableLocationAdapter=availableLocationsAdapter(this,locationList)
        binding.availableLocations.adapter=availableLocationAdapter

        binding.homeExploreRV.layoutManager=LinearLayoutManager(activity)
        exploreList= arrayListOf()
        expAdapter= exploreAdapter(this,exploreList)
        binding.homeExploreRV.adapter=expAdapter

        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun fillExploreRV() {
        val db= FirebaseFirestore.getInstance()
        db.collection("Locations").document("Uttar Pradesh")
            .collection("Muzaffarnagar")
            .document("Budhana")
            .collection("Hotels")
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(activity, "ERROR: "+error.message, Toast.LENGTH_SHORT).show()
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type== DocumentChange.Type.ADDED){
                        exploreList.add(dc.document.toObject(exploreModel::class.java))
                    }
                    else if(dc.type== DocumentChange.Type.MODIFIED){
//                        overridePendingTransition(0, 0)
//                        startActivity(intent)
//                        overridePendingTransition(0, 0)
                    }
                }
                expAdapter.notifyDataSetChanged()

            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillAvailableLocationRV() {
        val db=FirebaseFirestore.getInstance()
        db.collection("Locations").document("Uttar Pradesh")
            .collection("Muzaffarnagar")
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(activity, "ERROR: "+error.message, Toast.LENGTH_SHORT).show()
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type== DocumentChange.Type.ADDED){
                        locationList.add(dc.document.toObject(availableLocationModel::class.java))
                    }
                    else if(dc.type== DocumentChange.Type.MODIFIED){
//                        overridePendingTransition(0, 0)
//                        startActivity(intent)
//                        overridePendingTransition(0, 0)
                    }
                }
                availableLocationAdapter.notifyDataSetChanged()

            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "position-> $position, name->${locationList[position]}", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}