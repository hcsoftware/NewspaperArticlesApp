package com.xr6software.theguardiannews.view

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.databinding.NewsSearchFragmentBinding
import com.xr6software.theguardiannews.model.NewsListItem
import com.xr6software.theguardiannews.utils.MessageFactory
import com.xr6software.theguardiannews.utils.hideKeyboard
import com.xr6software.theguardiannews.utils.validateUserInput
import com.xr6software.theguardiannews.view.adapters.NewsListAdapter
import com.xr6software.theguardiannews.view.adapters.NewsListAdapterClickListener
import com.xr6software.theguardiannews.viewmodel.NewsSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSearchFragment : Fragment(), NewsListAdapterClickListener {

    companion object {
        fun newInstance() = NewsSearchFragment()
    }

    val viewModel by viewModels<NewsSearchViewModel>()
    private lateinit var viewBinding: NewsSearchFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val newsListAdapter : NewsListAdapter by lazy {
        NewsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = NewsSearchFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = viewBinding.searchFragmentRecyclerView
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = newsListAdapter
        }

        setOnClickListeners()
        setObservers()
    }

    private fun setObservers(){
        viewModel.getIsLoading().observe(viewLifecycleOwner, Observer {
            if (it) {viewBinding.searchFragmentLoading.visibility = View.VISIBLE}
            else {viewBinding.searchFragmentLoading.visibility = View.GONE}
        })
        viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
            newsListAdapter.updateDataOnView(it)
        })
        viewModel.getNoResults().observe(viewLifecycleOwner, Observer {
            if (it) {
                MessageFactory.showToast(requireActivity(),
                    R.string.search_fragment_dialog_no_results, Gravity.CENTER)
            }
        })
        viewModel.getErrorLoading().observe(viewLifecycleOwner, Observer {
            if (it) {
                MessageFactory.showToast(requireActivity(),
                    R.string.search_fragment_dialog_error_loading, Gravity.CENTER)
            }
        })
    }

    private fun setOnClickListeners(){
        viewBinding.searchFragmentSearchButton.setOnClickListener {
            if (viewBinding.searchFragmentInputText.validateUserInput() ) {
                hideKeyboard()
                viewModel.getNewsListFromAPIService(viewBinding.searchFragmentInputText.text.toString(), requireActivity())
            }
            else {
                hideKeyboard()
                MessageFactory.showToast(requireActivity(),
                    R.string.search_fragment_dialog_error_input, Gravity.CENTER)
            }
        }
    }

    override fun onClick(newsListItem: NewsListItem) {
        val bundle : Bundle = bundleOf("newsListItem" to newsListItem)
        findNavController().navigate(R.id.newsDetailFragment,bundle)

    }

}