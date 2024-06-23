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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PenjualEditFragment : Fragment() {

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

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
        var idItem:Int = args.idItem

        db = AppDatabase.build(requireContext())

        buttonSellEdit = view.findViewById(R.id.buttonSellEdit)
        buttonCancelEdit = view.findViewById(R.id.buttonCancelEdit)
        buttonDeleteEdit = view.findViewById(R.id.buttonDeleteEdit)
        textTitleEdit = view.findViewById(R.id.textTitleEdit)
        textPriceEdit = view.findViewById(R.id.textPriceEdit)
        textDescriptionEdit = view.findViewById(R.id.textDescriptionEdit)
        textBrandEdit = view.findViewById(R.id.textBrandEdit)
        textSizeEdit = view.findViewById(R.id.textSizeEdit)
        imgViewEdit = view.findViewById(R.id.imageViewEdit)


        coroutine.launch(Dispatchers.IO) {
            val nowItem = db.itemDao().itemById(idItem)
            withContext(Dispatchers.Main) {
                textTitleEdit.text = nowItem.nama
                textPriceEdit.text = nowItem.harga.toString()
                textDescriptionEdit.text = nowItem.deskripsi
                textBrandEdit.text = nowItem.brand
                textSizeEdit.text = nowItem.size

                Glide.with(requireContext()).load(nowItem.gambar).into(imgViewEdit)
            }
        }

        buttonSellEdit.setOnClickListener(){
            val title = textTitleEdit.text.toString()
            val price = textPriceEdit.text.toString().toInt()
            val description = textDescriptionEdit.text.toString()
            val brand = textBrandEdit.text.toString()
            val size = textSizeEdit.text.toString()
            val idUser = LoginViewModel.login_user_id

            db = AppDatabase.build(requireContext())

            coroutine.launch(Dispatchers.IO) {
                val currentItem = db.itemDao().itemById(idItem)
                val newItem = ItemEntity(currentItem.id_item, currentItem.id_user, "https://awsimages.detik.net.id/community/media/visual/2022/11/07/kasus-kucing-mati-dilempar-batu-di-jakarta-kronologi-hingga-penyebab-1.jpeg?w=1200", title, price, description, brand, size, currentItem.asli, currentItem.status)
                db.itemDao().update(newItem)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Berhasil Edit", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }

        buttonDeleteEdit.setOnClickListener {
            coroutine.launch(Dispatchers.IO) {
                db.itemDao().updateStatus(0, idItem)
            }
            findNavController().popBackStack()
        }

        buttonCancelEdit.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}