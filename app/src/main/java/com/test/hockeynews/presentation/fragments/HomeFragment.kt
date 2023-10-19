package com.test.hockeynews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.test.hockeynews.databinding.FragmentHomeBinding
import com.test.hockeynews.presentation.adapter.HomeUserAdapter
import com.test.hockeynews.presentation.models.UserModel
import com.test.hockeynews.presentation.viewmodel.SharedViewModel

class HomeFragment : Fragment(), HomeUserAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var myAdapter: HomeUserAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        viewModel.scoreGold.observe(viewLifecycleOwner) {
            recyclerView = binding.rvHome
            myAdapter = HomeUserAdapter(this, viewModel.scoreGold, viewLifecycleOwner)
            recyclerView.adapter = myAdapter
            viewModel.getUserListLiveData().observe(viewLifecycleOwner) { userList ->
                myAdapter.setList(userList)
            }
            clicks()
        }
    }

    private fun clicks() {
        binding.coinImgGold.setOnClickListener {
            val scoreGold = viewModel.scoreGold.value
            Toast.makeText(requireActivity(), "Your coins: $scoreGold", Toast.LENGTH_SHORT).show()
        }
        binding.coinImgGreen.setOnClickListener {
            Toast.makeText(requireActivity(), "Нou don't have any money yet", Toast.LENGTH_SHORT)
                .show()
        }
        binding.goalsLl.setOnClickListener {
            Toast.makeText(requireActivity(), "So far it's all naked", Toast.LENGTH_SHORT).show()
        }
        binding.friendsLl.setOnClickListener {
            Toast.makeText(requireActivity(), "No friends here yet", Toast.LENGTH_SHORT).show()
        }
        binding.leagueNameLl.setOnClickListener {
            Toast.makeText(requireActivity(), "These are the results of the team game", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onItemClick(userModel: UserModel) {
        viewModel.scoreGold.observe(viewLifecycleOwner) { score ->
            if (score != null) {
                if (score > 50) {
                    Toast.makeText(
                        requireActivity(),
                        "Result - ${userModel.scoreOfTeam1} : ${userModel.scoreOfTeam2}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Наберите 50 монет, чтобы предварительно посмотреть результаты матчей",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
