package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbm.koetaradjatrip.adapter.DataAdapter
import com.pbm.koetaradjatrip.databinding.FragmentShowDataBinding
import com.pbm.koetaradjatrip.models.DataViewModel
import com.pbm.koetaradjatrip.models.Data
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowDataFragment : Fragment(), DataAdapter.OnItemClickListener {

    private lateinit var viewModel: DataViewModel
    private lateinit var adapter: DataAdapter
    private var _binding: FragmentShowDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        adapter = DataAdapter(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.getAllData().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: Data) {
        val modalDialogFragment = ModalDialogFragment(data)
        modalDialogFragment.show(parentFragmentManager, "ModalDialogFragment")
    }

    override fun onDeleteClick(data: Data) {
        viewModel.deleteData(data)
    }
}
