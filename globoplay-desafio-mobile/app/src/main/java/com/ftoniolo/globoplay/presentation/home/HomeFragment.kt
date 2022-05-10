package com.ftoniolo.globoplay.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.ftoniolo.globoplay.databinding.FragmentHomeBinding
import com.ftoniolo.globoplay.framework.imageLoader.ImageLoader
import com.ftoniolo.globoplay.presentation.details.DetailsFilmViewArg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("UnusedPrivateMember")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initHomeAdapter()
//        observeInitialLoadState()

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.flipperFilms.displayedChild = when (uiState) {
                HomeViewModel.UiState.Loading -> FLIPPER_CHILD_LOADING
                is HomeViewModel.UiState.Success -> {
                    binding.rvVertical.run {
                        setHasFixedSize(false)
                        adapter = HomeParentAdapter(uiState.homeParentList, imageLoader) { film, view ->

                                val navExtras = FragmentNavigatorExtras(
                                    view to film.title
                                )
                                val directions = HomeFragmentDirections
                                    .actionHomeFragmentToDetailsFragment(
                                        DetailsFilmViewArg(
                                            id = film.id,
                                            overview = film.overview,
                                            title = film.title,
                                            imageUrl = film.imageUrl,
                                            releaseDate = film.releaseDate
                                        )
                                    )
                                findNavController().navigate(directions, navExtras)
                            }
                    }
                    FLIPPER_CHILD_FILMS
                }
                HomeViewModel.UiState.Error ->  {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.getFilmsByCategory()
                    }
                    FLIPPER_CHILD_ERROR
                }

                HomeViewModel.UiState.Empty -> FLIPPER_CHILD_EMPTY
            }
        }
        viewModel.getFilmsByCategory()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_FILMS = 1
        private const val FLIPPER_CHILD_ERROR = 2
        private const val FLIPPER_CHILD_EMPTY = 3
    }
}