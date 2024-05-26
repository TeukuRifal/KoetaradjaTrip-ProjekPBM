package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbm.koetaradjatrip.adapter.DataAdapter
import com.pbm.koetaradjatrip.databinding.FragmentShowDataBinding
import com.pbm.koetaradjatrip.models.DataViewModel

class ShowDataFragment : Fragment() {

    private lateinit var viewModel: DataViewModel
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

        val adapter = DataAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        viewModel.allData.observe(viewLifecycleOwner, Observer { data ->
            data?.let { adapter.submitList(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
