package com.kamil.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamil.shoppinglist.databinding.FragmentListLayoutBinding

class ListLayoutFragment : Fragment() {

    private var _binding: FragmentListLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListLayoutBinding.inflate(layoutInflater)



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListLayoutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}