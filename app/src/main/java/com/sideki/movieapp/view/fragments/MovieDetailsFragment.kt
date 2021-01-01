package com.sideki.movieapp.view.fragments

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sideki.movieapp.R
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    var recycler_position = 0

    override fun onResume() {
        super.onResume()
        activity?.setActionBar(toolbarDetail)
        activity?.title = args.movieArg.title
        activity?.titleColor = resources.getColor(R.color.white)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarDetail.setNavigationOnClickListener {
            val action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMoviesListFragment(
                    recycler_position
                )
            findNavController().navigate(action)
            //findNavController().navigate(R.id.action_movieDetailsFragment_to_moviesListFragment)
        }

        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w500" + args.movieArg.posterPath)
            .centerCrop()
            .into(image_movie_details)
        text_title_movie_details.text = args.movieArg.title
        rating_bar_movie_details.rating = args.movieArg.voteAverage / 2
        text_description_movie_details.text = args.movieArg.description

        recycler_position = args.position
    }
}