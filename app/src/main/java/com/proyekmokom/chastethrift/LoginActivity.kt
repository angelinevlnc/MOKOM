package com.proyekmokom.chastethrift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var username:TextView
    lateinit var password:TextView
    lateinit var btnLogin:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val listUser = FakeUserList.user
        username = findViewById(R.id.editLoginUsername)
        password = findViewById(R.id.editLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            var cek:Int = 0 // 1 = penjual; 2 = pembeli
            var idUser:Int = 0 // untuk ambil id user
            if(username.text.toString() != "" && password.text.toString() != ""){
                for (user in listUser){
                    if(user.username == username.text.toString() && user.password == password.text.toString()){
                        cek = user.role
                        idUser = user.id
                    }
                }
            }
            else{
                Toast.makeText(this, "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }

            if(cek == 0){
                //tidak ditemukan
                Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            }
            else if(cek == 1){
                //penjual
                val intent = Intent(this, MainActivityPenjual::class.java)
                intent.putExtra("idUser", idUser) //Kirim idUser
                startActivity(intent)
                finish() // Finish LoginActivity if you don't want the user to go back to it
            }
            else if(cek == 2){
                //pembeli
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("idUser", idUser) //Kirim idUser
                startActivity(intent)
                finish() // Finish LoginActivity if you don't want the user to go back to it
            }
        }
    }
}