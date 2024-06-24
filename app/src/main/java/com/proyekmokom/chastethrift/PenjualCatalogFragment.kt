package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class PenjualCatalogFragment : Fragment() {

    lateinit var rvCatalog: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var itemList: ArrayList<ItemEntity>
    private lateinit var rvCatalogAdapter: RvCatalogAdapter

    private val viewModel: PenjualCatalogViewModel by viewModels {
        PenjualCatalogViewModelFactory(AppDatabase.build(requireContext()))
    }

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

        // Retrieve the arguments using Safe Args
        val args: PenjualCatalogFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        itemList = ArrayList()

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val fetchedItems = viewModel.fetchIdUserAndStatusTrue(idUser)
                itemList.clear() // Clear existing items
                itemList.addAll(fetchedItems) // Add fetched items to itemList
                rvCatalogAdapter.notifyDataSetChanged() // Notify adapter of data change
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun setupRecyclerView() {
        rvCatalog.layoutManager = LinearLayoutManager(requireContext())
        rvCatalogAdapter = RvCatalogAdapter(itemList, requireContext()) { itemId ->
            if (itemId != null){
                val action = PenjualCatalogFragmentDirections
                    .actionPenjualCatalogFragmentToPenjualEditFragment(itemId)
                findNavController().navigate(action)
            }
        }
        rvCatalog.adapter = rvCatalogAdapter
    }
}