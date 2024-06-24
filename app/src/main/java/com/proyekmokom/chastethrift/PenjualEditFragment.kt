package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PenjualEditFragment : Fragment() {

    private val viewModel: PenjualEditViewModel by viewModels {
        val appDatabase = AppDatabase.build(requireContext())
        val itemRepository = ItemRepository(appDatabase.itemDao())
        PenjualEditViewModelFactory(itemRepository)
    }

    lateinit var buttonSellEdit: Button
    lateinit var buttonCancelEdit: Button
    lateinit var buttonDeleteEdit: Button
    lateinit var textTitleEdit : TextView
    lateinit var textPriceEdit : TextView
    lateinit var textDescriptionEdit : TextView
    lateinit var textBrandEdit : TextView
    lateinit var textSizeEdit : TextView
    lateinit var imgViewEdit : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penjual_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PenjualEditFragmentArgs by navArgs()
        val idItem:Int = args.idItem

        buttonSellEdit = view.findViewById(R.id.buttonSellEdit)
        buttonCancelEdit = view.findViewById(R.id.buttonCancelEdit)
        buttonDeleteEdit = view.findViewById(R.id.buttonDeleteEdit)
        textTitleEdit = view.findViewById(R.id.textTitleEdit)
        textPriceEdit = view.findViewById(R.id.textPriceEdit)
        textDescriptionEdit = view.findViewById(R.id.textDescriptionEdit)
        textBrandEdit = view.findViewById(R.id.textBrandEdit)
        textSizeEdit = view.findViewById(R.id.textSizeEdit)
        imgViewEdit = view.findViewById(R.id.imageViewEdit)


        viewModel.getItemById(idItem) { item ->
            requireActivity().runOnUiThread {
                textTitleEdit.text = item.nama
                textPriceEdit.text = item.harga.toString()
                textDescriptionEdit.text = item.deskripsi
                textBrandEdit.text = item.brand
                textSizeEdit.text = item.size

                Glide.with(requireContext()).load(item.gambar).into(imgViewEdit)
            }
        }

        buttonSellEdit.setOnClickListener(){
            val title = textTitleEdit.text.toString()
            val price = textPriceEdit.text.toString().toInt()
            val description = textDescriptionEdit.text.toString()
            val brand = textBrandEdit.text.toString()
            val size = textSizeEdit.text.toString()

            viewModel.updateItem("https://awsimages.detik.net.id/community/media/visual/2022/11/07/kasus-kucing-mati-dilempar-batu-di-jakarta-kronologi-hingga-penyebab-1.jpeg?w=1200", title, price, description, brand, size, idItem) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Berhasil Edit", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

        buttonDeleteEdit.setOnClickListener {
            viewModel.deleteItem(idItem) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Item dihapus", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

        buttonCancelEdit.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}