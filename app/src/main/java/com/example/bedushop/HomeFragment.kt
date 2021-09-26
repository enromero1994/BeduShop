package com.example.bedushop

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonRegister = view.findViewById<TextView>(R.id.Register)
        buttonRegister?.setOnClickListener{
            findNavController().navigate(R.id.registerFragment, null)
        }
        val buttonLogIn = view.findViewById<Button>(R.id.btnLogin)
        buttonLogIn?.setOnClickListener{
            findNavController().navigate(R.id.productListFragment, null)
        }


    }
}