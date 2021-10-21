package com.example.bedushop

import DataClass.LoginResponse
import DataClass.User
import Retrofit.AuthApi
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var tokenString : String
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

        var view = inflater.
        inflate(R.layout.home_fragment, container, false)
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
            if(email.text.isEmpty() || password.text.isEmpty()){
                Snackbar.make(view,"Faltan completar campos", Snackbar.LENGTH_SHORT)
                    .setAction("Entendido") {
                        // Responds to click on the action
                    }
                    .show()

            }else{

                //Retrofit
                loginUser(email,password,view)


                //findNavController().navigate(R.id.productListFragment, null)
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

    private fun loginUser(email: EditText, password: EditText, view: View) {
        tokenString = ""


        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofit.create(AuthApi::class.java)


        val email = email.text.toString()
        val password = password.text.toString()

        val call = endpoint.loginUser(email,password)

        call?.enqueue(object: Callback<LoginResponse> {
            //imprimimos algo si no nos llegó respuesta
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("error","Error: $t")
            }

            //mostramos los archivos solo si el resultado es 200
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                if(response.code()==200){
                    val body = response.body()
                    val id = body?.id

                    if (body != null) {

                        Log.e("Respuesta","$id")
                       // tokenString = body.token.toString()

                        if (id != null) {
                            getUser(id)
                        }

                    }


                    //tvPokemon.text = body?.name
                    //tvWeight.text = "peso: " + body?.weight.toString()
                    //Picasso.get().load(body?.sprites?.photoUrl).into(pokemon); //esto es lobrd
                } else{

                    Snackbar.make(view,"Usuario y/o contraseñas incorrectas", Snackbar.LENGTH_SHORT)
                        .setAction("Entendido") {
                            // Responds to click on the action
                        }
                        .show()

                }
            }

        })



    }

    private fun getUser(id : Int){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofit.create(AuthApi::class.java)
        val call = endpoint.getUser(id)
        call?.enqueue(object: Callback<User> {
            //imprimimos algo si no nos llegó respuesta
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("error","Error: $t")
            }

            //mostramos los archivos solo si el resultado es 200
            override fun onResponse(call: Call<User>, response: Response<User>){
                if(response.code()==200){
                    val body = response.body()


                    if (body != null) {

                        Log.e("Respuesta","${body.data.email}")
                        // tokenString = body.token.toString()

                        findNavController().navigate(R.id.productListFragment, null)

                    }


                    //tvPokemon.text = body?.name
                    //tvWeight.text = "peso: " + body?.weight.toString()
                    //Picasso.get().load(body?.sprites?.photoUrl).into(pokemon); //esto es lobrd
                } else{
                    Log.e("Not200","Error not 200: $response")

                }
            }

        })

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.item1).isVisible = false
        menu.findItem(R.id.item2).isVisible = false

    }
}