package com.example.binapplication.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.binapplication.BinApplication
import com.example.binapplication.BinViewModel
import com.example.binapplication.BinViewModelFactory
import com.example.binapplication.R
import com.example.binapplication.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private val viewModel: BinViewModel by activityViewModels {
        BinViewModelFactory(
            (activity?.application as BinApplication).database.numberDao()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val autoEditText = binding.request
        binding.searchButton.setOnClickListener {
            toNextScreen(autoEditText.text.toString())
        }

        val spinner = binding.historySpinner
        viewModel._history.observe(this.viewLifecycleOwner){
            val spinAdapter = ArrayAdapter(this.requireContext(),
                android.R.layout.simple_spinner_item, viewModel.history)
            spinner.adapter = spinAdapter

            val textAdapter = ArrayAdapter(this.requireContext(),
                android.R.layout.simple_dropdown_item_1line, viewModel.history)
            autoEditText.setAdapter(textAdapter)
        }



    }

    private fun toNextScreen(number: String){
        viewModel.getCardData(number)
        findNavController().navigate(R.id.action_searchFragment2_to_detailFragment)
    }
}