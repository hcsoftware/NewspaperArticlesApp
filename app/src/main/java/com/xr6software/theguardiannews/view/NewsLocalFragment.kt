package com.xr6software.theguardiannews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.databinding.NewsLocalFragmentBinding
import com.xr6software.theguardiannews.model.NewsDetailItemLocal
import com.xr6software.theguardiannews.view.adapters.LocalNewsListAdapter
import com.xr6software.theguardiannews.view.adapters.LocalNewsListAdapterClickListener
import com.xr6software.theguardiannews.viewmodel.NewsLocalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsLocalFragment : Fragment(), LocalNewsListAdapterClickListener {

    companion object {
        fun newInstance() = NewsLocalFragment()
    }

    val viewModel by viewModels<NewsLocalViewModel>()
    private lateinit var viewBinding: NewsLocalFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val newsListAdapter : LocalNewsListAdapter by lazy {
        LocalNewsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = NewsLocalFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = viewBinding.localnewsFragmentRecyclerView
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = newsListAdapter
        }
        setObservers()
        viewModel.getNewsListFromLocalDatabase(requireActivity())
    }

    fun setObservers() {
        viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
            newsListAdapter.updateDataOnView(it)
            viewBinding.localFragmentNoBookText.visibility = View.GONE
            if(it.isEmpty()) {viewBinding.localFragmentNoBookText.visibility = View.VISIBLE}
        })
    }

    override fun onClick(newsDetailItemLocal: NewsDetailItemLocal) {
        val bundle : Bundle = bundleOf("newsDetailItemLocal" to newsDetailItemLocal)
        findNavController().navigate(R.id.newsDetailFragment,bundle)
    }

}