package com.proyekmokom.chastethrift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    lateinit var txtTitle: TextView
    lateinit var txtHarga: TextView
    lateinit var txtDesc: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        db = AppDatabase.build(this)

        var itemId = intent.getIntExtra("item_id", -99)
        var item: ItemEntity? = null

        if (itemId != -99) {
            //get item by itemId
            coroutine.launch {
                item = db.itemDao().itemById(itemId)
                Log.e("ITEM", item.toString())
                Log.e("ITEM_ID", itemId.toString())

                if (item == null) {
                    return@launch
                }

                withContext(Dispatchers.Main) {
                    //Isi semua yang berhubungan dengan UI disini!
                    txtTitle = findViewById(R.id.txtTitle)
                    txtHarga = findViewById(R.id.txtHarga)
                    txtDesc = findViewById(R.id.txtDesc)

                    txtTitle.text = item!!.nama
                    txtHarga.text = item!!.harga.toString()
                    txtDesc.text = item!!.deskripsi
                }
            }
        } else {
            Toast.makeText(this, "Item tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}