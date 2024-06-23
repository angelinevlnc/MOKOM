package com.proyekmokom.chastethrift

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminApprovalListFragment : Fragment() {

    lateinit var rvCatalog: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var itemList: ArrayList<ItemEntity>
    private lateinit var rvCatalogAdapter: RvCatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_approval_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCatalog = view.findViewById(R.id.rv_admin_approval)

        db = AppDatabase.build(requireContext())
        itemList = arrayListOf()
        rvCatalog.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        rvCatalogAdapter = RvCatalogAdapter(itemList, requireContext()){ itemId: Int? ->
            if (itemId != null){
                val intent = Intent(requireContext(), AdminApproval::class.java)
                intent.putExtra("ITEM_ID", itemId)
                startForResult.launch(intent)
            }

        }
        rvCatalog.adapter = rvCatalogAdapter

        fetchData()
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            fetchData()
        }
    }

    private fun fetchData () {
        coroutine.launch(Dispatchers.IO) {
            val tmpItemList = db.itemDao().fetchUnApproved()
            itemList.clear()
            itemList.addAll(tmpItemList)
            withContext(Dispatchers.Main) {
                rvCatalogAdapter.notifyDataSetChanged()
            }
        }
    }
}