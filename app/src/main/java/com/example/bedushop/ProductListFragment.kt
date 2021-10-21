package com.example.bedushop

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialElevationScale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.product_fragment.*
import kotlinx.android.synthetic.main.product_list_fragment.*
import java.io.IOException
import kotlin.math.log

class ProductListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return inflater.inflate(R.layout.product_list_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerProducts)
        val list = getProducts(requireContext())
        val adapter = RecyclerAdapter(list, ProductItemListener)
        recyclerView.adapter = adapter
        // When user hits back button transition takes backward
        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private val ProductItemListener = RecyclerAdapter.OnClickListener { product, textView, textView2, imageView ->

        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(
                R.integer.material_motion_duration_long_1
            ).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(
                R.integer.material_motion_duration_long_1
            ).toLong()
        }
        val emailCardDetailTransitionName = getString(
                R.string.product_detail_transition_name
        )

        val direction: NavDirections =
            ProductListFragmentDirections.actionProductListFragmentToProductFragment(product)


        val extras = FragmentNavigatorExtras(
            textView to product.title,
            textView2 to product.price,
            imageView to product.image


        )
        findNavController().navigate(direction,extras)
    }




    //private lateinit var mAdapter : RecyclerAdapter


    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.product_list_fragment, container, false)
        setUpRecyclerView(view)
        return view
    }*/

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
      //  super.onActivityCreated(savedInstanceState)
        //setUpRecyclerView()

    //}

    //configuramos lo necesario para desplegar el RecyclerView

    /*private fun setUpRecyclerView(view: View){


        val recycler = view.findViewById<RecyclerView>(R.id.recyclerProducts)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        mAdapter = RecyclerAdapter(getProducts(requireContext())) { product ->
            val extras = FragmentNavigatorExtras(
                textView to product.title
            )
            val action = ProductListFragmentDirections.actionProductListFragmentToProductFragment(product)
            findNavController().navigate(action,extras)
        }
        //asignando el Adapter al RecyclerView
        recycler.adapter = mAdapter
    }*/

    private fun getJsonDataFromAsset(context: Context?, fileName: String = "products.json"): String? {
        val jsonString: String
        try {
            jsonString = context?.assets?.open(fileName)?.bufferedReader().use { it?.readText()!! }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    fun getProducts(context: Context?): List<Product> {
        val jsonString = getJsonDataFromAsset(context)
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listProductType)
    }

}