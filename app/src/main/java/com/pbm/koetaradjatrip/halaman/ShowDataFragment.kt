package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbm.koetaradjatrip.adapter.DataAdapter
import com.pbm.koetaradjatrip.databinding.FragmentShowDataBinding
import com.pbm.koetaradjatrip.models.DataViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowDataFragment : Fragment() {

    private lateinit var viewModel: DataViewModel
    private lateinit var adapter: DataAdapter
    private var _binding: FragmentShowDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DataAdapter { data ->
            viewModel.deleteData(data)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        lifecycleScope.launch {
            viewModel.allData.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
