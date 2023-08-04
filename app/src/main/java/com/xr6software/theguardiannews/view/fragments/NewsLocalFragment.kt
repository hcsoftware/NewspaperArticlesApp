package com.xr6software.theguardiannews.view.fragments

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
import com.xr6software.theguardiannews.database.model.toNewsArticle
import com.xr6software.theguardiannews.databinding.NewsLocalFragmentBinding
import com.xr6software.theguardiannews.view.adapters.AdapterClickListener
import com.xr6software.theguardiannews.view.adapters.NewsListAdapter
import com.xr6software.theguardiannews.viewmodel.fragments.NewsLocalViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
@author Hernán Carrera
@version 1.0
This Fragment lists all the news stored in local db.
 */
@AndroidEntryPoint
class NewsLocalFragment : Fragment(), AdapterClickListener<String> {

    companion object {
        fun newInstance() = NewsLocalFragment()
    }

    val viewModel by viewModels<NewsLocalViewModel>()
    private lateinit var viewBinding: NewsLocalFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val newsListAdapter : NewsListAdapter by lazy {
        NewsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.loadSavedNews()
    }

    private fun setObservers() {
        viewModel.getNewsList().observe(viewLifecycleOwner, Observer {
            val itemList = it.map {
               it.toNewsArticle()
            }
            newsListAdapter.updateDataOnView(itemList)
            viewBinding.localFragmentNoBookText.visibility = View.GONE
            if(it.isEmpty()) {viewBinding.localFragmentNoBookText.visibility = View.VISIBLE}
        })
    }

    override fun onClick(newsTitle: String, position: Int) {
        //true indicates the item is local.
        val data : Pair<String, Boolean> = Pair(newsTitle, true)
        val bundle : Bundle = bundleOf("fragmentItem" to data)
        findNavController().navigate(R.id.newsDetailFragment,bundle)
    }

}