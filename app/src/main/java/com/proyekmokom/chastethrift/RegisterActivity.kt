package com.proyekmokom.chastethrift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    lateinit var username: TextView
    lateinit var password: TextView
    lateinit var confirmPassword: TextView
    lateinit var btnRegister: Button
    lateinit var rbPembeli: RadioButton
    lateinit var rbPenjual: RadioButton
    lateinit var txtToLogin:TextView

    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.editRegisterUsername)
        password = findViewById(R.id.editRegisterPassword)
        confirmPassword = findViewById(R.id.editRegisterConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        rbPembeli = findViewById(R.id.rbPembeli)
        rbPenjual = findViewById(R.id.rbPenjual)
        txtToLogin = findViewById(R.id.txtToLogin)

        db = AppDatabase.build(this)

        btnRegister.setOnClickListener {
            if(username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() && confirmPassword.text.toString().isNotEmpty()){
                if(password.text.toString() == confirmPassword.text.toString()){
                    var role:Int = 0
                    if(rbPembeli.isChecked){
                        role = 2
                    }
                    else{
                        role = 1
                    }

                    val newUser = UserEntity(
                        id_user = null,
                        username = username.text.toString(),
                        password = password.text.toString(),
                        role = role,
                    )
                    coroutine.launch(Dispatchers.IO) {
                        var user = db.userDao().get(username.text.toString())
                        if (user.isEmpty()) {
                            //INSERT
                            db.userDao().insert(newUser)

                            //FETCH TO INTENT TO MAINACTIVITY
                            user = db.userDao().fetch()
                            val cek = user.last().role
                            val idUser = user.last().id_user!!

                            withContext(Dispatchers.Main) {
                                navigateToMainActivity(cek, idUser)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@RegisterActivity, "Username sudah ada!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password dan Confirm Password harus sama!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        txtToLogin.setOnClickListener{
            val intent2 = Intent(this, LoginActivity::class.java)
            startActivity(intent2)
            finish()
        }
    }
    private fun navigateToMainActivity(cek: Int, idUser: Int) {
        when (cek) {
            0 -> Toast.makeText(this, "Error, user tidak ditemukan!", Toast.LENGTH_SHORT).show()
            1 -> navigateTo(MainActivityPenjual::class.java, idUser)
            2 -> navigateTo(MainActivity::class.java, idUser)
            3 -> navigateTo(MainActivityAdmin::class.java, idUser)
        }
    }
    private fun navigateTo(destination: Class<*>, idUser: Int) {
        val intent = Intent(this, destination)
        intent.putExtra("idUser", idUser)
        startActivity(intent)
        finish()
    }
}