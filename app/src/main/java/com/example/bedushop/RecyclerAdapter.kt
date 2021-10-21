package com.example.bedushop


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.concurrent.Executors


class RecyclerAdapter(
    private val productsList: List<Product>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.titleProduct)
        private val price = itemView.findViewById<TextView>(R.id.priceProduct)
        private val image = itemView.findViewById<ImageView>(R.id.imgProduct)
        //private val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        //private val ratings = view.findViewById<TextView>(R.id.ratings)

        //private val banner: ImageView = itemView.findViewById(R.id.sports_item_image_view)
        //private val title: TextView = itemView.findViewById(R.id.title_item_text_view)

        fun bind(
            product: Product,
            onClickListener: OnClickListener
        ) {
            //banner.setImageResource(sports.banner)
            title.text = product.title
            title.transitionName = product.title
            price.text = product.price
            price.transitionName = product.price
            Picasso.get().load(product.image).into(image)
            //image.setImageBitmap(product.image)
            image.transitionName = product.image


            itemView.setOnClickListener {
                onClickListener.onClick(product,title,price,image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products: Product = productsList[position]
        holder.bind(products, onClickListener)
    }

    override fun getItemCount() = productsList.size

    class OnClickListener(val clickListener: (Product, TextView, TextView, ImageView) -> Unit) {
        fun onClick(
            product: Product,
            title : TextView,
            price : TextView,
            image : ImageView
        ) = clickListener(product, title,price,image)
    }


}
/*private fun ImageView.setImageBitmap(img: String) {
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
*/