package com.proyekmokom.chastethrift

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.proyekmokom.chastethrift.databinding.ActivityAdminApprovalBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminApproval : AppCompatActivity() {

    lateinit var binding: ActivityAdminApprovalBinding
    lateinit var db: AppDatabase

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var item: ItemEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminApprovalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra("ITEM_ID", -1)
        if (itemId == -1) {
            return
        }

        db = AppDatabase.build(this)
        coroutine.launch {
            item = db.itemDao().itemById(itemId)
            var penjual = db.userDao().searchById(item!!.id_user)

            withContext(Dispatchers.Main) {
                binding.txtPenjual.text = penjual.username
                binding.txtItem.text = item!!.nama
                Glide.with(applicationContext).load(item!!.gambar).into(binding.imageView6)

                binding.btnAccept.setOnClickListener {
                    updateStatus(1, itemId)
                    Toast.makeText(applicationContext, "Item ditandai sebagai ASLI", Toast.LENGTH_SHORT).show()

                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }

                binding.btnDecline.setOnClickListener {
                    updateStatus(-1, itemId)
                    Toast.makeText(applicationContext, "Item ditandai sebagai PALSU", Toast.LENGTH_SHORT).show()

                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    private fun updateStatus(status: Int, itemId: Int) {
        coroutine.launch {
            db.itemDao().updateStatusApproval(status, itemId)
        }
    }
}