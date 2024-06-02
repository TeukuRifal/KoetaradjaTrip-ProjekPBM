package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbm.koetaradjatrip.adapter.DataAdapter
import com.pbm.koetaradjatrip.databinding.FragmentShowDataBinding
import com.pbm.koetaradjatrip.models.Data
import com.pbm.koetaradjatrip.models.DataViewModel
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter

class ShowDataFragment : Fragment() {

    private lateinit var viewModel: DataViewModel
    private lateinit var adapter: PagingDataAdapter<Data, DataAdapter.DataViewHolder>
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

        adapter = DataAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        viewModel.allData.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
