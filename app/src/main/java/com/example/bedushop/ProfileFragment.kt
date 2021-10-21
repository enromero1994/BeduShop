package com.example.bedushop
import DataClass.User
import Retrofit.AuthApi
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bedushop.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    private lateinit var email : TextView
    private lateinit var userName : TextView
    private lateinit var avatar : ImageView

    private val itemList : ArrayList<ProfileItem> = arrayListOf(ProfileItem("Mis direcciones", R.drawable.ic_location_light, R.id.myLocations),
        ProfileItem("Métodos de pago", R.drawable.ic_credit_card, R.id.paymentMethod),
        ProfileItem("Pedidos", R.drawable.ic_history, R.id.orders),
        ProfileItem("Notificaciones", R.drawable.ic_notifications, R.id.notifications),
        ProfileItem("Cambiar contraseña", R.drawable.ic_lock_dark, R.id.password)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        avatar = view.findViewById(R.id.shapeableImageView)
        email = view.findViewById(R.id.email)
        userName = view.findViewById(R.id.userName)
        view.listView.adapter = ItemListAdapter(requireActivity(), itemList)


        view.listView.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            when(view.id){
                R.id.myLocations -> {

                    val mBottomSheetFragment = AddressFragment()
                    mBottomSheetFragment.show(requireActivity().supportFragmentManager, "MY_BOTTOM_SHEET")

                }
            }
        }



        getUser(6,email,userName,avatar)
        return view
    }

}
private fun getUser(id : Int,email:TextView,userName:TextView, avatar: ImageView){

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
                    userName.text = "${body.data.first_name} ${body.data.last_name}"
                    Picasso.get().load(body.data.avatar).into(avatar)


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
private fun setUpListView(view: View){

}

