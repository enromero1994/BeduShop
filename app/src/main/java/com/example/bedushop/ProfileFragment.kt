package com.example.bedushop

import DataClass.User
import Retrofit.AuthApi
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    private lateinit var email : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        email = view.findViewById(R.id.email)

        getUser(6,email)
        return view
    }

}
private fun getUser(id : Int,email:TextView){

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
                    email.text = body.data.email


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