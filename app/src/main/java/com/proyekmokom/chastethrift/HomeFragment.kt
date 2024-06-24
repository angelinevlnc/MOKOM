package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs


class HomeFragment : Fragment() {

    lateinit var rvCatalog: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var itemList: ArrayList<ItemEntity>
    private lateinit var rvCatalogAdapter: RvCatalogAdapter

    private val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels {
        val appDatabase = AppDatabase.build(requireContext())
        val itemRepository = ItemRepository(appDatabase.itemDao())
        HomeViewModelFactory(itemRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCatalog = view.findViewById(R.id.rvCatalog)

        var idUser:Int = args.idUser

        itemList = ArrayList()

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val liveData = viewModel.fetchItems()
                withContext(Dispatchers.Main) {
                    liveData.observe(viewLifecycleOwner) { fetchedItems ->
                        itemList.clear() // Clear existing items
                        itemList.addAll(fetchedItems) // Add fetched items to itemList
                        rvCatalogAdapter.notifyDataSetChanged() // Notify adapter of data change
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupRecyclerView() {
        rvCatalog.layoutManager = LinearLayoutManager(requireContext())
        rvCatalogAdapter = RvCatalogAdapter(itemList, requireContext()) { itemId ->
            itemId?.let {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("item_id", it)
                startActivity(intent)
            }
        }
        rvCatalog.adapter = rvCatalogAdapter
    }
}

