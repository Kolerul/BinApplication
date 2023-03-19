package com.example.binapplication.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.binapplication.BinViewModel
import com.example.binapplication.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private val viewModel: BinViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cardData.observe(this.viewLifecycleOwner){
            binding.apply {
                viewModel.cardData.value?.apply {
                    lengthData.text = number?.length.toString()
                    lunhData.text = number?.luhn.toString()
                    schemeData.text = scheme
                    typeData.text = type
                    brandData.text = brand
                    prepaidData.text = prepaid.toString()
                    countryNameData.text = country?.name
                    numericData.text = country?.numeric.toString()
                    alpha2Data.text = country?.alpha2
                    emojiData.text = country?.emoji
                    currencyData.text = country?.currency
                    latitudeData.text = country?.latitude.toString()
                    longtitudeData.text = country?.longitude.toString()
                    bankNameData.text = bank?.name
                    urlData.text = bank?.url
                    phoneData.text = bank?.phone
                    cityData.text = bank?.city
                }
            }
        }
    }


}