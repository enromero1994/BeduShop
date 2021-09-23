package com.example.bedushop

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var Login : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        Login = findViewById(R.id.Login)

        Login.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java).apply {

            }
            startActivity(intent)
            //Toast.makeText(applicationContext,"Hello World",Toast.LENGTH_SHORT).show()

        }

    }
}