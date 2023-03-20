package com.example.binapplication.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.binapplication.BinApplication
import com.example.binapplication.BinViewModel
import com.example.binapplication.BinViewModelFactory
import com.example.binapplication.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private val viewModel: BinViewModel by activityViewModels{
        BinViewModelFactory((activity?.application as BinApplication).database.numberDao())
    }

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

        binding.urlData.setOnClickListener {
            val queryUrl: Uri = Uri.parse("https://www.google.com/search?q=${binding.urlData.text}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context?.startActivity(intent)
        }

        binding.phoneData.setOnClickListener {
            val numberUri: Uri = Uri.parse("tel:${binding.phoneData.text}")
            val intent = Intent(Intent.ACTION_DIAL, numberUri)
            context?.startActivity(intent)
        }

        binding.cityData.setOnClickListener {
            val queryUrl: Uri = Uri.parse("https://www.google.ru/maps/place/${binding.cityData.text}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context?.startActivity(intent)
        }

    }


}