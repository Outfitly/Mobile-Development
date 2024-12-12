package com.android.outfitly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.outfitly.ArticleAdapter
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var articleAdapter: ArticleAdapter
    private val homeViewModel by lazy {ViewModelProvider(requireActivity())[HomeViewModel::class.java]}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan SessionManager untuk mendapatkan nama/email
        val userName = sessionManager.getUserName()

        // Contoh penggunaan nama atau email
        binding.tvGreetings.text = "Hi $userName"
        // Jika Anda ingin menampilkan email
        // binding.tvGreetings.text = "Hi $userEmail"

        homeViewModel.getNewsFashion()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(listOf())
        binding.articleFashionHome.layoutManager = LinearLayoutManager(requireActivity())
        binding.articleFashionHome.adapter = articleAdapter
    }

    private fun setupObservers() {
        homeViewModel.newsFashion.observe(viewLifecycleOwner) {
            articleAdapter.updateData(it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}