package com.Try.MyApps.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Try.MyApps.model.ResponseUserGithub
import com.Try.MyApps.databinding.FragmentHomeBinding
import com.Try.MyApps.ui.detail.DetailActivity
import com.Try.MyApps.ui.utils.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Tidak perlu melakukan apa-apa saat teks dikirimkan
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    // Jika teks kosong, kembali ke tampilan awal
                    viewModel.getUser()
                } else {
                    // Lakukan pencarian ketika teks tidak kosong
                    viewModel.getUser(newText.toString())
                }
                return true
            }
        })

// Set a click listener on the SearchView to focus it when clicked
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }


        viewModel.resultUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponseUserGithub.Item>)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()

        return view
    }
}


//Entity -> Table
//Dao -> Kumpulan Query
//AppDatabase -> Configurasi dari database room
