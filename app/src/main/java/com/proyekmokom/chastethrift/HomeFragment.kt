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
import androidx.navigation.fragment.navArgs


class HomeFragment : Fragment() {

    lateinit var rvCatalog: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var itemList: ArrayList<ItemEntity>
    private lateinit var rvCatalogAdapter: RvCatalogAdapter

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

        // Retrieve the arguments using Safe Args
        val args: HomeFragmentArgs by navArgs()
        var idUser:Int = args.idUser

        db = AppDatabase.build(requireContext())
        itemList = ArrayList()
        rvCatalog.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        rvCatalogAdapter = RvCatalogAdapter(itemList, requireContext()) { itemId: Int? ->
            if (itemId != null){
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("item_id", itemId)
                startActivity(intent)
            }
        }
        rvCatalog.adapter = rvCatalogAdapter

        coroutine.launch(Dispatchers.IO) {
            val tmpItemList = db.itemDao().fetchStatusTrue()
            itemList.clear()
            itemList.addAll(tmpItemList)
            withContext(Dispatchers.Main) {
                rvCatalogAdapter.notifyDataSetChanged()
            }
        }
    }
}

