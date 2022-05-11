package com.ftoniolo.globoplay.presentation.details.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ftoniolo.globoplay.databinding.FragmentMovieDetailsBinding
import com.ftoniolo.globoplay.presentation.details.TabViewPagerArgs

class MovieDetailsFragment(private val tabViewPagerArgs: TabViewPagerArgs) :
    Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieDetailsBinding.inflate(
        inflater, container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
    }

    private fun setView() {
        val overview = tabViewPagerArgs.overview.ifEmpty {
            "Em breve, traremos mais informações sobre este filme." }

        binding.txtOriginalTitle.text = tabViewPagerArgs.title
        binding.txtOverview.text = overview
        binding.txtReleaseDate.text = maskDate(tabViewPagerArgs.releaseDate)
    }

    @Suppress("MagicNumber")
    fun maskDate(dataRelease : String) : String{
        val arr = dataRelease.split("-").toTypedArray()
        return "${arr[2]}/${arr[1]}/${arr[0]}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}