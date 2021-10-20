package com.example.bedushop

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

class HomeFragment : Fragment() {

    private lateinit var btn_login : Button
    private lateinit var email_Error : TextView
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var password_Error : TextView
    private lateinit var loginSuccess : TextView
    private lateinit var Register : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        var view = inflater.inflate(R.layout.home_fragment, container, false)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        email_Error = view.findViewById(R.id.emailError);
        password_Error = view.findViewById(R.id.passwordError)
        loginSuccess = view.findViewById(R.id.loginSuccess)
        Register = view.findViewById(R.id.Register)
        btn_login = view.findViewById(R.id.btnLogin)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonRegister = view.findViewById<TextView>(R.id.Register)

        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right

            }
        }
        buttonRegister?.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment, null,options)
        }
        val buttonLogIn = view.findViewById<Button>(R.id.btnLogin)
        buttonLogIn?.setOnClickListener{
            if(email.text.isEmpty()){
                email_Error.isVisible = true
            }
            if(password.text.isEmpty()){
                password_Error.isVisible = true
            }
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){

                findNavController().navigate(R.id.productListFragment, null)
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
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.item1).isVisible = false
        menu.findItem(R.id.item2).isVisible = false

    }
}