package com.example.onlineshopping.screen.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.FragmentCartBinding
import com.example.onlineshopping.model.CartModel
import com.example.onlineshopping.model.ProductModel
import com.example.onlineshopping.screen.makeorder.MakeOrderActivity
import com.example.onlineshopping.screen.profil.MainViewModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.utils.PrefUtils
import com.example.onlineshopping.view.CartAdapter
import com.example.onlineshopping.view.ProductsAdapter
import java.io.Serializable
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!



    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing  = it
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_SHORT).show()
        })
        viewModel.productsData.observe(requireActivity(), Observer {
            binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerCart.adapter = CartAdapter(it)
        })

        binding.buttonMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(),MakeOrderActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, (viewModel.productsData.value ?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)

        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }
        loadData()

    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}