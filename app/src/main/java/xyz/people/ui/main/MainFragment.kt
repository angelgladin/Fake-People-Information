package xyz.people.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import xyz.people.R
import xyz.people.databinding.MainFragmentBinding
import xyz.people.ui.main.entity.UserView
import xyz.people.util.ResultState

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: MainFragmentBinding? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUi()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    Log.e("loading", ":)")
                }
                is ResultState.Success -> {
                    bindData(it.data)
                    Log.e("loading", it.toString())
                }
                is ResultState.Error -> {
                    Log.e("loading", ":)")
                }
            }
            binding?.swiperefresh!!.isRefreshing = false
        })
    }

    private fun setupUi() {
        binding?.let { it.swiperefresh.setOnRefreshListener {
            viewModel.retry()
        } }
    }

    private fun bindData(user: UserView) {
        binding?.let { it.button.text = user.country }
    }
}