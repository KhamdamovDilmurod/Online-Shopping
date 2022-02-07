package com.example.onlineshopping.screen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.FragmentFavouriteBinding
import com.example.onlineshopping.databinding.FragmentHomeBinding
import com.example.onlineshopping.screen.profil.MainViewModel
import com.example.onlineshopping.utils.PrefUtils
import com.example.onlineshopping.view.ProductsAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavouriteFragment : Fragment(), SearchView.OnQueryTextListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productsData.observe(this, Observer{
            binding.rvSelectedProducts.adapter = ProductsAdapter(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_SHORT).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSelectedProducts.layoutManager = LinearLayoutManager(requireActivity())

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.searchView.setOnQueryTextListener(this)
        loadData()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getFavouriteList())
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val items = viewModel.productsData.value?.filter { it.name.uppercase().contains(newText!!.uppercase()) }
        binding.rvSelectedProducts.adapter = ProductsAdapter(items ?: emptyList())
        return false
    }
}