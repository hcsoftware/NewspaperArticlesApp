package com.xr6software.theguardiannews.view.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.theguardiannews.R
import com.xr6software.theguardiannews.databinding.NewsSearchFragmentBinding
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.model.NewsArticleInfo
import com.xr6software.theguardiannews.model.toNewsArticle
import com.xr6software.theguardiannews.utils.*
import com.xr6software.theguardiannews.view.adapters.AdapterClickListener
import com.xr6software.theguardiannews.view.adapters.NewsListAdapter
import com.xr6software.theguardiannews.viewmodel.fragments.NewsSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
@author Hernán Carrera
@version 1.0
This Fragment searchs for news given a topic and list results.
 */
@AndroidEntryPoint
class NewsSearchFragment : Fragment(), AdapterClickListener<String> {

    companion object {
        fun newInstance() = NewsSearchFragment()
    }

    val viewModel by viewModels<NewsSearchViewModel>()
    private lateinit var viewBinding: NewsSearchFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArticleInfoList: List<NewsArticleInfo>
    private val newsListAdapter: NewsListAdapter by lazy {
        NewsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        setDefaultValues()
        setOnClickListeners()
        setObservers()
    }

    private fun setDefaultValues() {
        viewBinding.searchFragmentInputDateFrom.setText("2010/01/01")
        viewBinding.searchFragmentInputDateTo.setText(
            Date().getCurrentDateTime().toString("yyyy/MM/dd")
        )
    }

    private fun setObservers() {

        viewModel.getUiState().observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                NewsSearchViewModel.UiSearchFragmentState.Loading -> {
                    viewBinding.searchFragmentLoading.visibility = View.VISIBLE
                }
                NewsSearchViewModel.UiSearchFragmentState.NoResults -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    MessageFactory.showToast(
                        requireActivity(),
                        R.string.search_fragment_dialog_no_results, Gravity.CENTER
                    )
                    newsListAdapter.updateDataOnView(listOf<NewsArticle>())
                }
                is NewsSearchViewModel.UiSearchFragmentState.Error -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    MessageFactory.showToast(
                        requireActivity(),
                        R.string.search_fragment_dialog_error_loading, Gravity.CENTER
                    )
                    newsListAdapter.updateDataOnView(listOf<NewsArticle>())
                }
                is NewsSearchViewModel.UiSearchFragmentState.Articles -> {
                    viewBinding.searchFragmentLoading.visibility = View.GONE
                    newsArticleInfoList = uiState.articlesList
                    val itemList = uiState.articlesList.map {
                        it.toNewsArticle()
                    }
                    newsListAdapter.updateDataOnView(itemList)
                }
                else -> {}
            }
        })

    }

    private fun setOnClickListeners() {
        viewBinding.searchFragmentInputDateFrom.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showDateDialog(v as EditText)
            }
        }
        viewBinding.searchFragmentInputDateTo.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showDateDialog(v as EditText)
            }
        }
        viewBinding.searchFragmentSearchButton.setOnClickListener {
            if (checkValidUserInput()) {
                var searchTopic: String
                if (viewBinding.searchFragmentExactCheckbox.isChecked) {
                    searchTopic =
                        viewBinding.searchFragmentInputText.text.toString().trim().addDoubleQuotes()
                } else {
                    searchTopic = viewBinding.searchFragmentInputText.text.toString().trim()
                }
                viewModel.searchNewsArticles(
                    searchTopic,
                    viewBinding.searchFragmentInputDateFrom.text.toString(),
                    viewBinding.searchFragmentInputDateTo.text.toString()
                )
            } else {
                MessageFactory.showToast(
                    requireActivity(),
                    R.string.search_fragment_dialog_error_input, Gravity.CENTER
                )
            }
            hideKeyboard()
        }
    }

    private fun checkValidUserInput(): Boolean {
        return (viewBinding.searchFragmentInputText.validateUserInput()
                &&
                viewBinding.searchFragmentInputDateFrom.text.toString().datePeriodIsValid(
                    viewBinding.searchFragmentInputDateTo.text.toString()
                )
                )
    }

    private fun showDateDialog(editText: EditText) {
        editText.clearFocus()
        val listener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> editText.setText("$year/$monthOfYear/$dayOfMonth") }
        val dpDialog = DatePickerDialog(
            requireActivity(),
            listener,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        )
        dpDialog.show()
    }

    override fun onClick(newsTitle: String, position: Int) {
        //false indicates the item isn't locally.
        val data : Pair<String, Boolean> = Pair(newsArticleInfoList[position].apiUrl, false)
        val bundle : Bundle = bundleOf("fragmentItem" to data)
        findNavController().navigate(R.id.newsDetailFragment,bundle)
    }

}