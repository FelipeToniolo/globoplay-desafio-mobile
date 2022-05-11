package com.ftoniolo.globoplay.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.ftoniolo.globoplay.R
import com.ftoniolo.globoplay.databinding.FragmentDetailsBinding
import com.ftoniolo.globoplay.framework.imageLoader.ImageLoader
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailsBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setSharedElementTransitionOnEnter()
        setupTabViews()
    }

    private fun setView(){
        val filmViewArgs = args.detailsFilmViewArg
        binding.itemPoster.run {
            transitionName = filmViewArgs.title
            imageLoader.load(
                this, filmViewArgs.imageUrl
            )
        }
        binding.imagePosterBlur.run {
            imageLoader.loadWithBlur(
                this, filmViewArgs.imageUrl
            )
        }

        binding.textNameFilm.text = filmViewArgs.title
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    private fun setupTabViews(){
        val tabLayout = binding.tabDetails
        val viewpager = binding.addViewpager
        val tabs = listOf(R.string.details, R.string.watch_too)

        val tabViewPagerArgs = TabViewPagerArgs(
            overview = args.detailsFilmViewArg.overview,
            title = args.detailsFilmViewArg.title,
            releaseDate = args.detailsFilmViewArg.releaseDate,
            id = args.detailsFilmViewArg.id
        )
        val adapter = TabViewPagerAdapter(this, tabs, tabViewPagerArgs)
            viewpager.adapter = adapter

        TabLayoutMediator(tabLayout, viewpager){ tab, position ->
            tab.text = getString(tabs[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}