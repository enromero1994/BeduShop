package com.example.bedushop

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detail_product_fragment.view.*
import java.util.concurrent.Executors
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.os.Bundle
import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.bedushop.BuildConfig.DEBUG



class RecyclerAdapter(
    private val context:Context,
    private val products: List<Product>,
    private val clickListener: (Product) -> Unit): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)

        holder.bind(product, context)


        holder.view.setOnClickListener(){


            val bundle = Bundle()
            bundle.putString("titulo",product.title)
            bundle.putString("price",product.price)
            bundle.putString("description",product.description)
            bundle.putString("image",product.image)
            Toast.makeText(context, product.image, Toast.LENGTH_SHORT).show()

            Navigation.findNavController(holder.view).navigate(R.id.productFragment,bundle)



        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.product_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val productName = view.findViewById(R.id.titleProduct) as TextView
        //val description = view.findViewById(R.id.descriptionProduct) as TextView
        val price = view.findViewById(R.id.priceProduct) as TextView
        val image = view.findViewById(R.id.imgProduct) as ImageView




        //"atando" los datos a las Views
        fun bind(product: Product, context: Context){
            productName.text = product.title
            //description.text = product.description
            price.text = product.price
            image.setImageBitmap(product.image)
        }
    }

}

private fun ImageView.setImageBitmap(img: String) {
    val imageView = findViewById<ImageView>(R.id.imgProduct)

    // Declaring executor to parse the URL
    val executor = Executors.newSingleThreadExecutor()

    // Once the executor parses the URL
    // and receives the image, handler will load it
    // in the ImageView
    val handler = Handler(Looper.getMainLooper())

    // Initializing the image
    var image: Bitmap? = null

    // Only for Background process (can take time depending on the Internet speed)
    executor.execute {

        // Image URL
        val imageURL = img

        // Tries to get the image and post it in the ImageView
        // with the help of Handler
        try {
            val `in` = java.net.URL(imageURL).openStream()
            image = BitmapFactory.decodeStream(`in`)

            // Only for making changes in UI
            handler.post {
                imageView.setImageBitmap(image)
            }
        }

        // If the URL doesnot point to
        // image or any other kind of failure
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

