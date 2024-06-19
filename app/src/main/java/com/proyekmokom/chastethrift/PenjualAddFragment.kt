package com.proyekmokom.chastethrift

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PenjualAddFragment : Fragment() {

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    lateinit var buttonSell: Button
    lateinit var textTitle : TextView
    lateinit var textPrice : TextView
    lateinit var textDescription : TextView
    lateinit var textBrand : TextView
    lateinit var textSize : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penjual_add, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSell = view.findViewById(R.id.buttonSell)
        textTitle = view.findViewById(R.id.textTitle)
        textPrice = view.findViewById(R.id.textPrice)
        textDescription = view.findViewById(R.id.textDescription)
        textBrand = view.findViewById(R.id.textBrand)
        textSize = view.findViewById(R.id.textSize)

        buttonSell.setOnClickListener(){
            val title = textTitle.text.toString()
            val price = textPrice.text.toString().toInt()
            val description = textPrice.text.toString()
            val brand = textBrand.text.toString()
            val size = textSize.text.toString()
            val idUser = LoginViewModel.login_user_id

            db = AppDatabase.build(requireContext())

            coroutine.launch(Dispatchers.IO) {
                val item = ItemEntity(idUser, idUser, "https://awsimages.detik.net.id/community/media/visual/2022/11/07/kasus-kucing-mati-dilempar-batu-di-jakarta-kronologi-hingga-penyebab-1.jpeg?w=1200", title, price, description, "kategori", brand, size, null,1)
                db.itemDao().insert(item)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Berhasil Tambah", Toast.LENGTH_SHORT).show()
                    //Pindah ke fragment lain
                }
            }
        }
    }
}