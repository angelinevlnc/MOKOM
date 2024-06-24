package com.proyekmokom.chastethrift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.proyekmokom.chastethrift.MidtransPayment.MidtransGateway
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
    lateinit var btnPurchase: Button
    lateinit var imgView: ImageView
    lateinit var imgView3: ImageView
    lateinit var textView21: TextView
    lateinit var btnCancelPurchase: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Isi semua yang berhubungan dengan UI disini!
        txtTitle = findViewById(R.id.txtTitle)
        txtHarga = findViewById(R.id.txtHarga)
        txtDesc = findViewById(R.id.txtDesc)
        imgView = findViewById(R.id.imageView2)
        btnPurchase = findViewById(R.id.btnPurchase)
        imgView3 = findViewById(R.id.imageView3)
        textView21 = findViewById(R.id.textView21)
        btnCancelPurchase = findViewById(R.id.btnCancelPurchase)

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
                    Glide.with(applicationContext).load(item?.gambar).into(imgView)

                    txtTitle.text = item!!.nama
                    txtHarga.text = item!!.harga.toString()
                    txtDesc.text = item!!.deskripsi
                    if(item!!.asli == -1){
                        imgView3.visibility = View.GONE
                        textView21.text = "Not Authentic"
                    }
                    else if(item!!.asli != -1 && item!!.asli != 1){
                        imgView3.visibility = View.GONE
                        textView21.text = "?? Authenticity"
                    }

                    btnPurchase.setOnClickListener {
                        var i = Intent(baseContext, MidtransGateway::class.java)
                        i.putExtra("namaBarang", txtTitle.text.toString())
                        i.putExtra("hargaBarang", txtHarga.text.toString().toInt())
                        startActivity(i)
                    }
                }
            }
        } else {
            Toast.makeText(this, "Item tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnCancelPurchase.setOnClickListener {
            finish()
        }
    }
}