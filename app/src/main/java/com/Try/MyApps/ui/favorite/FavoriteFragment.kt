package com.Try.MyApps.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.Try.MyApps.ui.home.UserAdapter
import com.Try.MyApps.local.DbModule
import com.Try.MyApps.databinding.FragmentFavoriteBinding
import com.Try.MyApps.databinding.FragmentHomeBinding
import com.Try.MyApps.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DbModule(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view: View = binding.root
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter

        viewModel.getUserFavorite().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        return view
    }
}
