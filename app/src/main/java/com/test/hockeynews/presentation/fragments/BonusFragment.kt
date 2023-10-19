package com.test.hockeynews.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.hockeynews.databinding.FragmentBonusBinding
import com.test.hockeynews.presentation.GameActivity

class BonusFragment : Fragment() {

    private lateinit var binding: com.test.hockeynews.databinding.FragmentBonusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBonusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startGameBtn.setOnClickListener {
            val intent = Intent(requireActivity(), GameActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}