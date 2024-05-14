package com.proyekmokom.chastethrift

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PenjualCatalogFragment : Fragment() {

    lateinit var rvCatalog: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_penjual_catalog, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCatalog = view.findViewById(R.id.rvPenjualCatalog)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        rvCatalog.layoutManager = layoutManager
        // Buat instance adapter dan pasang ke RecyclerView
        val adapter = RvCatalogAdapter(FakeList.catalog)
        rvCatalog.adapter = adapter




    }
}