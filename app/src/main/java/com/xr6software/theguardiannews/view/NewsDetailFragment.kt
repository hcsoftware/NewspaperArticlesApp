package com.xr6software.theguardiannews.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.databinding.NewsDetailFragmentBinding
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.model.NewsDetailItemLocal
import com.xr6software.theguardiannews.model.NewsListItem
import com.xr6software.theguardiannews.utils.MessageFactory
import com.xr6software.theguardiannews.utils.fixTextSimbolsAndLoad
import com.xr6software.theguardiannews.viewmodel.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    val viewModel by viewModels<NewsDetailViewModel>()
    private lateinit var viewBinding: NewsDetailFragmentBinding
    private lateinit var newsDetailItem: NewsDetailItem
    private lateinit var news : NewsListItem
    private lateinit var localNews: NewsDetailItemLocal
    private var localMode: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = NewsDetailFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.containsKey("newsListItem") == true ) {
            news = arguments?.getSerializable("newsListItem") as NewsListItem
            viewModel.getNewsDetailFromAPIService(news.apiUrl,requireActivity())
            viewModel.getNewsSavedOnDbStatus(news.headline)
            localMode = false
        }
        if (arguments?.containsKey("newsDetailItemLocal") == true) {
            localNews = arguments?.getSerializable("newsDetailItemLocal") as NewsDetailItemLocal
            fillDataOnDetailLayout(localNews.headline, localNews.thumbnail, localNews.firstPublicationDate, localNews.trailText, localNews.body)
            viewModel.getNewsSavedOnDbStatus(localNews.headline)
            localMode = true
        }

        setObservers()
        setOnClickListeners()

    }

    private fun fillDataOnDetailLayout(title : String, imgUrl: String, date: Date, subtitle: String, newsBody: String) {
        viewBinding.newsDetailFragmentImage.load(imgUrl)
        viewBinding.newsDetailFragmentHeadline.fixTextSimbolsAndLoad(title)
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        viewBinding.newsDetailFragmentDate.text = simpleDateFormat.format(date)
        viewBinding.newsDetailFragmentTrailtext.fixTextSimbolsAndLoad(subtitle)
        viewBinding.newsDetailFragmentBody.loadDataWithBaseURL("", newsBody, "text/html", "UTF-8", "")
    }

    private fun setOnClickListeners() {
        viewBinding.newsDetailFragmentDbStatus.setOnClickListener {
            if (localMode) {
                viewModel.updateLocalDbStatus(NewsDetailItem(
                    localNews.headline,localNews.thumbnail,localNews.firstPublicationDate, localNews.trailText, localNews.body
                ))
            }
            else {viewModel.updateLocalDbStatus(newsDetailItem)}

        }
    }

    private fun setObservers() {

        viewModel.getNewsDetailItem().observe(viewLifecycleOwner, Observer {
            newsDetailItem = it
            fillDataOnDetailLayout(newsDetailItem.headline,newsDetailItem.thumbnail, newsDetailItem.firstPublicationDate, newsDetailItem.trailText, newsDetailItem.body)
        })
        viewModel.getIsLoading().observe(viewLifecycleOwner, Observer {
            if (it) {viewBinding.newsDetailFragmentLoading.visibility = View.VISIBLE}
            else {viewBinding.newsDetailFragmentLoading.visibility = View.GONE}
        })
        viewModel.getItemOnLocalDb().observe(viewLifecycleOwner, Observer {
            setFloatingButtonIcon(it)
        })
        viewModel.getErrorLoading().observe(viewLifecycleOwner, Observer {
            if (it) {
                MessageFactory.showToast(requireActivity(),
                    R.string.search_fragment_dialog_error_loading, Gravity.CENTER)
            }
        })

    }

    private fun setFloatingButtonIcon(status: Boolean) {
        viewBinding.newsDetailFragmentDbStatus.setImageResource(
            if (status) {
                R.drawable.ic_baseline_bookmark_added_24
            }
            else {
                R.drawable.ic_baseline_bookmark_add_24
            }
        )
    }

}