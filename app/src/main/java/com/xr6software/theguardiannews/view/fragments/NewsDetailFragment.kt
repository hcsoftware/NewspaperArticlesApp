package com.xr6software.theguardiannews.view.fragments

//import com.xr6software.theguardiannews.model.toNewsArticleDb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.databinding.NewsDetailFragmentBinding
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.model.toNewsArticleDb
import com.xr6software.theguardiannews.viewmodel.fragments.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

/**
@author Hernán Carrera
@version 1.0
This Fragment shows a news Item description.
 */
@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    val viewModel by viewModels<NewsDetailViewModel>()
    private lateinit var viewBinding: NewsDetailFragmentBinding
    private lateinit var newsArticle: NewsArticle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = NewsDetailFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentData = arguments?.getSerializable("fragmentItem") as Pair<String, Boolean>

        //load local item
        if (fragmentData.second) {
            viewModel.getLocalNewsArticle(fragmentData.first)
        }
        //load remote item
        else {
            viewModel.getNewsArticle(fragmentData.first)
        }

        setObservers()
        setOnClickListeners()
    }



    private fun setOnClickListeners() {
        viewBinding.newsDetailFragmentDbStatus.setOnClickListener {
            viewModel.updateLocalDbStatus(newsArticle.toNewsArticleDb())
        }
    }

    private fun setObservers() {

        viewModel.getUiState().observe(viewLifecycleOwner, Observer { uiState ->

            when (uiState) {
                NewsDetailViewModel.NewsDetailUiState.Loading -> {
                    viewBinding.newsDetailFragmentLoading.visibility = View.VISIBLE
                }
                is NewsDetailViewModel.NewsDetailUiState.Error -> {
                    viewBinding.newsDetailFragmentLoading.visibility = View.GONE
                    //TODO show error message
                }
                is NewsDetailViewModel.NewsDetailUiState.NewsDetail -> {
                    viewBinding.newsDetailFragmentLoading.visibility = View.GONE
                    fillDataOnDetailLayout(uiState.newsDetail)
                    viewModel.getIsLocallyStatus(uiState.newsDetail.headline)
                    newsArticle = uiState.newsDetail
                }
            }

        })
        viewModel.getIsLocally().observe(viewLifecycleOwner, Observer {
            setFloatingButtonIcon(it)
        })

    }

    private fun fillDataOnDetailLayout(newsArticle: NewsArticle) {
        viewBinding.newsDetailFragmentImage.load(newsArticle.thumbnail)
        viewBinding.newsDetailFragmentHeadline.text = newsArticle.headline
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        viewBinding.newsDetailFragmentDate.text = simpleDateFormat.format(newsArticle.firstPublicationDate)
        viewBinding.newsDetailFragmentTrailtext.text = newsArticle.trailText
        viewBinding.newsDetailFragmentBody.loadDataWithBaseURL(
            "",
            newsArticle.body.toString(),
            "text/html",
            "UTF-8",
            ""
        )
    }

    private fun setFloatingButtonIcon(status: Boolean) {
        viewBinding.newsDetailFragmentDbStatus.setImageResource(
            if (status) {
                R.drawable.ic_baseline_bookmark_added_24
            } else {
                R.drawable.ic_baseline_bookmark_add_24
            }
        )
    }

}