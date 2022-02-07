package com.example.onlineshopping.screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.onlineshopping.databinding.FragmentHomeBinding
import com.example.onlineshopping.model.CategoryModel
import com.example.onlineshopping.screen.profil.MainViewModel
import com.example.onlineshopping.utils.Constants
import com.example.onlineshopping.view.CategoryAdapter
import com.example.onlineshopping.view.CategoryAdapterCallback
import com.example.onlineshopping.view.ProductsAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.offersData.observe(requireActivity(), Observer {
            binding.carouselView.setImageListener { position, imageView ->
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                Glide.with(imageView).load("${Constants.HOST_IMAGE}${it[position].image}")
                    .into(imageView)
            }
            binding.carouselView.pageCount = it.count()
        })

        viewModel.categoriesData.observe(requireActivity(), Observer {
            binding.recylerCategories.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.recylerCategories.adapter =
                CategoryAdapter(it, object : CategoryAdapterCallback {
                    override fun onClickItem(item: CategoryModel) {
                        viewModel.getProductsByCategory(item.id)
                    }
                })
        })

        viewModel.productsData.observe(requireActivity(), Observer {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val rvadapter = ProductsAdapter(it)
            binding.recyclerView.adapter = rvadapter
        })
        loadData()
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getAllDBCategories()
        viewModel.getAllDBProducts()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}