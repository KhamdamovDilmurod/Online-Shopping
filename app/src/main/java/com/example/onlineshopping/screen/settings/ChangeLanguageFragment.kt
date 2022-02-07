package com.example.onlineshopping.screen.settings


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshopping.databinding.FragmentCartBinding
import com.example.onlineshopping.databinding.FragmentChangeLanguageBinding
import com.example.onlineshopping.screen.profil.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk

class ChangeLanguageFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentChangeLanguageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangeLanguageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeLanguageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonEnglish.setOnClickListener {
            Hawk.put("pref_lang", "en")
            requireActivity().finish()
            startActivity(Intent(requireActivity(),MainActivity::class.java))
        }
        binding.buttonUzbek.setOnClickListener {
            Hawk.put("pref_lang", "uz")
            requireActivity().finish()
            startActivity(Intent(requireActivity(),MainActivity::class.java))
        }

    }


}