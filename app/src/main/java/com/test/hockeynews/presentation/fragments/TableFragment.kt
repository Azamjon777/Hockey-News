package com.test.hockeynews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.test.hockeynews.databinding.FragmentTableBinding
import com.test.hockeynews.presentation.adapter.TableUserAdapter
import com.test.hockeynews.presentation.viewmodel.SharedViewModel

class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private lateinit var myTableAdapter: TableUserAdapter
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        recyclerView = binding.rvTable
        myTableAdapter = TableUserAdapter()
        recyclerView.adapter = myTableAdapter
        viewModel.getTableListLiveData().observe(viewLifecycleOwner) { userList ->
            myTableAdapter.setTableList(userList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}