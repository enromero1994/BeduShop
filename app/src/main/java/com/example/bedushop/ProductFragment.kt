package com.example.bedushop
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random



class ProductFragment : Fragment() {

    private val args: ProductFragmentArgs by navArgs()
    //Detalle de Producto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.product_fragment, container, false)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            // The drawing view is the id of the view above which the container transform
            // will play in z-space.
            drawingViewId = R.id.my_nav_host_fragment
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
            // Set the color of the scrim to transparent as we also want to animate the
            // list fragment out of view
            scrimColor = Color.TRANSPARENT
            //setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }

        //sharedElementEnterTransition =
             //TransitionInflater.from(context).inflateTransition(R.transition.transition1)
                //androidx.transition.TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        //postponeEnterTransition(250, TimeUnit.MILLISECONDS)
    /*
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
        priceView.text = "$${price}"
        descriptionView.text = description
*/

       // initViews(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val title: TextView = view.findViewById(R.id.titleProduct)
        val price: TextView = view.findViewById(R.id.priceProduct)
        val image: ImageView = view.findViewById(R.id.imgProduct)
        val description : TextView = view.findViewById(R.id.descriptionProduct)
        title.text = args.product.title
        title.transitionName = args.product.title
        price.text = args.product.price
        price.transitionName = args.product.price
        image.setImageBitmap(args.product.image)
        image.transitionName = args.product.image
        description.text = args.product.description

    }

    private fun initViews(view: View) {
        var title: TextView = view.findViewById(R.id.titleProduct)
        title.text = args.product.title

        title.transitionName = args.product.title
        //view.findViewById<TextView>(R.id.titleProduct).text = args.product.title
        //view.findViewById<TextView>(R.id.priceProduct).text = "$${args.product.price}"
        //view.findViewById<ImageView>(R.id.imgProduct).setImageBitmap(args.product.image)
        //view.findViewById<RatingBar>(R.id.detailsRatingBar).rating =
          //  Random.nextDouble(0.0, 5.0).toFloat()
        //view.findViewById<TextView>(R.id.detailsRatings).text = (0..300).random().toString()
        //view.findViewById<TextView>(R.id.descriptionProduct).text = args.product.description
        //view.findViewById<TextView>(R.id.interestPrice).text =
         //   "$${String.format("%.2f", (args.product.price / 6))}"
        /*view.findViewById<Button>(R.id.addToCartBtn).setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_cartFragment, null)

        }*/
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Boton carrito
        view.findViewById<View>(R.id.button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_productFragment_to_cartFragment)
        )
    }*/
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
