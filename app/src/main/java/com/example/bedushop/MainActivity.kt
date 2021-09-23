package com.example.bedushop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var btn_login : Button
    private lateinit var email_Error : TextView
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var password_Error : TextView
    private lateinit var loginSuccess : TextView
    private lateinit var Register : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        email_Error = findViewById(R.id.emailError);
        password_Error = findViewById(R.id.passwordError)
        loginSuccess = findViewById(R.id.loginSuccess)
        Register = findViewById(R.id.Register)
        btn_login = findViewById(R.id.btnLogin)

        Register.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java).apply {

            }
            startActivity(intent)
            //Toast.makeText(applicationContext,"Hello World",Toast.LENGTH_SHORT).show()

        }
        btn_login.setOnClickListener{
            if(email.text.isEmpty()){
                email_Error.isVisible = true
            }
            if(password.text.isEmpty()){
                password_Error.isVisible = true
            }
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){

                loginSuccess.isVisible = true




            }

        }
        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                email_Error.isVisible = false
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

        })
        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                password_Error.isVisible = false
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

        })

    }
}


