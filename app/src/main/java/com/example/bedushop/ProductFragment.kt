package com.example.bedushop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


class ProductFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.product_fragment, container, false)
        val titleView = view.findViewById<TextView>(R.id.titleProduct)
        val priceView = view.findViewById<TextView>(R.id.priceProduct)
        val imageView = view.findViewById<ImageView>(R.id.imgProduct)
        val descriptionView = view.findViewById<TextView>(R.id.descriptionProduct)

        val title = requireArguments().getString("titulo")
        val price = requireArguments().getString("price")
        val image = requireArguments().getString("image")
        val description = requireArguments().getString("description")

        if (image != null) {
            imageView.setImageBitmap(image)
        }
        titleView.text = title
        priceView.text = price
        descriptionView.text = description
        Toast.makeText(context,image,Toast.LENGTH_SHORT).show()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_productFragment_to_cartFragment)
        )
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
}