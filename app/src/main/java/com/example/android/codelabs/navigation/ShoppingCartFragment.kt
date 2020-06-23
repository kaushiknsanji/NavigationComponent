package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass to display the Shopping cart.
 * Use the [ShoppingCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingCartFragment : Fragment() {
    // Completed STEP 12.1 - Create a Fragment for the Shopping Cart Menu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    companion object {
        /**
         * Factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment [ShoppingCartFragment].
         */
        @JvmStatic
        fun newInstance() =
                ShoppingCartFragment()
    }
}
