package com.example.bedushop


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val productsList: List<Product>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.titleProduct)
        //private val banner: ImageView = itemView.findViewById(R.id.sports_item_image_view)
        //private val title: TextView = itemView.findViewById(R.id.title_item_text_view)

        fun bind(
            product: Product,
            onClickListener: OnClickListener
        ) {
            //banner.setImageResource(sports.banner)
            title.text = product.title
            title.transitionName = product.title

            itemView.setOnClickListener {
                onClickListener.onClick(product,title)
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

    class OnClickListener(val clickListener: (Product, TextView) -> Unit) {
        fun onClick(
            product: Product,
            title : TextView
        ) = clickListener(product, title)
    }
}

